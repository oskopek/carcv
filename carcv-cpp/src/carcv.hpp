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

#include "fileio.hpp"

#include "opencv2/objdetect/objdetect.hpp"

using namespace std;
using namespace cv;

/*
 * HAAR_SURF = use Viola-Jones detection and match it with SURF
 * HAAR_TMPLMATCH = use Viola-Jones detection and match it with template matching
 */
enum method {HAAR_SURF = 1, HAAR_TMPLMATCH = 2};

/*
 * SP_ID_DIFF=Difference of biggest frame id - smallest frame id in given list, divided by framerate
 *
 * SP_SUM=Count frames in given list, divided by framerate
 */
enum speed_method {SP_ID_DIFF = 1, SP_SUM = 2};

class CarCV {
public:

	static list<CarImg> detect_sortPOS_AND_NEG(list<CarImg> &imgList, CascadeClassifier &cascade, list<CarImg> *negList, const double scale);

	//static list<string> sortPOS_AND_NEG(list<string> &imgList, CascadeClassifier &cascade, list<CarImg> *negList); //unimplemented, joined with detect()

	/*
	 * Supply a list of positive images, and a dir where to place cars
	 */
	static list<list<CarImg> > sortUnique(list<CarImg> &posCarImgList, CascadeClassifier &cascade, const double PROBABILITYCONST);

	static list<CarImg> inSpeedBox(list<CarImg> &carLineList, CascadeClassifier &cascade, Rect &speedBox, const double scale);

	static double calcSpeed(list<CarImg> clist, int speed_method, double framerate, double real_measuring_length);

};