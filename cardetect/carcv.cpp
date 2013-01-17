#include "carcv.hpp"
#include "det.hpp"

#include <boost/lexical_cast.hpp>

#define DEBSTR "DEBUG:	"
#define ERRSTR "ERROR:	"
#define SPEEDCONSTANT 3.6

using namespace std;
using namespace cv;

namespace fs = boost::filesystem;

const double scale = 1;

int main(int argc, char** argv) {
	cout << "arg1: path of list" << endl;
	cout << "arg2: cascade.xml path" << endl;
	cout << "arg3-6: x, y, width, height" << endl;

	double rX = atof(argv[3]);
	double rY = atof(argv[4]);
	double rWidth = atof(argv[5]);
	double rHeight = atof(argv[6]);

	Rect speedBox(rX, rY, rWidth, rHeight);

	CascadeClassifier cascade;
	cascade.load(argv[2]);

	fs::path listPath(argv[1]);

	CarCV::run(listPath, CCV_HAAR_SURF, cascade, speedBox);
	//CarCV::test(argc, argv);

	return 0;
}

/*
 * Main run method
 * method is from enum of same name
 */
void CarCV::run(fs::path &imgListPath, int method, CascadeClassifier &cascade, Rect speedBox) {
	fs::path posDirPath = "pos"; //load pos dir path
	fs::path negDirPath = "neg"; //load neg dir path

	double t1, t2, Tstart, Tend;
	double tickspersecond=cvGetTickFrequency() * 1.0e6;
	Tstart = (double) cvGetTickCount();

	t1 = (double) cvGetTickCount();
	CarCV::debugMessage("START parseList()");
	list<string> strImgList = CarCV::parseList(imgListPath); //parse image list file to list<string>
	CarCV::debugMessage("END parseList()");
	t2 = (double) cvGetTickCount() - t1;
	cout << "TIME:		" << (t2/(double)tickspersecond) << "s" << endl;
	cout << endl;

	t1 = (double) cvGetTickCount();
	CarCV::debugMessage("START loadCarImgList()");
	list<CarImg> imgList = CarCV::loadCarImgList(strImgList); //load CarImg objects from the list
	CarCV::debugMessage("END loadCarImgList()");
	t2 = (double) cvGetTickCount() - t1;
	cout << "TIME:		" << (t2/(double)tickspersecond) << "s" << endl;
	cout << endl;

	list<CarImg> negList;

	t1 = (double) cvGetTickCount();
	CarCV::debugMessage("START detect_sortPOS_AND_NEG()");
	list<CarImg> posCarImgList = CarCV::detect_sortPOS_AND_NEG(imgList, cascade, &negList);//detect and sort objects in images of imgList
	CarCV::debugMessage("END detect_sortPOS_AND_NEG()");
	t2 = (double) cvGetTickCount() - t1;
	cout << "TIME:		" << (t2/(double)tickspersecond) << "s" << endl;
	cout << endl;

	//printing lists
	/*
	cout << endl << endl << endl;
	cout << "-------------------------------------------------" << endl;
	cout << "POSITIVE IMAGES" << endl;
	cout << "-------------------------------------------------" << endl;
	int index = 0;
	for (list<CarImg>::iterator i = posCarImgList.begin(); i != posCarImgList.end(); i++) {
		cout << index << ". " << (*i).getPath() << endl;

		index++;
	}

	cout << endl << endl << endl;
	cout << "-------------------------------------------------" << endl;
	cout << "NEGATIVE IMAGES" << endl;
	cout << "-------------------------------------------------" << endl;
	index = 0;
	for (list<CarImg>::iterator i = negList.begin(); i != negList.end(); i++) {
		cout << index << ". " << (*i).getPath() << endl;

		index++;
	}
	*/
	//printing lists


	fs::path carsDir = "cars";
	fs::path carsInsideDir = "inside";

	//saveing

	t1 = (double) cvGetTickCount();
	CarCV::debugMessage("START saveCarImgList(pos)");
	CarCV::saveCarImgList(posCarImgList, posDirPath);
	CarCV::debugMessage("END saveCarImgList(pos)");
	t2 = (double) cvGetTickCount() - t1;
	cout << "TIME:		" << (t2/(double)tickspersecond) << "s" << endl;
	cout << endl;

	t1 = (double) cvGetTickCount();
	CarCV::debugMessage("START saveCarImgList(neg)");
	CarCV::saveCarImgList(negList, negDirPath);
	CarCV::debugMessage("END saveCarImgList(neg)");
	t2 = (double) cvGetTickCount() - t1;
	cout << "TIME:		" << (t2/(double)tickspersecond) << "s" << endl;
	cout << endl;

	//tested up to here

	t1 = (double) cvGetTickCount();
	CarCV::debugMessage("START sortUnique(pos)");
	list<list<CarImg> > cars = CarCV::sortUnique(posCarImgList, cascade, 0.2);
	CarCV::debugMessage("END saveUnique(pos)");
	t2 = (double) cvGetTickCount() - t1;
	cout << "TIME:		" << (t2/(double)tickspersecond) << "s" << endl;
	cout << endl;

	//printing lists

	cout << endl << endl << endl;
	cout << "-------------------------------------------------" << endl;
	cout << "CARS" << endl;
	cout << "-------------------------------------------------" << endl;
	int indexi = 0;
	int indexj = 0;
	list<CarImg> line;
	for (list<list<CarImg> >::iterator i = cars.begin(); i != cars.end(); i++) {
		line = *i;
		for (list<CarImg>::iterator j = line.begin(); j != line.end(); j++){
			cout << "[" << indexi << ":" << indexj << "]	 " << (*j).getPath() << endl;
			indexj++;
		}
		indexj = 0;
		indexi++;
	}
	//printing lists


	t1 = (double) cvGetTickCount();
	CarCV::debugMessage("START saveCars()");
	CarCV::saveCars(cars, carsDir);
	CarCV::debugMessage("END saveCars()");
	t2 = (double) cvGetTickCount() - t1;
	cout << "TIME:		" << (t2/(double)tickspersecond) << "s" << endl;
	cout << endl;


	list<CarImg> * carlist;
	const int carsSize = cars.size();

	list<list<CarImg> > carsInSpeedBox;

	t1 = (double) cvGetTickCount();
	CarCV::debugMessage("START isInSpeedBox()");

	for (int i = 0; i < carsSize; i++) {
		carlist = CarCV::atList(&cars, i);

		carsInSpeedBox.push_back(CarCV::inSpeedBox(*carlist, cascade, speedBox));
	}

	CarCV::debugMessage("END isInSpeedBox()");
	t2 = (double) cvGetTickCount() - t1;
	cout << "TIME:		" << (t2/(double)tickspersecond) << "s" << endl;
	cout << endl;

	t1 = (double) cvGetTickCount();
	CarCV::debugMessage("START saveCarsInside()");
	CarCV::saveCars(carsInSpeedBox, carsInsideDir);
	CarCV::debugMessage("END saveCarsInside()");
	t2 = (double) cvGetTickCount() - t1;
	cout << "TIME:		" << (t2/(double)tickspersecond) << "s" << endl;
	cout << endl;

	//printing lists

	cout << endl << endl << endl;
	cout << "-------------------------------------------------" << endl;
	cout << "CARS" << endl;
	cout << "-------------------------------------------------" << endl;
	indexi = 0;
	indexj = 0;
	line.clear();
	for (list<list<CarImg> >::iterator i = carsInSpeedBox.begin(); i != carsInSpeedBox.end(); i++) {
		line = *i;
		for (list<CarImg>::iterator j = line.begin(); j != line.end(); j++){
			cout << "[" << indexi << ":" << indexj << "]	 " << (*j).getPath() << endl;
			indexj++;
		}
		indexj = 0;
		indexi++;
	}
	//printing lists


	const int carsInSpeedBoxSize = carsInSpeedBox.size();
	double speed;

	t1 = (double) cvGetTickCount();
	CarCV::debugMessage("START calcSpeed()");
	for (int i = 0; i < carsInSpeedBoxSize; i++) {
		carlist = CarCV::atList(&carsInSpeedBox, i);
		speed = CarCV::calcSpeed(*carlist, CCV_SP_FROMALLFILES, 30, 10);
		cout << "Car" << i << " speed:	" << speed << " km/h" << endl;
	}
	CarCV::debugMessage("END calcSpeed()");
	t2 = (double) cvGetTickCount() - t1;
	cout << "TIME:		" << (t2/(double)tickspersecond) << "s" << endl;
	cout << endl;

	cout << endl << endl;
	Tend = (double) cvGetTickCount() - Tstart;
	cout << "TOTALTIME:		" << (Tend/(double)tickspersecond) << "s" << endl;
	return;
}

