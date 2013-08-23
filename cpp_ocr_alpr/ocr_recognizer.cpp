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

	Mat_<int> templ = Mat_<int>(vec.size(),1);

	for(vector<int>::iterator i = vec.begin(); i != vec.end(); ++i) {
		templ << *i;
	}

	result = (templ);

	return result;
}

Mat vector2Mat2D(vector< vector<int> > vec) {
	Mat result;

	Mat_<int> templ = Mat_<int>(vec.at(0).size(),2); //todo: vec.at(0).size() remove this hack

	for(vector< vector<int> >::iterator i = vec.begin(); i != vec.end(); ++i) {
		for(vector<int>::iterator j = (*i).begin(); j != (*i).end(); ++j) {
			templ << *j;
		}
	}

	result = (templ);

	return result;
}

int main() {

	//1. training

	string samples_str = "samples";
	string responses_str = "responses";
	vector<int> responses = loadArray< vector<int> >(responses_str);
	vector< vector<int> > samples = loadArray<vector < vector<int> > >(samples_str);

	//test: convert vector to Mat
	Mat samples_mat = vector2Mat2D(samples);
	Mat responses_mat = vector2Mat1D(responses);

	//train
	CvKNearest kn_model = CvKNearest();
	kn_model.train(samples_mat, responses_mat);


	//2. testing

	return 0;

}
