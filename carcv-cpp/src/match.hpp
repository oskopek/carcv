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

#include <boost/filesystem.hpp>

#include "opencv2/features2d/features2d.hpp"
#include "opencv2/core/core.hpp"

using namespace std;
using namespace cv;

namespace fs = boost::filesystem;

/*
 * Matching class Match
 */
class Match {
public:
	static vector<DMatch> vecMatches(Mat * img1, Mat * img2, Mat &descriptors_object, vector<KeyPoint> &keypoints_object, vector<KeyPoint> &keypoints_scene);
	static double match(fs::path path1, fs::path path2);
	static double match(string path1, string path2);
	static double match(Mat * img1, Mat * img2);
	static vector<DMatch> vecGoodMatches(Mat * img1, Mat * img2, Mat &descriptors_object, vector<KeyPoint> &keypoints_object, vector<KeyPoint> &keypoints_scene);
	static Mat matGoodMatches(Mat * img1, Mat * img2, bool good);
	static bool templateMatch(Mat * img, Mat * templ, int match_method);
	static vector<Point2f> sceneCornersGoodMatches(Mat * img1, Mat * img2, bool good);
};