/*
 * Returns list of positive images list<CarImg>
 * Negative images are stored in *negList pointer (should probably be empty when calling method)
 */
list<CarImg> CarCV::detect_sortPOS_AND_NEG(list<CarImg> &imgList, CascadeClassifier &cascade, list<CarImg> *negList) { //tested, works
	list<CarImg> posList;

	fs::path cPath = (*imgList.begin()).getPath();
	Mat cMat;
	CarImg *cImg;



	const int listSize = imgList.size();
	for (int i = 0; i < listSize; i++) {
		cImg = CarCV::atList(&imgList, i);
		cPath = cImg->getPath();
		cMat = cImg->getImg();

		string result;
		if (Det::isDetected(cMat, cascade, scale)) {
			result = "POSITIVE";
			posList.push_back(*cImg); //maybe .clone()?
		} else {
			result = "NEGATIVE";
			negList->push_back(*cImg); //maybe .clone()?
		}
		CarCV::debugMessage("Sorting image:	" + cPath.generic_string() + "--->" + result);
	}

	return posList;
}

/*
 * Returns only the images where the car is in the given speedBox
 *
 * Images should already contain only one car
 */
list<CarImg> CarCV::inSpeedBox(list<CarImg> &carLineList, CascadeClassifier &cascade, Rect &speedBox) {
	list<CarImg> inside;
	vector<Rect> objects;
	Mat img;
	bool isIn;

	for(list<CarImg>::iterator iter = carLineList.begin(); iter != carLineList.end(); iter++) {
		img = iter->getImg();
		objects = Det::detect(img, cascade, scale);

		//todo: expand for multiple cars in one image
		isIn = Det::isInRect(objects.front(), speedBox);

		if (isIn) {
			inside.push_back(*iter);
		}

	}

	return inside;
}

