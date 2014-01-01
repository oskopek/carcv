/*
 * Copyright 2012 CarCV Development Team
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

#include "detection.hpp"
#include "match.hpp"
#include "tools.hpp"

#include <iostream>
#include <iterator>
#include <stdio.h>

#include <boost/filesystem.hpp>

#include "opencv2/highgui/highgui.hpp"
#include "opencv2/imgproc/imgproc.hpp"

#include <cmath>

#define RESET_COLOR "\e[m"
#define MAKE_RED "\e[31m"

using namespace std;
using namespace cv;
namespace fs = boost::filesystem;

const static Scalar colors[] =  { 	CV_RGB(0,0,255),
		CV_RGB(0,128,255),
		CV_RGB(0,255,255),
		CV_RGB(0,255,0),
		CV_RGB(255,128,0),
		CV_RGB(255,255,0),
		CV_RGB(255,0,0),
		CV_RGB(255,0,255)};

/*
 * Detect an image with Mat#detect(Mat&, CascadeClassifier&, double)
 * an shows it in a window named windowName
 */
void Detection::detectAndDraw( Mat * img, CascadeClassifier& cascade, double scale, string windowName)
{
	Mat result = detectMat(img, cascade, scale);
	cv::imshow(windowName, result);
}

/*
 * Detects objects in img and returns a vector of rectangles of object regions
 */
vector<Rect> Detection::detect(Mat *img, CascadeClassifier &cascade, double scale)
{
	vector<Rect> objects;
	Mat gray;
	Mat smallImg( cvRound (img->rows/scale), cvRound(img->cols/scale), CV_8UC1 );

	cvtColor(*img, gray, CV_BGR2GRAY);
	resize(gray, smallImg, smallImg.size(), 0, 0, INTER_LINEAR);
	equalizeHist(smallImg, smallImg);


	cascade.detectMultiScale(smallImg, objects,
			1.1, 2, 0
			//|CV_HAAR_FIND_BIGGEST_OBJECT
			//|CV_HAAR_DO_ROUGH_SEARCH
			|CV_HAAR_SCALE_IMAGE,
			Size(30, 30));
	return objects;
}

/*
 * Returns probability that the detected object in  imga = imgb
 * with CascadeClassifier cascade and int scaleHI, scaleLO
 * probability is from range <0, 1>
 */
double Detection::probability(Mat *imga, Mat *imgb, CascadeClassifier &cascade, const int scaleLO, const int scaleHI) { //implemented according to whiteboard, needs optimization

	int probTrue = 0;
	int counterAll = 0;


	double dscale = (double) scaleLO/100;

	int scale = 1;
	vector<Rect> imgaObj = detect(imga, cascade, scale);
	vector<Rect> imgbObj = detect(imgb, cascade, scale);

	Rect detectedA = imgaObj.front();
	Rect detectedB = imgbObj.front();
	//

	Mat cropped;
	RotatedRect sceneCornersRect;
	vector<Point2f> scene_corners;
	//
	for (int i=scaleLO; i < scaleHI; i++) {
		cropped = Detection::crop(imga, detectedA,dscale);
		scene_corners = Match::sceneCornersGoodMatches(&cropped, imgb, true);

		if(scene_corners.empty()) {
			Tools::debugMessage("SCENE_CORNERS EMPTY");
			counterAll++;
			continue;
		}

		sceneCornersRect = minAreaRect(scene_corners);

		Size2f sizeSCR = Size_<float>(sceneCornersRect.size);
		Size2f sizeCropped = Size_<float>((float) cropped.cols, (float) cropped.rows);

		if(Detection::evaluatef(sizeSCR.area(), sizeCropped.area())) {
			probTrue++;
			//cout << "TRUE:	"<< "SizeSCR=" << sizeSCR.area() << "	;SizeCropped=" << sizeCropped.area() << endl;
		}

		counterAll++;
	}

	dscale = (double) scaleLO/100;
	for (int i=scaleLO; i < scaleHI; i++) {
		cropped = Detection::crop(imgb, detectedB,dscale);
		scene_corners = Match::sceneCornersGoodMatches(&cropped, imga, true);

		if(scene_corners.empty()) { //todo: works?
			Tools::debugMessage("SCENE_CORNERS EMPTY");
			counterAll++;
			continue;
		}

		sceneCornersRect = minAreaRect(scene_corners);

		Size2f sizeSCR = Size_<float>(sceneCornersRect.size);
		Size2f sizeCropped = Size_<float>((float) cropped.cols, (float) cropped.rows);

		if(Detection::evaluatef(sizeSCR.area(), sizeCropped.area())) {
			probTrue++;
			//cout << "TRUE:	"<< "SizeSCR=" << sizeSCR.area() << "	;SizeCropped=" << sizeCropped.area() << endl;
		}

		counterAll++;
	}

	double dProbTrue = (double) probTrue;
	double prob = (double) dProbTrue/counterAll;

	//cout << "Prob=" << prob << "	;ProbTrue=" << dProbTrue << "	;CounterAll=" << counterAll << endl;

	return prob;
}

