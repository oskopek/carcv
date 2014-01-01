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

#include "opencv2/objdetect/objdetect.hpp"
#include "opencv2/highgui/highgui.hpp"
#include "opencv2/imgproc/imgproc.hpp"
#include "opencv2/calib3d/calib3d.hpp"
#include "opencv2/flann/flann.hpp"
#include "opencv2/nonfree/features2d.hpp"


#include "match.hpp"
#include "tools.hpp"

namespace fs = boost::filesystem;
using namespace std;
using namespace cv;

std::vector<DMatch> Match::vecMatches(Mat * img1, Mat * img2,
		Mat &descriptors_object, vector<KeyPoint> &keypoints_object,
		vector<KeyPoint> &keypoints_scene)
{
	Mat img_object = *img1;
	Mat img_scene = *img2;

	/*
	  if( !img_object.data || !img_scene.data )
	  { std::cout<< " --(!) Error reading images " << std::endl; return -1; }
	 */

	//-- Step 1: Detect the keypoints using SURF Detector
	int minHessian = 400;

	SurfFeatureDetector detector(minHessian);

	//std::vector<KeyPoint> keypoints_object, keypoints_scene;

	detector.detect(img_object, keypoints_object); //TODO: use the third argument here? from previous match?
	detector.detect(img_scene, keypoints_scene);

	//-- Step 2: Calculate descriptors (feature vectors)
	SurfDescriptorExtractor extractor;

	Mat /*descriptors_object,*/ descriptors_scene;

	extractor.compute(img_object, keypoints_object, descriptors_object);
	extractor.compute(img_scene, keypoints_scene, descriptors_scene);

	//-- Step 3: Matching descriptor vectors using FLANN matcher
	FlannBasedMatcher matcher;
	std::vector<DMatch> matches;
	matcher.match(descriptors_object, descriptors_scene, matches);

	return matches;
}

/*
 * Input matches, get a vector of "good" matches
 */
vector<DMatch> Match::vecGoodMatches(Mat * img1, Mat * img2,
		Mat &descriptors_object, vector<KeyPoint> &keypoints_object,
		vector<KeyPoint> &keypoints_scene)
{
	vector<DMatch> matches = vecMatches(img1, img2, descriptors_object, keypoints_object, keypoints_scene);

	double max_dist = 0; double min_dist = 100;

	//-- Quick calculation of max and min distances between keypoints
	for( int i = 0; i < descriptors_object.rows; i++ )
	{ double dist = matches[i].distance;
	if( dist < min_dist ) min_dist = dist;
	if( dist > max_dist ) max_dist = dist;
	}

	//outcommented, produces noise in terminal
	//printf("-- Max dist : %f \n", max_dist );
	//printf("-- Min dist : %f \n", min_dist );

	//-- Draw only "good" matches (i.e. whose distance is less than 3*min_dist )
	std::vector< DMatch > good_matches;

	for( int i = 0; i < descriptors_object.rows; i++ ) {
		if(matches[i].distance < 3*min_dist) {
			good_matches.push_back( matches[i]);
		}
	}
	return good_matches;
}


Mat Match::matGoodMatches(Mat * img1, Mat * img2, bool good) {
	Mat img_object = *img1;
	Mat img_scene = *img2;
	Mat descriptors_object;
	vector<KeyPoint> keypoints_object, keypoints_scene;
	vector<DMatch> good_matches;
	if (good) {
		good_matches = vecGoodMatches(img1, img2, descriptors_object, keypoints_object, keypoints_scene);
	}
	else {
		good_matches = vecMatches(img1, img2, descriptors_object, keypoints_object, keypoints_scene);
	}
	Mat img_matches;
	drawMatches( img_object, keypoints_object, img_scene, keypoints_scene,
			good_matches, img_matches, Scalar::all(-1), Scalar::all(-1),
			vector<char>(), DrawMatchesFlags::NOT_DRAW_SINGLE_POINTS );
	//-- Localize the object
	std::vector<Point2f> obj;
	std::vector<Point2f> scene;

	for( int i = 0; i < good_matches.size(); i++ )
	{
		//-- Get the keypoints from the good matches
		obj.push_back( keypoints_object[ good_matches[i].queryIdx ].pt );
		scene.push_back( keypoints_scene[ good_matches[i].trainIdx ].pt );
	}

	if (obj.size() < 4 || scene.empty()) {
		cerr << "Obj: " << obj.size() << "; Scene: " << scene.size() << endl;
		return img_matches;
	} else {
		Mat H = findHomography( obj, scene, CV_RANSAC );

		//-- Get the corners from the image_1 ( the object to be "detected" )
		std::vector<Point2f> obj_corners(4);
		obj_corners[0] = cvPoint(0,0); obj_corners[1] = cvPoint( img_object.cols, 0 );
		obj_corners[2] = cvPoint( img_object.cols, img_object.rows ); obj_corners[3] = cvPoint( 0, img_object.rows );
		std::vector<Point2f> scene_corners(4);

		perspectiveTransform( obj_corners, scene_corners, H);

		//-- Draw lines between the corners (the mapped object in the scene - image_2 )
		line( img_matches, scene_corners[0] + Point2f( img_object.cols, 0), scene_corners[1] + Point2f( img_object.cols, 0), Scalar(0, 255, 0), 4 );
		line( img_matches, scene_corners[1] + Point2f( img_object.cols, 0), scene_corners[2] + Point2f( img_object.cols, 0), Scalar( 0, 255, 0), 4 );
		line( img_matches, scene_corners[2] + Point2f( img_object.cols, 0), scene_corners[3] + Point2f( img_object.cols, 0), Scalar( 0, 255, 0), 4 );
		line( img_matches, scene_corners[3] + Point2f( img_object.cols, 0), scene_corners[0] + Point2f( img_object.cols, 0), Scalar( 0, 255, 0), 4 );
		/*
	  //-- Show detected matches
	  imshow( "Good Matches & Object detection", img_matches );

	  waitKey(0);*/
		return img_matches;
	}
}