/*
 * Sort images from posImgList into unique car subdirectiories of carsDir
 * Uses <sarcasm> Ondrej Skopek Sort Algorithm (OSSA) </sarcasm>
 */
list<list<CarImg> > CarCV::sortUnique(list<CarImg> &posCarImgList, CascadeClassifier &cascade, const double PROBABILITYCONST) { //tested, works


	map<CarImg, double> probability; //flushed at every iteration over posImgList

	list<list<CarImg> > cars; //sorted list

	list<double> carProbabilty; //result probabilities for every potential unique car
	CarImg tempCar;

	const int posCarImgListSize = posCarImgList.size();
	for (int i = 0; i < posCarImgListSize; i++) { //iterate over posImgList
		probability.clear();
		carProbabilty.clear();
		const CarImg *sortingCar = CarCV::atList(&posCarImgList, i);
		Mat sortingCarMat = sortingCar->getImg();

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
			list<CarImg> * curList = CarCV::atList(&cars, j);

			const int carsjSize = curList->size();
			for (k = 0; k < carsjSize; k++) {
				const CarImg * curCar = CarCV::atList(curList, k);

				Mat curCarMat = curCar->getImg();

				const double prob = Det::probability(sortingCarMat, curCarMat, cascade, 85, 90);

				//add the car obj and probability of sorted car being the same as cur object to the map
				probability.insert(std::pair<CarImg, double>(*curCar, prob));
			}
		}

		const int carsSize = cars.size();
		for (int l = 0; l < carsSize; l++) {
			list<CarImg> * lineL = CarCV::atList(&cars, l);
			double prob = 0;
			int m;

			const int carslSize = lineL->size();
			for (m = 0; m < carslSize; m++) {
				CarImg * t = CarCV::atList(lineL, m);

				prob += (double) *CarCV::atMap(&probability, *t);
			}
			double count = (double) carslSize;
			prob =  prob / count; //count the average of given images of a unique car
			carProbabilty.push_back(prob);
		}

		int carProbId = CarCV::findMaxIndex(carProbabilty); //finds the index with highest probability
		double maxCarProb = *CarCV::atList(&carProbabilty, carProbId); //and the value

		if (maxCarProb>=PROBABILITYCONST) { //if found a decent match
			//if decent, add to the existing car
			CarCV::atList(&cars, carProbId)->push_back(*sortingCar);
			ostringstream oss;
			oss << i << ">=Push to: Car" << carProbId << "	with prob=" << maxCarProb << ":	" << sortingCar->toString();
			CarCV::debugMessage(oss.str());
		}
		else {
			//if not decent enough, add it as a new car
			list<CarImg> newLine;
			newLine.push_back(*sortingCar);
			cars.push_back(newLine);
			ostringstream oss;
			oss << i << ">=Push to: Car" << cars.size()-1 << "	with prob=" << maxCarProb << ":	" << sortingCar->toString();
			CarCV::debugMessage(oss.str());
		}

	}

	return cars;
}

