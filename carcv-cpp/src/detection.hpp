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