vector<Point2f> Match::sceneCornersGoodMatches(Mat * img1, Mat * img2, bool good) {
	Mat * img_object = img1;
	Mat * img_scene = img2;
	Mat descriptors_object;
	vector<KeyPoint> keypoints_object, keypoints_scene;
	vector<DMatch> good_matches;
	if (good) {
		good_matches = vecGoodMatches(img1, img2, descriptors_object, keypoints_object, keypoints_scene);
	}
	else {
		good_matches = vecMatches(img1, img2, descriptors_object, keypoints_object, keypoints_scene);
	}
	Mat * img_matches;
	drawMatches(*img_object, keypoints_object,
			*img_scene, keypoints_scene,
			good_matches, *img_matches,
			Scalar::all(-1), Scalar::all(-1),
			vector<char>(), DrawMatchesFlags::NOT_DRAW_SINGLE_POINTS );

	//-- Localize the object
	std::vector<Point2f> obj;
	std::vector<Point2f> scene;

	for( int i = 0; i < good_matches.size(); i++ )
	{
		//-- Get the keypoints from the good matches
		obj.push_back( keypoints_object[ good_matches[i].queryIdx ].pt );
		scene.push_back( keypoints_scene[ good_matches[i].trainIdx ].pt );
	}

	if (obj.size() < 4 || scene.empty()) {
		ostringstream oss;
		oss << "No possible Rectangle: Less than 4 corners detected	" << "Obj:	" << obj.size() << "	Scene:	" << scene.size();

		Tools::debugMessage(oss.str());

		//to let the method return something valid, fill a tiny little rectangle:
		std::vector<Point2f> scene_corners/*(4)*/;
		/*scene_corners.push_back(cvPoint(0, 0));
		  scene_corners.push_back(cvPoint(0, 0));
		  scene_corners.push_back(cvPoint(0, 0));
		  scene_corners.push_back(cvPoint(0, 0));*/
		return scene_corners; //img_matches; //out-commented return is invalid
	} else {
		Mat H = findHomography( obj, scene, CV_RANSAC );


		//-- Get the corners from the image_1 ( the object to be "detected" )
		std::vector<Point2f> obj_corners(4);
		obj_corners[0] = cvPoint(0,0); obj_corners[1] = cvPoint( img_object->cols, 0 );
		obj_corners[2] = cvPoint( img_object->cols, img_object->rows ); obj_corners[3] = cvPoint( 0, img_object->rows );
		std::vector<Point2f> scene_corners(4);

		perspectiveTransform( obj_corners, scene_corners, H);

		return scene_corners;
	}
}

double Match::match(fs::path path1, fs::path path2)
{
	Mat img1 = imread(path1.generic_string());
	Mat img2 = imread(path2.generic_string());
	return match(&img1, &img2);
}

//TODO: complete SURF FLANN MATCHER AND TEMPLATE MATCHER AND SSNR (GPU) MATCHER

double Match::match(string path1, string path2)
{
	fs::path path1p = path1;
	fs::path path2p = path2;
	return match(path1p, path2p);
}

double Match::match(Mat *img1, Mat *img2) {
	return 1;
}

bool Match::templateMatch(Mat *img, Mat *templ, int match_method)
{
	/// Source image to display
	Mat img_display;
	img->copyTo( img_display );
	Mat result;

	/// Create the result matrix
	int result_cols =  img->cols - templ->cols + 1;
	int result_rows = img->rows - templ->rows + 1;

	result.create( result_cols, result_rows, CV_32FC1 );

	/// Do the Matching and Normalize
	matchTemplate( *img, *templ, result, match_method );
	normalize( result, result, 0, 1, NORM_MINMAX, -1, Mat() );

	/// Localizing the best match with minMaxLoc
	double minVal; double maxVal; Point minLoc; Point maxLoc;
	Point matchLoc;

	minMaxLoc( result, &minVal, &maxVal, &minLoc, &maxLoc, Mat() );

	/// For SQDIFF and SQDIFF_NORMED, the best matches are lower values. For all the other methods, the higher the better
	if( match_method  == CV_TM_SQDIFF || match_method == CV_TM_SQDIFF_NORMED )
	{ matchLoc = minLoc; }
	else
	{ matchLoc = maxLoc; }

	/// Show me what you got
	rectangle( img_display, matchLoc, Point( matchLoc.x + templ->cols , matchLoc.y + templ->rows ), Scalar::all(0), 2, 8, 0 );
	rectangle( result, matchLoc, Point( matchLoc.x + templ->cols , matchLoc.y + templ->rows ), Scalar::all(0), 2, 8, 0 );

	imshow("Image", img_display);
	imshow("Result", result);
	waitKey(0);
	return true;
}