/*
 * Should return the index of the biggest double in mlist
 * If two are equal, returns the index of the first one
 *
 */
int CarCV::findMaxIndex(list<double> &mlist) { //tested, works
	list<double>::iterator mlistI = mlist.begin();
	double probmax;
	int index;

	for (int i = 0; mlistI != mlist.end();i++) {
		if(*mlistI > probmax) {
			probmax = *mlistI;
			index = i;
		}

		mlistI++;
	}
	return index;
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
	if (speed_method == 1) { //CCV_SP_FROMSORTEDFILES

		double list_size = clist.size();

		double speed = real_measuring_length * (list_size / framerate) * SPEEDCONSTANT;

		return speed;
	} else if (speed_method == 2) { //CCV_SP_FROMALLFILES

		const int indexesLength = clist.front().getPath().filename().generic_string().length();

		vector<int> indexes;

		int index = 0;
		for(list<CarImg>::iterator i = clist.begin(); i != clist.end(); i++) {
			indexes.push_back((*i).parseId());
		}

		int maxId = *max_element(indexes.begin(), indexes.end());
		int minId = *min_element(indexes.begin(), indexes.end());

		cout << "maxId=" << maxId << ";minId=" << minId << endl;

		double diff = abs((double) (maxId - minId));

		double speed = real_measuring_length * (diff / framerate) * SPEEDCONSTANT;

		cout << speed << "=" << real_measuring_length << "*(" << diff << "/" << framerate << ")*" << SPEEDCONSTANT << endl;

		return speed;
	}
	else {
		int n = speed_method;
		cerr << "ERROR: Unimplemented method: " << n << endl;
		return -1;
	}
	return 0;
}

/*
 * Save CarImg objects to carDir (USE FOR UNIQUE CARS)
 */
void CarCV::saveCarImgList(list<CarImg> carList) { //tested, works
	for(list<CarImg>::iterator i = carList.begin(); i != carList.end(); i++) {
		(*i).save();
		cout << "Saving: " << i->toString() << endl;
	}

}

/*
 * Save CarImg objects to carDir (USE FOR UNIQUE CARS)
 */
