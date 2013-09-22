/*
 * CarCV - Car recognizing and speed calculating platform
 *
 * Authors:
 * Copyright (C) 2012-2013, Ondrej Skopek
 *
 * All rights reserved.
 */

#include "opencv2/objdetect/objdetect.hpp"

using namespace cv;

/*
 * Detection class Det
 */
class Detection {
public:
	static bool detectAndSort(Mat *img, CascadeClassifier &cascade, double scale, string posdir, string negdir, string filename);
	static int countDetected(Mat *img, CascadeClassifier &cascade, double scale);
	void detectAndDraw(Mat *img, CascadeClassifier &cascade, double scale, string windowName);
	static vector<Rect> detect(Mat *img, CascadeClassifier& cascade, double scale);
	static double probability(Mat *imga, Mat *imgb, CascadeClassifier &cascade, const int scaleLO, const int scaleHI);
	static Mat detectMat(Mat *img, CascadeClassifier &cascade, double scale);
	static bool isDetected(Mat *img, CascadeClassifier &cascade, double scale);
	static Mat crop(Mat *img, Rect &roi, double &scale);
	static void coutp(string name, Rect roi);
	static Point2d center(Rect r);
	static Rect scaleRect(Rect roi, double scale);
	void testCropping(Mat *crop, Mat *comp, CascadeClassifier &cascade, double &scale);
	static bool evaluatef(const float a, const float b);
	static bool isInRect(Rect is, Rect in);
};
