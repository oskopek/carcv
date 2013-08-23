#include "opencv2/imgproc/imgproc.hpp"
#include "opencv2/objdetect/objdetect.hpp"
#include <opencv2/core/core.hpp>
#include <opencv2/ml/ml.hpp>

//#include <boost/lexical_cast.hpp>
#include <boost/filesystem.hpp>

#include <iostream>
#include <fstream>
#include <cstdlib>

#include "ocr_train.hpp"

using namespace cv;
using namespace std;

namespace fs = boost::filesystem;


Mat vector2Mat1D(vector<int> vec) {
	Mat result;


	return result;
}

Mat vecotr2Mat2D(vector< vector<int> > vec) {
	Mat result;


	return result;
}

int main() {

	//1. training

	string samples_str = "samples";
	string responses_str = "responses";
	vector<int> responses = loadArray< vector<int> >(responses_str);
	vector< vector<int> > samples = loadArray<vector < vector<int> > >(samples_str);

	//todo: convert vector to Mat



	CvKNearest kn_model = CvKNearest();
	//kn_model.train();


	//2. testing

	return 0;

}