void CarCV::saveCarImgList(list<CarImg> carList, fs::path carListDir) { //tested, works
	carListDir = fs::absolute(carListDir);
	if (!fs::exists(carListDir) || !fs::is_directory(carListDir)) { //if not exists, create it
		fs::create_directory(carListDir);
	}

	CarImg c;
	fs::path thisPath;
	string thisFilename;
	for(list<CarImg>::iterator i = carList.begin(); i != carList.end(); i++) {
		thisFilename = (*i).getPath().filename().generic_string();

		thisPath = carListDir/thisFilename;

		c = *i;
		c.setPath(thisPath);

		c.save();
	}

}

/**
 * Save list<list<CarImg> > objects to carsDir
 */
void CarCV::saveCars(list<list<CarImg> > cars, fs::path carsDir) { //tested, should work
	carsDir = fs::absolute(carsDir);
	if (!fs::exists(carsDir) || !fs::is_directory(carsDir)) { //if not exists, create it
		fs::create_directory(carsDir);
	}

	fs::path::iterator iterate;
	fs::path temp;
	list<CarImg> *line;

	//this gets the "car" prefix from the name of carsDir, "cars"
	iterate = carsDir.end();
	iterate--;
	string cDirName = (*iterate).generic_string();
	string linePrefix = CarCV::shorten(cDirName, cDirName.size()-1);

	string number;

	int carsSize = cars.size();
	for (int i = 0; i < carsSize; i++) {
		line = CarCV::atList(&cars, i);

		if (i < 10) {
			number = "000"+boost::lexical_cast<string>(i);
		} else if (i < 100) {
			number = "00"+boost::lexical_cast<string>(i);
		} else if (i < 1000) {
			number = "0"+boost::lexical_cast<string>(i);
		} else {
			number = boost::lexical_cast<string>(i);
		}

		temp = fs::path(cDirName+"/"+linePrefix+number);
		temp = fs::absolute(temp);

		if (!fs::exists(temp) || !fs::is_directory(temp)) { //if not exists, create it
				fs::create_directory(temp);
		}


		if (line->size()<=1) { //this catches onesize lists, and replaces them the right way
			string thisFilename = line->front().getPath().filename().generic_string();

			fs::path thisPath = temp/thisFilename;

			CarImg backupImg = line->front();
			CarImg c = backupImg;
			c.setPath(thisPath);

			replace(line->begin(), line->end(), backupImg, c);
		} else {
			int j = 0;
			for (list<CarImg>::iterator lineIt=line->begin(); lineIt != line->end(); lineIt++) {
				string thisFilename = lineIt->getPath().filename().generic_string();

				fs::path thisPath = temp/thisFilename;

				CarImg backupImg = *lineIt;
				CarImg c = backupImg;
				c.setPath(thisPath);

				*line = CarCV::replaceObj(*line, backupImg, c, j); //replaces line with replaced line
				j++;
			}
		}

		CarCV::saveCarImgList(*line);
		ostringstream oss;
		oss << "SaveCarImgList	" << "Line: " << i << ";Size=" << line->size();
		CarCV::debugMessage(oss.str());
		CarCV::debugMessage("Car at 0:		" + line->front().toString());
	}


}

/*
 * int length = length of return shortened sstring
 */
string CarCV::shorten(string s, int length) {
	int len = length+1;
	const char* schar = s.c_str();

	char sshort[len];

	for(int i = 0; i < len; i++) {
		sshort[i] = schar[i];
	}
	sshort[len-1] = '\0';

	string shortString = sshort;
	return shortString;

}



/*
 * Load/parse list<CarImg> objects from carsDir
 * WARNING: _DON'T_ expect folder car0 to be have index 0, car1 index 1, etc..
 */
list<list<CarImg> > CarCV::loadCars(fs::path carsDir) { //tested, should work, nema rovnake poradie! fix?
	list<list<CarImg> > carsList;

	fs::directory_iterator dIt(carsDir);
	fs::directory_iterator dEnd;

	fs::path currentPath;
	while(dIt != dEnd) {
		currentPath = fs::absolute((*dIt));

		list<CarImg> line = CarCV::loadCarImgList(currentPath);

		carsList.push_front(line);

		dIt++;
	}

	return carsList;
}

