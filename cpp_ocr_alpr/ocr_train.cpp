#include <opencv2/imgproc/imgproc.hpp>
#include <opencv2/objdetect/objdetect.hpp>
#include <opencv2/highgui/highgui.hpp>
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

int main() {

	//Loads training image with numbers
	Mat img = imread("train.jpg");
	Mat img_original;
	img.copyTo(img_original);

	//Grayscale
	Mat gray;
	cvtColor(img, gray, CV_BGR2GRAY);

	//Gaussian Blur
	Mat blur;
	Size_<int> ksize(5,5); //Gaussian kernel size
	GaussianBlur(gray, blur, ksize, 0);

	//Builds a threshold making a binary image
	Mat thresh;
	adaptiveThreshold(blur, thresh, 255, 1, 1, 11, 2);

	//Find contours
	vector<vector<Point> > contours;
	vector<Vec4i> hierarchy;
	findContours(thresh, contours, hierarchy, CV_RETR_LIST, CV_CHAIN_APPROX_SIMPLE);

	//Prepare arrays
	vector< vector<int> > samples;
	//Mat samples;
	vector<int> responses;


	double cntArea;
	Mat contourMat;
	for (vector< vector<Point> >::iterator it = contours.begin();
		 it != contours.end(); ++it ) {

		vector<Point> cnt = *it;
		cntArea = contourArea(cnt);

		if (cntArea<100 || cntArea>1000) { //Ensures area is large enough
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

		imshow("norm", contourMat); //Shows original image
		int key = waitKey(0);
		
		key -= 0x100000; //hack: fixes keyboard number mapping

		if (key==27) { //Esc key
			return 0;
		}
		else if (key > 48 && key < 150) { // 48-57 keys are numbers from 0-9 and from 65 to 90 A-Z characters
			responses.push_back(key);
			Mat sample;
			sample = roismall.reshape(0,1);//.t();
			samples.push_back(sample);
		}
		else {
			cout << endl <<  "Error: Invalid key " << key << " pressed!!! Press keys on standard keyboard" << endl << endl;
		}

	}

	//todo: save arrays to file
	string samples_str = "samples";
	string responses_str = "responses";
	saveArray(responses_str, responses);
	saveArray(samples_str, samples);


	waitKey(0);
	destroyAllWindows();
	return 0;
}
