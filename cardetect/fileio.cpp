#include "fileio.hpp"
#include "tools.hpp"

#include <boost/lexical_cast.hpp>

using namespace std;
using namespace cv;

namespace fs = boost::filesystem;

/*
 * Save CarImg objects to carDir (USE FOR UNIQUE CARS)
 */
void FileIO::saveCarImgList(list<CarImg> carList) { //tested, works
	for(list<CarImg>::iterator i = carList.begin(); i != carList.end(); i++) {
		(*i).save();
		cout << "Saving: " << i->toString() << endl;
	}

}

/*
 * Save CarImg objects to carDir (USE FOR UNIQUE CARS)
 */
void FileIO::saveCarImgList(list<CarImg> carList, fs::path carListDir) { //tested, works
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
void FileIO::saveCars(list<list<CarImg> > cars, fs::path carsDir) { //tested, should work
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
	string linePrefix = Tools::shorten(cDirName, cDirName.size()-1);

	string number;

	int carsSize = cars.size();
	for (int i = 0; i < carsSize; i++) {
		line = Tools::atList(&cars, i);

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

				*line = Tools::replaceObj(*line, backupImg, c, j); //replaces line with replaced line
				j++;
			}
		}

		FileIO::saveCarImgList(*line);
		ostringstream oss;
		oss << "SaveCarImgList	" << "Line: " << i << ";Size=" << line->size();
		Tools::debugMessage(oss.str());
		Tools::debugMessage("Car at 0:		" + line->front().toString());
	}


}

/*
 * Load/parse list<CarImg> objects from carsDir
 * WARNING: _DON'T_ expect folder car0 to be have index 0, car1 index 1, etc..
 */
list<list<CarImg> > FileIO::loadCars(fs::path carsDir) { //tested, should work, nema rovnake poradie! fix?
	list<list<CarImg> > carsList;

	fs::directory_iterator dIt(carsDir);
	fs::directory_iterator dEnd;

	fs::path currentPath;
	while(dIt != dEnd) {
		currentPath = fs::absolute((*dIt));

		list<CarImg> line = FileIO::loadCarImgList(currentPath);
		line.sort();

		carsList.push_front(line);

		dIt++;
	}

	carsList.sort();
	return carsList;
}

/*
 * Load/parse CarImg objects from carDir
 * Beware of 'boost::filesystem3::filesystem_error':'No such (file) or directory' for parameter carDir
 */
list<CarImg> FileIO::loadCarImgList(fs::path carDir) { //tested, works
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

	carImgList.sort();
	return carImgList;
}

/*
 * Load/parse CarImg objects from carDir
 * Beware of 'boost::filesystem3::filesystem_error':'No such file or (directory)' for parameter carList or any of its contents
 */
list<CarImg> FileIO::loadCarImgList(list<string> carList) { //tested, works
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

	carImgList.sort();
	return carImgList;
}