/*
 * Detects objects in img, draws rectangles around them and returns the img
 */
Mat Detection::detectMat(Mat *img, CascadeClassifier &cascade, double scale) {

	int i = 0;
	vector<Rect> objects = detect(img, cascade, scale);

	for( vector<Rect>::const_iterator r = objects.begin(); r != objects.end(); r++, i++ )
	{
		Point center;
		Scalar color = colors[i%8];
		int radius;

		double aspect_ratio = (double)r->width/r->height;
		if( 0.75 < aspect_ratio && aspect_ratio < 1.3 )
		{
			center.x = cvRound((r->x + r->width*0.5)*scale);
			center.y = cvRound((r->y + r->height*0.5)*scale);
			radius = cvRound((r->width + r->height)*0.25*scale);
			circle( *img, center, radius, color, 3, 8, 0 );
		}
		else {
			rectangle( *img, cvPoint(cvRound(r->x*scale), cvRound(r->y*scale)),
					cvPoint(cvRound((r->x + r->width-1)*scale), cvRound((r->y + r->height-1)*scale)),
					color, 3, 8, 0);
		}
	}
	return *img;
}

/*
 * Returns true if detected object is in img, false if not.
 *
 */
bool Detection::isDetected(Mat *img, CascadeClassifier &cascade, double scale)
{
	return (countDetected(img, cascade, scale) > 0 ? true : false);
}

/*
 * Returns the number of detected objects in img
 */
int Detection::countDetected(Mat *img, CascadeClassifier &cascade, double scale)
{
	return detect(img, cascade, scale).size();
}

/*
 * Returns true if successfully sorted
 */
bool Detection::detectAndSort(Mat *img, CascadeClassifier &cascade, double scale, string posdir, string negdir, string filename)
{
	fs::path filenamePath = filename;
	//filenamePath = fs::absolute(filenamePath);
	fs::path posdirPath = posdir;
	//posdirPath = fs::absolute(posdirPath);
	fs::path negdirPath = negdir;
	//negdirPath = fs::absolute(negdirPath);

	if (!fs::exists(posdirPath) || !fs::is_directory(posdirPath)) {
		fs::create_directory(posdirPath);
	}

	if (!fs::exists(negdirPath) || fs::is_directory(negdirPath)) {
		fs::create_directory(negdirPath);
	}

	if (isDetected(img, cascade, scale)) {
		posdirPath/=filename;
		Mat rect = detectMat(img, cascade, scale);
		//cout << "Moving image " << filenamePath.c_str() << " to " << posdirPath.c_str() << endl;
		cout << "COPY: " << filenamePath << " to: " << posdirPath << endl;
		imwrite(posdirPath.generic_string(), rect);
		//fs::copy_file(filename, posdirPath);
	} else {
		negdirPath/=filename;
		//cout << "Moving image " << filenamePath.c_str() << " to " << negdirPath.c_str() << endl;
		cout << "COPY: " << filenamePath << " to: " << negdirPath << endl;
		fs::copy_file(filename, negdirPath);
	}
	return true;
}

/*
 * Scales the roi and scales it
 * Note!!! scale must be < 1
 */
Mat Detection::crop(Mat *img, Rect &roi, double &scale) {
	Rect scaledROI = scaleRect(roi, scale);
	Mat ret = (*img)(scaledROI);
	return ret;

}

/*
 * Calculates the center point of Rect r
 */
Point2d Detection::center(Rect r) {
	Point2d p;
	p.x = r.x+((r.width)/2);
	p.y = r.y+((r.height)/2);
	return p;
}

