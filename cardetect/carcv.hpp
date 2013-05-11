/*
 * CarCV - Car recognizing and speed calculating platform
 *
 * Authors:
 * Copyright (C) 2012-2013, Ondrej Skopek
 *
 * All rights reserved.
 */

#include "tools.hpp"
#include "fileio.hpp"

#include "opencv2/objdetect/objdetect.hpp"

namespace fs = boost::filesystem;
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

	static void help();

	static void run(fs::path &imgListPath, int method, CascadeClassifier &cascade, Rect speedBox);

	static int starter(int argc, char** argv);

	static list<CarImg> detect_sortPOS_AND_NEG(list<CarImg> &imgList, CascadeClassifier &cascade, list<CarImg> *negList);

	//static list<string> sortPOS_AND_NEG(list<string> &imgList, CascadeClassifier &cascade, list<CarImg> *negList); //unimplemented, joined with detect()

	/*
	 * Supply a list of positive images, and a dir where to place cars
	 */
	static list<list<CarImg> > sortUnique(list<CarImg> &posCarImgList, CascadeClassifier &cascade, const double PROBABILITYCONST);

	static list<CarImg> inSpeedBox(list<CarImg> &carLineList, CascadeClassifier &cascade, Rect &speedBox);

	static double calcSpeed(list<CarImg> clist, int speed_method, double framerate, double real_measuring_length);

};
