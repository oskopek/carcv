#include "opencv2/objdetect/objdetect.hpp"
#include "opencv2/highgui/highgui.hpp"
#include "opencv2/imgproc/imgproc.hpp"

#include <boost/filesystem.hpp>

#include <iostream>
#include <iterator>
#include <stdio.h>

using namespace cv;

/*
 * Detection class Det
 */
class Det {
public:
	static void help();
	int run(int argc, const char** argv);
	bool detectAndSort(Mat &img, CascadeClassifier &cascade, double scale, string posdir, string negdir, string filename);
	int countDetected(Mat &img, CascadeClassifier &cascade, double scale);
	void detectAndDraw(Mat &img, CascadeClassifier &cascade, double scale, string windowName);
	vector<Rect> detect(Mat &img, CascadeClassifier& cascade, double scale);
	static double probability(Mat &imga, Mat &imgb, CascadeClassifier &cascade, double &scale);
	Mat detectMat(Mat &img, CascadeClassifier &cascade, double scale);
	bool isDetected(Mat &img, CascadeClassifier &cascade, double scale);
	Mat crop(Mat &img, Rect &roi, double &scale);
	void coutp(string name, Rect roi);
	Point2d center(Rect r);
	Rect scaleRect(Rect roi, double scale);
	void testCropping(Mat &crop, Mat &comp, CascadeClassifier &cascade, double &scale);
};
