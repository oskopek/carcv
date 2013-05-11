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

#define SPEED_CONVERSION_CONST 3.6

using namespace std;
using namespace cv;

namespace fs = boost::filesystem;



void CarCV::help()
{
	cout << "\nThis program demonstrates the cascade recognizer. Now you can use Haar or LBP features.\n"
			"This classifier can recognize many kinds of rigid objects, once the appropriate classifier is trained.\n"
			"It's most known use is for cars.\n"
			"Usage:\n"
			"./carcv [--cascade=<cascade_path> this is the primary trained classifier such as cars]\n"
			//"   [--nested-cascade[=nested_cascade_path this an optional secondary classifier such as headlights]]\n"
			"   [--scale=<image scale greater or equal to 1, try 1.3 for example> DO NOT EDIT, KEEP scale=1!]\n"
			//"   [--try-flip]\n"
			"   [--method=<DETECTSORT+SORTUNIQUE+INSIDE+SPEED+> choose one or any combination, with +]\n"
			"   [--speedbox=<x+y+width+height+> of Speed Box Rectangle (needed for method INSIDE)]\n"
			"	[--list=<list_path> path to list of images for given method(s)]\n"
			"   [--posdir=<pos_dir> path where to put positive images]\n"
			"   [--negdir=<neg_dir> path where to put negative images]\n"
			"   [--cardir=<car_dir> path where to put unique car images]\n"
			"   [--insidedir=<inside_dir> path where to put car images which are inside the SpeedBox]\n"
			"   \n\n"
			"example call:\n"
			"./carcv --cascade=\"haarcascade_cars.xml\" --scale=1 --list=list.txt --method=DETECTSORT+SORTUNIQUE\n\n"
			/*"During execution:\n\tHit any key to quit.\n"
    "\tUsing OpenCV version " << CV_VERSION << "\n"*/ << endl;
}

string cascadeName = "/home/odenkos/soc/car_project/test/current.xml";
fs::path cascadePath = fs::absolute(cascadeName);

string listName = "./list.txt";
fs::path listPath = fs::absolute(listName);

string methodName = "DETECTSORT";
string speedBoxStr = "0+0+0+0";

string posdir = "pos";
fs::path posDirPath = fs::absolute(posdir);

string negdir = "neg";
fs::path negDirPath = fs::absolute(negdir);

string cardir = "cars";
fs::path carDirPath = fs::absolute(cardir);

string insidedir = "inside";
fs::path insideDirPath = fs::absolute(insidedir);

const double scale = 1;



int main(int argc, char** argv) {
	/*cout << "arg1: path of list" << endl;
	cout << "arg2: cascade.xml path" << endl;
	cout << "arg3-6: x, y, width, height" << endl;

	double rX = atof(argv[3]);
	double rY = atof(argv[4]);
	double rWidth = atof(argv[5]);
	double rHeight = atof(argv[6]);

	Rect speedBox(rX, rY, rWidth, rHeight);

	CascadeClassifier cascade;
	cascade.load(argv[2]);

	fs::path listPath(argv[1]);*/

	//CarCV::run(listPath, CCV_HAAR_SURF, cascade, speedBox);
	//CarCV::test(argc, argv);
	return CarCV::starter(argc, argv);
}

