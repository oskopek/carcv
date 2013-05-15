/*
 * CarCV - Car recognizing and speed calculating platform
 *
 * Authors:
 * Copyright (C) 2012-2013, Ondrej Skopek
 *
 * All rights reserved.
 */

#include "carcv.hpp"
#include "detection.hpp"
#include "tools.hpp"

#define SPEED_CONVERSION_CONST 3.6


/*
 * Returns list of positive images list<CarImg>
 * Negative images are stored in *negList pointer (should probably be empty when calling method)
 */
list<CarImg> CarCV::detect_sortPOS_AND_NEG(list<CarImg> &imgList, CascadeClassifier &cascade, list<CarImg> *negList, const double scale) {
	list<CarImg> posList;

	fs::path cPath = (*imgList.begin()).getPath();
	Mat * cMat;
	CarImg * cImg;

	string s = "me";

	const int listSize = imgList.size();
	for (int i = 0; i < listSize; i++) {
		cImg = Tools::atList(&imgList, i);
		cPath = cImg->getPath();
		cMat = cImg->getImg();

		string result;
		if (Detection::isDetected(cMat, cascade, scale)) {
			result = "POSITIVE";
			posList.push_back(*cImg); //maybe .clone()?
		} else {
			result = "NEGATIVE";
			negList->push_back(*cImg); //maybe .clone()?
		}
		Tools::debugMessage("Sorting image:	" + cPath.generic_string() + "--->" + result);
	}

	posList.sort();
	return posList;
}

/*
 * Returns only the images where the car is in the given speedBox
 *
 * Images should already contain only one car
 */
list<CarImg> CarCV::inSpeedBox(list<CarImg> &carLineList, CascadeClassifier &cascade, Rect &speedBox, const double scale) {
	list<CarImg> inside;
	vector<Rect> objects;
	Mat * img;
	bool isIn;

	for(list<CarImg>::iterator iter = carLineList.begin(); iter != carLineList.end(); iter++) {
		img = iter->getImg();
		objects = Detection::detect(img, cascade, scale);

		//todo: expand for multiple cars in one image
		isIn = Detection::isInRect(objects.front(), speedBox);

		if (isIn) {
			inside.push_back(*iter);
		}

	}

	inside.sort();
	return inside;
}

/*
 * Sort images from posImgList into unique car subdirectiories of carsDir
 * Uses <sarcasm> Ondrej Skopek Sort Algorithm (OSSA) </sarcasm>
 */
list<list<CarImg> > CarCV::sortUnique(list<CarImg> &posCarImgList, CascadeClassifier &cascade, const double PROBABILITYCONST) {


	map<CarImg, double> probability; //flushed at every iteration over posImgList

	list<list<CarImg> > cars; //sorted list

	list<double> carProbabilty; //result probabilities for every potential unique car
	CarImg * tempCar;

	const int posCarImgListSize = posCarImgList.size();
	for (int i = 0; i < posCarImgListSize; i++) { //iterate over posImgList
		probability.clear();
		carProbabilty.clear();
		const CarImg *sortingCar = Tools::atList(&posCarImgList, i);
		Mat * sortingCarMat = sortingCar->getImg();

		if(cars.size() == i) { //this prevents array index out of bounds and other errors
			list<CarImg> nullLine;
			cars.remove(nullLine); //removes empty lines
			//cars.push_back(nullLine); //adds an empty line if sortedCar doesn't match any already existing car
		}

		if (i == 0 && cars.size() == 0) { //first iteration
			list<CarImg> newLine;
			newLine.push_back(*sortingCar);
			cars.push_back(newLine);
			cout << "First iteration, push to Car" << i << endl;
			continue;
		}



		for (int j = 0; j < cars.size(); j++) { //iterate over the main list of cars
			int k;
			list<CarImg> * curList = Tools::atList(&cars, j);

			const int carsjSize = curList->size();
			for (k = 0; k < carsjSize; k++) {
				const CarImg * curCar = Tools::atList(curList, k);

				Mat * curCarMat = curCar->getImg();

				const double prob = Detection::probability(sortingCarMat, curCarMat, cascade, 85, 90);

				//add the car obj and probability of sorted car being the same as cur object to the map
				probability.insert(std::pair<CarImg, double>(*curCar, prob));
			}
		}

		const int carsSize = cars.size();
		for (int l = 0; l < carsSize; l++) {
			list<CarImg> * lineL = Tools::atList(&cars, l);
			double prob = 0;
			int m;

			const int carslSize = lineL->size();
			for (m = 0; m < carslSize; m++) {
				CarImg * t = Tools::atList(lineL, m);

				prob += (double) *(Tools::atMap(&probability, *t));
			}
			double count = (double) carslSize;
			prob =  prob / count; //count the average of given images of a unique car
			carProbabilty.push_back(prob);
		}

		int carProbId = Tools::findMaxIndex(carProbabilty); //finds the index with highest probability
		double maxCarProb = *Tools::atList(&carProbabilty, carProbId); //and the value

		if (maxCarProb>=PROBABILITYCONST) { //if found a decent match
			//if decent, add to the existing car
			Tools::atList(&cars, carProbId)->push_back(*sortingCar);
			ostringstream oss;
			oss << i << ">=Push to: Car" << carProbId << "	with prob=" << maxCarProb << ":	" << sortingCar->toString();
			Tools::debugMessage(oss.str());
		}
		else {
			//if not decent enough, add it as a new car
			list<CarImg> newLine;
			newLine.push_back(*sortingCar);
			cars.push_back(newLine);
			ostringstream oss;
			oss << i << ">=Push to: Car" << cars.size()-1 << "	with prob=" << maxCarProb << ":	" << sortingCar->toString();
			Tools::debugMessage(oss.str());
		}

	}

	cars.sort();
	return cars;
}

/*
 * Calculates speed of given unique car from the list of CarImg
 * Give him a row from the main list<list<CarImg> >
 * Returns a positive double
 * if speed_method isn't recognized, returns -1
 *
 * speed_method is from enum of same name
 */
double CarCV::calcSpeed(list<CarImg> clist, int speed_method, double framerate, double real_measuring_length) {
	if (speed_method == 1) { //SP_FROMSORTEDFILES

		double list_size = clist.size();

		double speed = real_measuring_length * (list_size / framerate) * SPEED_CONVERSION_CONST;

		return speed;
	} else if (speed_method == 2) { //SP_FROMALLFILES

		const int indexesLength = clist.front().getPath().filename().generic_string().length();

		vector<int> indexes;

		int index = 0;
		for(list<CarImg>::iterator i = clist.begin(); i != clist.end(); i++) {
			indexes.push_back((*i).parseId());
		}

		int maxId = *max_element(indexes.begin(), indexes.end());
		int minId = *min_element(indexes.begin(), indexes.end());

		//cout << "maxId=" << maxId << ";minId=" << minId << endl;

		double diff = abs((double) (maxId - minId));

		double speed = real_measuring_length * (diff / framerate) * SPEED_CONVERSION_CONST;

		//cout << speed << "=" << real_measuring_length << "*(" << diff << "/" << framerate << ")*" << SPEED_CONVERSION_CONST << endl;

		return speed;
	}
	else {
		int n = speed_method;
		cerr << "ERROR: Unimplemented method: " << n << endl;
		return -1;
	}
	return 0;
}