/*
 * Load/parse CarImg objects from carDir
 * Beware of 'boost::filesystem3::filesystem_error':'No such (file) or directory' for parameter carDir
 */
list<CarImg> CarCV::loadCarImgList(fs::path carDir) { //tested, works
	list<CarImg> carImgList;
	fs::directory_iterator dIt(carDir);
	fs::directory_iterator dEnd;

	fs::path currentPath;
	while(dIt != dEnd) {
		currentPath = fs::absolute((*dIt));

		CarImg c;
		c.setPath(currentPath);
		c.load();

		carImgList.push_back(c);

		dIt++;
	}

	return carImgList;
}

/*
 * Load/parse CarImg objects from carDir
 * Beware of 'boost::filesystem3::filesystem_error':'No such file or (directory)' for parameter carList or any of its contents
 */
list<CarImg> CarCV::loadCarImgList(list<string> carList) { //tested, works
	list<CarImg> carImgList;
	list<string>::iterator it = carList.begin();

	fs::path currentPath;
	while(it != carList.end()) {
		currentPath = fs::absolute((*it));

		CarImg c;
		c.setPath(currentPath);
		c.load();

		carImgList.push_back(c);

		it++;
	}

	return carImgList;
}

/*
 * Parses the input file plist into a list<string>
 */
list<string> CarCV::parseList(fs::path &plist) { //tested, should work
	list<string> retlist;

	FILE* f = fopen(plist.c_str(), "rt");
	if(f)
	{
		char buf [1000+1];
		for(int i = 0; fgets( buf, 1000, f ); i++)
		{
			int len = (int)strlen(buf);
			while( len > 0 && isspace(buf[len-1]) ) {
				len--;
			}
			buf[len] = '\0';
			retlist.push_back(buf);
		}
	}
	return retlist;
}

/*
 * List item at index
 * If index is out of bounds, should return *tlist.end(), but returns rather unexpected results
 */
template <class T>
T * CarCV::atList(list<T> *tlist, int index) { //

	typename list<T>::iterator tlistI = tlist->begin();

	for (int i = 0; tlistI != tlist->end();i++) {
			if (i == index) {
				return &(*tlistI);
			}
			tlistI++;
		}
	return &(*tlist->end());//*--tlistI; was used for returning the last element anyway
}

/*
 * Length of plist
 * Useless: use plist.size()
 */
template <class P>
int CarCV::listSize(list<P> &plist) { //useless, use plist.size()
	typename list<P>::iterator plistI = plist.begin();
	int i;

	for (i = 0; plistI != plist.end();i++) {
			plistI++;
		}
	return i;
}


/*
 * Map item at index
 * If index is not found in map, returns (*tmap.end()).second
 */
template <class K, class V>
V * CarCV::atMap(map<K, V> *tmap, K index) { //tested, works

	typename map<K, V>::iterator tmapI = tmap->begin();
	typename map<K, V>::iterator searching = tmap->find(index);


	for (int i = 0; tmapI != tmap->end();i++) {
			if (tmapI == searching) {
				return &(*tmapI).second;
			}
			tmapI++;
		}
	return &(*tmap->end()).second;
}

/*
 * Size of pmap
 * Useless, use pmap.size()
 */
template <class K, class V>
int CarCV::mapSize(map<K, V> &pmap) { //useless, use pmap.size()
	typename map<K, V>::iterator pmapI = pmap.begin();
	int i;

	for (i = 0; pmapI != pmap.end();i++) {
			pmapI++;
		}
	return i;
}

template <class T>
list<T> CarCV::replaceObj(list<T> list, T replaceObj, T withObj, int index) {
	typename std::list<T> replaced = list;
	typename std::list<T>::iterator lineIte = replaced.begin();

	for (int i = 0; i != index; i++) {
		lineIte++;
	}

	const int lineSize = replaced.size();
	if (lineSize > 1) {
		typename std::list<T>::iterator bIt = lineIte;
		typename std::list<T>::iterator eIt = lineIte;
		bIt--;
		eIt++;
		replace(bIt, eIt, replaceObj, withObj);
	}
	else {
		replace(list.begin(), list.end(), replaceObj, withObj);
	}
	return replaced;
}

