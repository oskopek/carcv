#include <opencv2/imgproc/imgproc.hpp>
#include <opencv2/objdetect/objdetect.hpp>
#include <opencv2/highgui/highgui.hpp>
#include <opencv2/core/core.hpp>
#include <opencv2/ml/ml.hpp>

#include <boost/lexical_cast.hpp>
#include <boost/filesystem.hpp>

#include <iostream>
#include <fstream>
#include <cstdlib>

#include "ocr_train.hpp"
#include "ocr_recognizer.hpp"

using namespace cv;
using namespace std;

namespace fs = boost::filesystem;




int main() {

	//1. training

	string samples_str = "samples";
	string responses_str = "responses";
	vector<float> responses = loadArray< vector<float> >(responses_str);
	vector< vector<float> > samples = load2DArray<float>(samples_str);

	cout << "Samples: " << samples.size() << endl;
	cout << "Responses: " << responses.size() << endl;

	//test: convert vector to Mat
	Mat samples_mat = vector2Mat2D(samples);
	Mat responses_mat = vector2Mat1D(responses);

	//train
	CvKNearest kn_model = CvKNearest();
	kn_model.train(samples_mat, responses_mat);

	//2. testing

	//Loads test image with numbers
	Mat img = imread("test.jpg");
	Mat img_original;
	img.copyTo(img_original);

	//Grayscale
	Mat gray;
	cvtColor(img, gray, CV_BGR2GRAY);

	//Builds a threshold making a binary image
	Mat thresh;
	adaptiveThreshold(gray, thresh, 255, 1, 1, 11, 2);

	//Output
	Mat out;

	//Find contours
	vector<vector<Point> > contours;
	vector<Vec4i> hierarchy;
	findContours(thresh, contours, hierarchy, CV_RETR_LIST, CV_CHAIN_APPROX_SIMPLE);

	double cntArea;
	Mat contourMat;
	for (vector< vector<Point> >::iterator it = contours.begin();
		 it != contours.end(); ++it ) {

		vector<Point> cnt = *it;
		cntArea = contourArea(cnt);

		if (cntArea<50) {
			continue;
		}

		Rect boundbox = boundingRect(cnt);
		int x = boundbox.x;
		int y = boundbox.y;
		int width = boundbox.width;
		int height = boundbox.height;

		if (height<25) { //If it isn't high enough
			continue;
		}

		Point px(x,y);
		Point py(x+width, y+height);
		Scalar color(0, 0, 255); //red

		img.copyTo(contourMat);
		rectangle(contourMat, px, py, color, 2); //draws a rectangle on top of img

		Mat roi;
		roi = thresh(boundbox); //todo could be reverse (x,y) (y,x), am not sure

		//resize it to 10x10
		Mat roismall;
		Size_<int> smallSize(10,10);
		resize(roi, roismall, smallSize);

		//todo ""  roismall = roismall.reshape((1,100))
		//roismall = np.float32(roismall)

		Mat results, neighborResponses, dist;

		float retval = kn_model.find_nearest(roismall, 1, results, neighborResponses, dist);
		float number_res = results.at<float>(0,0);

		string str_result = boost::lexical_cast<string>(number_res);

		Point start_point(x,y+height);
		putText(out, str_result, start_point, 0, 1, color);
	}

	imshow("img", img);
	imshow("out", out);

	return 0;

}
