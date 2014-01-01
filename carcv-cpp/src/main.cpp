/*
 * Copyright 2012-2014 CarCV Development Team
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

#include "carcv.hpp"
#include "tools.hpp"

#include "opencv2/objdetect/objdetect.hpp"
#include "opencv2/highgui/highgui.hpp"

#include <boost/lexical_cast.hpp>

using namespace std;
using namespace cv;

namespace fs = boost::filesystem;

/*
 * Prints help information to cout
 */
void help()
{
	cout << "\nThis program demonstrates the cascade recognizer. Now you can use Haar or LBP features.\n"
			"This classifier can recognize many kinds of rigid objects, once the appropriate classifier is trained.\n"
			"It's most known use is for cars.\n"
			"Usage:\n"
			"./carcv [--cascade=<cascade_path> this is the primary trained classifier such as cars]\n"
			//"   [--nested-cascade[=nested_cascade_path this an optional secondary classifier such as headlights]]\n"
			"	[--scale=<image scale greater or equal to 1, try 1.3 for example> DO NOT EDIT, KEEP scale=1!]\n"
			//"   [--try-flip]\n"
			"   	[--method=<DETECTSORT+SORTUNIQUE+INSIDE+SPEED+> choose one or any combination, with +]\n"
			"   	[--speedbox=<x+y+width+height+> of Speed Box Rectangle (needed for method INSIDE)]\n"
			"	[--list=<list_path> path to list of images for given method(s)]\n"
			"   	[--posdir=<pos_dir> path where to put positive images]\n"
			"   	[--negdir=<neg_dir> path where to put negative images]\n"
			"   	[--cardir=<car_dir> path where to put unique car images]\n"
			"   	[--insidedir=<inside_dir> path where to put car images which are inside the SpeedBox]\n"
			"\n\n"
			"Example call:\n"
			"./carcv --cascade=\"haarcascade_cars.xml\" --scale=1 --list=list.txt --method=DETECTSORT+SORTUNIQUE\n\n"
			"During execution:\n\tHit any key to quit.\n"
			"\tUsing OpenCV version " << CV_VERSION << "\n" << endl;
}

string cascadeName = "/home/odenkos/soc/car_project/test/current.xml";
fs::path cascadePath = fs::absolute(cascadeName);

string listName = "list.txt";
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

/*
 * Starting method that manages input arguments and the program flow
 */
int starter(int argc, char** argv) {
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



	help();

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
		Tools::errorMessage("Could not load classifier cascade at path: " + boost::lexical_cast<string>(cascadePath));
		return 1;
	}
	else if(!fs::exists(listPath)) {
		Tools::errorMessage("List at path: "+ boost::lexical_cast<string>(listPath) + " doesn't exist");
		return 1;
	}
	else if(!fs::is_regular_file(listPath)) {
		Tools::errorMessage("List at path: " + boost::lexical_cast<string>(listPath) + " isn't a valid file");
		return 1;
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
		list<CarImg> posCarImgList = CarCV::detect_sortPOS_AND_NEG(imgList, cascade, &negList, scale);//detect and sort objects in images of imgList
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

			carsInSpeedBox.push_back(CarCV::inSpeedBox(*carlist, cascade, speedBox, scale));
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


int main(int argc, char** argv) {

	return starter(argc, argv);
}