void grabKVparams(char **argv) { //just for testing reference, erase later
	for (int i = 1; i <= 5; i++) {
		istringstream source(argv[i]);
		string token;
		string key;
		double n;
		for (int i = 0; getline( source, token, '=' ); i++) {
			if (i == 0) {
				key = token;
			}
		    istringstream ss(token);
		    ss >> n;

		}
		cout << key << endl;
		cout << n << endl;
		// do something with n
		cout << "----------" << endl;
	}
}

void CarCV::test(int argc, char** argv) {
	/*fs::path imgPath1(argv[1]);
	CarImg car1;
	car1.setPath(fs::absolute(imgPath1));
	car1.load();

	fs::path imgPath2(argv[2]);
	CarImg car2;
	car2.setPath(fs::absolute(imgPath2));
	car2.load();

	fs::path imgPath3(argv[3]);
	CarImg car3;
	car3.setPath(fs::absolute(imgPath3));
	car3.load();

	fs::path imgPath4(argv[4]);
	CarImg car4;
	car4.setPath(fs::absolute(imgPath4));
	car4.load();

	fs::path carDir = argv[5];

	list<list<CarImg> > cars;

	list<CarImg> c1;
	c1.push_back(car1);
	c1.push_back(car2);

	list<CarImg> c2;
	c2.push_back(car3);
	c2.push_back(car4);

	cars.push_front(c1);
	cars.push_back(c2);

	//CarCV::saveCars(cars, carDir);

	list<list<CarImg> > set = CarCV::loadCars(carDir);
	list<CarImg> *loaded = CarCV::atList(&set, atoi(argv[6]));

	cvNamedWindow("Images");
	for(list<CarImg>::iterator i = loaded->begin(); i != loaded->end(); i++) {
		imshow("Images", (*i).getImg());
		waitKey(0);
	}
	cvDestroyWindow("Images");*/

	Rect r1, r2;

	r1.x = 5;
	r1.y = 5;
	r1.width = 10;
	r1.height = 10;

	r2.x = 5;
	r2.y = 5;
	r2.width = 5;
	r2.width = 5;

	cout << Det::isInRect(r1, r2) << endl;

	r2.x = 11;
	r2.y = 11;
	r2.width = 5;
	r2.width = 5;

	cout << Det::isInRect(r1, r2) << endl;

	r2.x = 10;
	r2.y = 10;
	r2.width = 5;
	r2.width = 5;

	cout << Det::isInRect(r1, r2) << endl;

}

void CarCV::debugMessage(string message) {
	time_t rawtime;
	struct tm * timeinfo;

	time ( &rawtime );
	timeinfo = localtime ( &rawtime );
	string timestamp = asctime (timeinfo);
	char* timestampC = const_cast<char *> (timestamp.c_str());
	string prefix = DEBSTR;

	for(int i = 0; i < timestamp.size(); i++) { //removes the nextline char
		if (timestampC[i] == '\n') {
			timestampC[i] = '\0';
			break;
		}
	}
	timestamp = timestampC;

	cout << "[" << timestamp << "]" << prefix << message << endl;
	//printf("[%s]%s%s", timestamp.c_str(), prefix.c_str(), message.c_str());
}

void CarCV::errorMessage(string message) {
	time_t rawtime;
	struct tm * timeinfo;

	time ( &rawtime );
	timeinfo = localtime ( &rawtime );
	string timestamp = asctime (timeinfo);
	char* timestampC = const_cast<char *> (timestamp.c_str());
	string prefix = ERRSTR;

	for(int i = 0; i < timestamp.size(); i++) { //removes the nextline char
		if (timestampC[i] == '\n') {
			timestampC[i] = '\0';
			break;
		}
	}
	timestamp = timestampC;

	cout << "[" << timestamp << "]" << prefix << message << endl;
}