int CarCV::starter(int argc, char** argv) {
	const string scaleOpt = "--scale=";
	size_t scaleOptLen = scaleOpt.length();

	const string cascadeOpt = "--cascade=";
	size_t cascadeOptLen = cascadeOpt.length();

	const string methodOpt = "--method=";
	size_t methodOptLen = methodOpt.length();

	const string listOpt = "--list=";
	size_t listOptLen = listOpt.length();

	const string speedBoxOpt = "--speedbox=";
	size_t speedBoxOptLen = speedBoxOpt.length();

	const string posDirOpt = "--posdir=";
	size_t posDirOptLen = posDirOpt.length();

	const string negDirOpt = "--negdir=";
	size_t negDirOptLen = negDirOpt.length();

	const string carDirOpt = "--cardir=";
	size_t carDirOptLen = carDirOpt.length();

	const string insideDirOpt = "--insidedir=";
	size_t insideDirOptLen = insideDirOpt.length();

	string inputName;



	CarCV::help();

	Rect speedBox;
	CascadeClassifier cascade;
	double scale = 1;

	for( int i = 1; i < argc; i++ )
	{
		cout << "Processing " << i << " " <<  argv[i] << endl;
		if( cascadeOpt.compare( 0, cascadeOptLen, argv[i], cascadeOptLen ) == 0 )
		{
			cascadeName.assign( argv[i] + cascadeOptLen );
			cascadePath = fs::absolute(fs::path(cascadeName));
			cout << "  from which we have cascade=" << cascadePath.c_str() << endl;
		}
		else if( scaleOpt.compare( 0, scaleOptLen, argv[i], scaleOptLen ) == 0 )
		{
			if( !sscanf( argv[i] + scaleOpt.length(), "%lf", &scale ) || scale < 1 )
				scale = 1;
			cout << " from which we read scale = " << scale << endl;
		}
		else if( methodOpt.compare( 0, methodOptLen, argv[i], methodOptLen ) == 0 )
		{
			methodName.assign( argv[i] + methodOptLen );
			cout << "  from which we have method=" << methodName << endl;
		}
		else if( speedBoxOpt.compare( 0, speedBoxOptLen, argv[i], speedBoxOptLen ) == 0 )
		{
			speedBoxStr.assign( argv[i] + speedBoxOptLen );
			double dimensions[4];
			int index = 0;
			ostringstream oss;
			for (string::iterator i = speedBoxStr.begin(); i != speedBoxStr.end(); i++) {
				if(*i == '+') {
					dimensions[index] = atof(oss.str().c_str());
					index++;
					oss.str("");
					continue;
				}
				oss << *i;
			}

			double x = dimensions[0];
			double y = dimensions[1];
			double width = dimensions[2];
			double height = dimensions[3];

			speedBox = Rect(x, y, width, height);

			cout << "  from which we have speedbox=" << x << "," << y << "," << width << "," << height << endl;
		}
		else if( listOpt.compare( 0, listOptLen, argv[i], listOptLen ) == 0 )
		{
			listName.assign( argv[i] + listOptLen );
			listPath = fs::absolute(fs::path(listName));
			cout << "  from which we have list=" << listPath.c_str() << endl;
		}
		else if( posDirOpt.compare( 0, posDirOptLen, argv[i], posDirOptLen ) == 0 )
		{
			posdir.assign( argv[i] + posDirOptLen );
			posDirPath = fs::absolute(fs::path(posdir));
			cout << "  from which we have posdir=" << posDirPath.c_str() << endl;
		}
		else if( negDirOpt.compare( 0, negDirOptLen, argv[i], negDirOptLen ) == 0 )
		{
			negdir.assign( argv[i] + negDirOptLen );
			negDirPath = fs::absolute(fs::path(negdir));
			cout << "  from which we have negdir=" << negDirPath.c_str() << endl;
		}
		else if( carDirOpt.compare( 0, carDirOptLen, argv[i], carDirOptLen ) == 0 )
		{
			cardir.assign( argv[i] + carDirOptLen );
			carDirPath = fs::absolute(fs::path(cardir));
			cout << "  from which we have cardir=" << carDirPath.c_str() << endl;
		}
		else if( insideDirOpt.compare( 0, insideDirOptLen, argv[i], insideDirOptLen ) == 0 )
		{
			insidedir.assign( argv[i] + insideDirOptLen );
			insideDirPath = fs::absolute(fs::path(insidedir));
			cout << "  from which we have insidedir=" << insideDirPath.c_str() << endl;
		}
		else if( argv[i][0] == '-' )
		{
			cerr << "WARNING: Unknown option %s" << argv[i] << endl;
		}
		else
			inputName.assign( argv[i] );
	}

	if( !cascade.load( cascadeName ) )
	{
		cerr << "ERROR: Could not load classifier cascade" << endl;
		help();
		return -1;
	}

	cout << endl << endl << endl;
	Tools::debugMessage("LOADING FINISHED");


	////////////////////////////////////////////////////////////

	double t1, t2, Tstart, Tend;
	double tickspersecond=cvGetTickFrequency() * 1.0e6;
	Tstart = (double) cvGetTickCount();

	if(methodName.find("DETECTSORT")!=string::npos)
	{

		t1 = (double) cvGetTickCount();
		Tools::debugMessage("START parseList()");
		list<string> strImgList = Tools::parseList(listPath); //parse image list file to list<string>
		Tools::debugMessage("END parseList()");
		t2 = (double) cvGetTickCount() - t1;
		cout << "TIME:		" << (t2/(double)tickspersecond) << "s" << endl;
		cout << endl;

		t1 = (double) cvGetTickCount();
		Tools::debugMessage("START loadCarImgList()");
		list<CarImg> imgList = FileIO::loadCarImgList(strImgList); //load CarImg objects from the list
		Tools::debugMessage("END loadCarImgList()");
		t2 = (double) cvGetTickCount() - t1;
		cout << "TIME:		" << (t2/(double)tickspersecond) << "s" << endl;
		cout << endl;

		list<CarImg> negList;

		t1 = (double) cvGetTickCount();
		Tools::debugMessage("START detect_sortPOS_AND_NEG()");
		list<CarImg> posCarImgList = CarCV::detect_sortPOS_AND_NEG(imgList, cascade, &negList);//detect and sort objects in images of imgList
		Tools::debugMessage("END detect_sortPOS_AND_NEG()");
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

		//saveing
		t1 = (double) cvGetTickCount();
		Tools::debugMessage("START saveCarImgList(pos)");
		FileIO::saveCarImgList(posCarImgList, posDirPath);
		Tools::debugMessage("END saveCarImgList(pos)");
		t2 = (double) cvGetTickCount() - t1;
		cout << "TIME:		" << (t2/(double)tickspersecond) << "s" << endl;
		cout << endl;

		t1 = (double) cvGetTickCount();
		Tools::debugMessage("START saveCarImgList(neg)");
		FileIO::saveCarImgList(negList, negDirPath);
		Tools::debugMessage("END saveCarImgList(neg)");
		t2 = (double) cvGetTickCount() - t1;
		cout << "TIME:		" << (t2/(double)tickspersecond) << "s" << endl;
		cout << endl;

	}

	if(methodName.find("SORTUNIQUE")!=string::npos)
	{

		list<CarImg> posCarImgList = FileIO::loadCarImgList(posDirPath);

		t1 = (double) cvGetTickCount();
		Tools::debugMessage("START sortUnique(pos)");
		list<list<CarImg> > cars = CarCV::sortUnique(posCarImgList, cascade, 0.2);
		Tools::debugMessage("END sortUnique(pos)");
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
		Tools::debugMessage("START saveCars()");
		FileIO::saveCars(cars, carDirPath);
		Tools::debugMessage("END saveCars()");
		t2 = (double) cvGetTickCount() - t1;
		cout << "TIME:		" << (t2/(double)tickspersecond) << "s" << endl;
		cout << endl;

	}

	if(methodName.find("INSIDE") != string::npos)
	{

		list<list<CarImg> > cars = FileIO::loadCars(carDirPath);

		list<CarImg> * carlist;
		const int carsSize = cars.size();

		list<list<CarImg> > carsInSpeedBox;

		t1 = (double) cvGetTickCount();
		Tools::debugMessage("START isInSpeedBox()");

		for (int i = 0; i < carsSize; i++) {
			carlist = Tools::atList(&cars, i);
			carlist->sort();

			carsInSpeedBox.push_back(CarCV::inSpeedBox(*carlist, cascade, speedBox));
		}

		Tools::debugMessage("END isInSpeedBox()");
		t2 = (double) cvGetTickCount() - t1;
		cout << "TIME:		" << (t2/(double)tickspersecond) << "s" << endl;
		cout << endl;

		t1 = (double) cvGetTickCount();
		Tools::debugMessage("START saveCarsInside()");
		FileIO::saveCars(carsInSpeedBox, insideDirPath);
		Tools::debugMessage("END saveCarsInside()");
		t2 = (double) cvGetTickCount() - t1;
		cout << "TIME:		" << (t2/(double)tickspersecond) << "s" << endl;
		cout << endl;

		//printing lists

		cout << endl << endl << endl;
		cout << "-------------------------------------------------" << endl;
		cout << "INSIDE CARS" << endl;
		cout << "-------------------------------------------------" << endl;
		int indexi = 0;
		int indexj = 0;
		list<CarImg> line;
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

		cout << endl << endl << endl;

	}

	if(methodName.find("SPEED") != string::npos)
	{

		list<list<CarImg> > carsInSpeedBox = FileIO::loadCars(insideDirPath);

		list<CarImg> * carlist;

		const int carsInSpeedBoxSize = carsInSpeedBox.size();
		double speed;

		t1 = (double) cvGetTickCount();
		Tools::debugMessage("START calcSpeed()");
		for (int i = 0; i < carsInSpeedBoxSize; i++) {
			carlist = Tools::atList(&carsInSpeedBox, i);

			speed = CarCV::calcSpeed(*carlist, SP_ID_DIFF, 30, 10);
			cout << "Car" << i << " speed:	" << speed << " km/h" << endl;
		}
		Tools::debugMessage("END calcSpeed()");
		t2 = (double) cvGetTickCount() - t1;
		cout << "TIME:		" << (t2/(double)tickspersecond) << "s" << endl;
		cout << endl;

	}

	cout << endl << endl;
	Tend = (double) cvGetTickCount() - Tstart;
	cout << "TOTALTIME:		" << (Tend/(double)tickspersecond) << "s" << endl;



	return 0;

	////////////////////////////////////////////////////////////

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
	Tools::debugMessage("START parseList()");
	list<string> strImgList = Tools::parseList(imgListPath); //parse image list file to list<string>
	Tools::debugMessage("END parseList()");
	t2 = (double) cvGetTickCount() - t1;
	cout << "TIME:		" << (t2/(double)tickspersecond) << "s" << endl;
	cout << endl;

	t1 = (double) cvGetTickCount();
	Tools::debugMessage("START loadCarImgList()");
	list<CarImg> imgList = FileIO::loadCarImgList(strImgList); //load CarImg objects from the list
	Tools::debugMessage("END loadCarImgList()");
	t2 = (double) cvGetTickCount() - t1;
	cout << "TIME:		" << (t2/(double)tickspersecond) << "s" << endl;
	cout << endl;

	list<CarImg> negList;

	t1 = (double) cvGetTickCount();
	Tools::debugMessage("START detect_sortPOS_AND_NEG()");
	list<CarImg> posCarImgList = CarCV::detect_sortPOS_AND_NEG(imgList, cascade, &negList);//detect and sort objects in images of imgList
	Tools::debugMessage("END detect_sortPOS_AND_NEG()");
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
	Tools::debugMessage("START saveCarImgList(pos)");
	FileIO::saveCarImgList(posCarImgList, posDirPath);
	Tools::debugMessage("END saveCarImgList(pos)");
	t2 = (double) cvGetTickCount() - t1;
	cout << "TIME:		" << (t2/(double)tickspersecond) << "s" << endl;
	cout << endl;

	t1 = (double) cvGetTickCount();
	Tools::debugMessage("START saveCarImgList(neg)");
	FileIO::saveCarImgList(negList, negDirPath);
	Tools::debugMessage("END saveCarImgList(neg)");
	t2 = (double) cvGetTickCount() - t1;
	cout << "TIME:		" << (t2/(double)tickspersecond) << "s" << endl;
	cout << endl;

	//tested up to here

	t1 = (double) cvGetTickCount();
	Tools::debugMessage("START sortUnique(pos)");
	list<list<CarImg> > cars = CarCV::sortUnique(posCarImgList, cascade, 0.2);
	Tools::debugMessage("END saveUnique(pos)");
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
	Tools::debugMessage("START saveCars()");
	FileIO::saveCars(cars, carsDir);
	Tools::debugMessage("END saveCars()");
	t2 = (double) cvGetTickCount() - t1;
	cout << "TIME:		" << (t2/(double)tickspersecond) << "s" << endl;
	cout << endl;


	list<CarImg> * carlist;
	const int carsSize = cars.size();

	list<list<CarImg> > carsInSpeedBox;

	t1 = (double) cvGetTickCount();
	Tools::debugMessage("START isInSpeedBox()");

	for (int i = 0; i < carsSize; i++) {
		carlist = Tools::atList(&cars, i);

		carsInSpeedBox.push_back(CarCV::inSpeedBox(*carlist, cascade, speedBox));
	}

	Tools::debugMessage("END isInSpeedBox()");
	t2 = (double) cvGetTickCount() - t1;
	cout << "TIME:		" << (t2/(double)tickspersecond) << "s" << endl;
	cout << endl;

	t1 = (double) cvGetTickCount();
	Tools::debugMessage("START saveCarsInside()");
	FileIO::saveCars(carsInSpeedBox, carsInsideDir);
	Tools::debugMessage("END saveCarsInside()");
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
	Tools::debugMessage("START calcSpeed()");
	for (int i = 0; i < carsInSpeedBoxSize; i++) {
		carlist = Tools::atList(&carsInSpeedBox, i);
		speed = CarCV::calcSpeed(*carlist, SP_ID_DIFF, 30, 10);
		cout << "Car" << i << " speed:	" << speed << " km/h" << endl;
	}
	Tools::debugMessage("END calcSpeed()");
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
list<CarImg> CarCV::inSpeedBox(list<CarImg> &carLineList, CascadeClassifier &cascade, Rect &speedBox) {
	list<CarImg> inside;
	vector<Rect> objects;
	Mat img;
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
list<list<CarImg> > CarCV::sortUnique(list<CarImg> &posCarImgList, CascadeClassifier &cascade, const double PROBABILITYCONST) { //tested, works


	map<CarImg, double> probability; //flushed at every iteration over posImgList

	list<list<CarImg> > cars; //sorted list

	list<double> carProbabilty; //result probabilities for every potential unique car
	CarImg tempCar;

	const int posCarImgListSize = posCarImgList.size();
	for (int i = 0; i < posCarImgListSize; i++) { //iterate over posImgList
		probability.clear();
		carProbabilty.clear();
		const CarImg *sortingCar = Tools::atList(&posCarImgList, i);
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
			list<CarImg> * curList = Tools::atList(&cars, j);

			const int carsjSize = curList->size();
			for (k = 0; k < carsjSize; k++) {
				const CarImg * curCar = Tools::atList(curList, k);

				Mat curCarMat = curCar->getImg();

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