bool Detection::isInRect(Rect is, Rect in) { //should work
	bool inside = false;

	list<Point2d> isBoundary;
	list<Point2d> inBoundary;
	Point2d p;


	//is:
	for(int x = is.x; x < is.x+is.width; x++) {
		p = Point2d(x, is.y);
		isBoundary.push_back(p);
	}

	for(int x = is.x; x < is.x+is.width; x++) {
		p = Point2d(x, is.y+is.height);
		isBoundary.push_back(p);
	}

	for(int y = is.y; y < is.y+is.width; y++) {
		p = Point2d(is.x, y);
		isBoundary.push_back(p);
	}

	for(int y = is.y; y < is.y+is.width; y++) {
		p = Point2d(is.x+is.width, y);
		isBoundary.push_back(p);
	}


	//in:
	for(int x = in.x; x < in.x+in.width; x++) {
		p = Point2d(x, in.y);
		inBoundary.push_back(p);
	}

	for(int x = in.x; x < in.x+in.width; x++) {
		p = Point2d(x, in.y+in.height);
		inBoundary.push_back(p);
	}

	for(int y = in.y; y < in.y+in.width; y++) {
		p = Point2d(in.x, y);
		inBoundary.push_back(p);
	}

	for(int y = in.y; y < in.y+in.width; y++) {
		p = Point2d(in.x+in.width, y);
		inBoundary.push_back(p);
	}


	list<Point2d>::iterator temp;

	for (list<Point2d>::iterator i = isBoundary.begin(); i != isBoundary.end(); i++) {
		temp = find(inBoundary.begin(), inBoundary.end(), *i);

		if (temp != inBoundary.end()) {
			inside = true;
			break;
		}
	}

	for (list<Point2d>::iterator i = inBoundary.begin(); i != inBoundary.end(); i++) {
		temp = find(isBoundary.begin(), isBoundary.end(), *i);

		if (temp != isBoundary.end()) {
			inside = true;
			break;
		}
	}


	return inside;
}

/*
 * Scales the given rect to keep ~ the same center point, just be scaled
 */
Rect Detection::scaleRect(Rect roi, double scale) {
	Rect scaledROI;

	scaledROI.height = roi.height*scale;
	scaledROI.width = roi.width*scale;

	Point2d croi = center(roi);
	scaledROI.x = croi.x-((roi.width*scale)/2);
	scaledROI.y = croi.y-((roi.height*scale)/2);
	//scaledROI.x = roi.x+(roi.width*(1-scale));
	//scaledROI.y = roi.y+(roi.height*(1-scale));

	return scaledROI;
}

/*
 * Prints info about the rectangle to cout
 */
void Detection::coutp(string name, Rect roi) {
	cout << name.c_str() << ":		" << "Point[" << roi.x << ", " << roi.y << "];height=" << roi.height << ";width=" << roi.width << endl;
}

/*
void Detection::testCropping(Mat &forcrop, Mat &comp, CascadeClassifier &cascade, double &scale) {
	vector<Rect> forcropObj = detect(forcrop, cascade, scale);
	vector<Rect> compObj = detect(comp, cascade, scale);

	Rect roi = forcropObj.front();
	Match m;
	Mat lol;

	cvNamedWindow("forcropcropped");
	cvNamedWindow("matched");

	int start = 85;
	int limit = 90;

	for(int i = start; i < limit; i++) {
		double di = (double) i;
		cout << di << endl;
		double scale = di/limit;
		cout << scale << endl;

		Mat cropped = crop(forcrop, roi, scale);
		imshow("forcropcropped", cropped);

		lol = m.matGoodMatches(cropped, comp, true);
		imshow("matched", lol);
		waitKey(0);

		Mat img_matches = comp.clone(); //(Size(1000,1000), CV_8UC1);
		Mat img_object = comp.clone();
		vector<Point2f> scene_corners = m.sceneCornersGoodMatches(cropped, comp, true);
		cout << scene_corners << endl;
		//-- Draw lines between the corners (the mapped object in the scene - image_2 )
			  line( img_matches, scene_corners[0] + Point2f( img_object.cols, 0), scene_corners[1] + Point2f( img_object.cols, 0), Scalar(0, 255, 0), 4 );
			  line( img_matches, scene_corners[1] + Point2f( img_object.cols, 0), scene_corners[2] + Point2f( img_object.cols, 0), Scalar( 0, 255, 0), 4 );
			  line( img_matches, scene_corners[2] + Point2f( img_object.cols, 0), scene_corners[3] + Point2f( img_object.cols, 0), Scalar( 0, 255, 0), 4 );
			  line( img_matches, scene_corners[3] + Point2f( img_object.cols, 0), scene_corners[0] + Point2f( img_object.cols, 0), Scalar( 0, 255, 0), 4 );
		imshow("forcropcropped", img_matches);
		waitKey(0);
	}
	cvDestroyAllWindows();
	return;
}
 */

bool Detection::evaluatef(const float a, const float b) {
	float absA = fabsf(a);
	float absB = fabsf(b);
	float absdiff = absA > absB ? absA-absB : absB-absA ;

	if (absdiff < 5000) {
		return true;
	}
	else {
		return false;
	}
}