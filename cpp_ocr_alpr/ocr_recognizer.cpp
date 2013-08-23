#include "opencv2/imgproc/imgproc.hpp"
#include "opencv2/objdetect/objdetect.hpp"
#include <opencv2/core/core.hpp>
#include <opencv2/ml/ml.hpp>

//#include <boost/lexical_cast.hpp>
#include <boost/filesystem.hpp>

#include <iostream>
#include <fstream>
#include <cstdlib>

using namespace cv;
using namespace std;

namespace fs = boost::filesystem;

vector< vector <double> > load2DNumbers(string filename) { //obosolete
	ifstream dataIn;
	string line = "";

	dataIn.open(filename.c_str());


	vector< vector<double> > numbers;
	while (getline(dataIn, line)) {


		//numbers.push_back(strtod(line.c_str(), NULL));
	}

	return numbers;
}

vector<double> loadNumbers(string filename) { //obsolete
	ifstream dataIn;
	string line = "";

	dataIn.open(filename.c_str());


	vector<double> numbers;
	while (getline(dataIn, line)) {
		numbers.push_back(strtod(line.c_str(), NULL));
	}

	return numbers;
}




int main() {

	// training part
	/*
	vector< vector<double> > samples = load2DNumbers("samples.data");
	vector<double> responses = loadNumbers("responses.data");

	for (int i = 0; i < samples.size(); i++) {
		cout << "Element at " << i << ": " << samples.at(i) << endl;
	}

	cout << endl << endl;

	for (int i = 0; i < responses.size(); i++) {
		cout << "Element at " << i << ": " << responses.at(i) << endl;
	}
	*/

	CvKNearest kn_model = CvKNearest();
	//kn_model.train();


	return 0;

}
