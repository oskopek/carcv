#include "det.hpp"
#include "match.hpp"

#include <cmath>

#define RESET_COLOR "\e[m"
#define MAKE_RED "\e[31m"

using namespace std;
using namespace cv;
namespace fs = boost::filesystem;

void Det::help()
{
    cout << "\nThis program demonstrates the cascade recognizer. Now you can use Haar or LBP features.\n"
    "This classifier can recognize many kinds of rigid objects, once the appropriate classifier is trained.\n"
    "It's most known use is for cars.\n"
    "Usage:\n"
    "./carrect [--cascade=<cascade_path> this is the primary trained classifier such as cars]\n"
    //"   [--nested-cascade[=nested_cascade_path this an optional secondary classifier such as headlights]]\n"
    "   [--scale=<image scale greater or equal to 1, try 1.3 for example>]\n"
    //"   [--try-flip]\n"
    "   [--method=<DETECTDEMO, DETECTSORTDEMO, DETECTDRAWDEMO>]"
    "   [list of images]\n\n"
    "example call:\n"
    "./carrect --cascade=\"haarcascade_cars.xml\" --scale=1.3 list.txt\n\n"
    "During execution:\n\tHit any key to quit.\n"
    "\tUsing OpenCV version " << CV_VERSION << "\n" << endl;
}

string cascadeName = "/home/odenkos/recttest.xml";
string methodName = "DETECTDEMO";
string posdir = "pos";
string negdir = "neg";
string one, two;

string windowName = "result";

const static Scalar colors[] =  { 	CV_RGB(0,0,255),
									CV_RGB(0,128,255),
									CV_RGB(0,255,255),
									CV_RGB(0,255,0),
									CV_RGB(255,128,0),
									CV_RGB(255,255,0),
									CV_RGB(255,0,0),
									CV_RGB(255,0,255)};

/*int Det::run(int argc, const char** argv)
{
    Mat frame, frameCopy, image;
    const string scaleOpt = "--scale=";
    size_t scaleOptLen = scaleOpt.length();
    const string cascadeOpt = "--cascade=";
    size_t cascadeOptLen = cascadeOpt.length();
    const string methodOpt = "--method=";
    size_t methodOptLen = methodOpt.length();
    string inputName;

    help();

    CascadeClassifier cascade;
    double scale = 1;

    for( int i = 1; i < argc; i++ )
    {
        cout << "Processing " << i << " " <<  argv[i] << endl;
        if( cascadeOpt.compare( 0, cascadeOptLen, argv[i], cascadeOptLen ) == 0 )
        {
            cascadeName.assign( argv[i] + cascadeOptLen );
            cout << "  from which we have cascadeName=" << cascadeName << endl;
        }
        else if( scaleOpt.compare( 0, scaleOptLen, argv[i], scaleOptLen ) == 0 )
        {
            if( !sscanf( argv[i] + scaleOpt.length(), "%lf", &scale ) || scale < 1 )
                scale = 1;
            cout << " from which we read scale = " << scale << endl;
        }
        else if( methodOpt.compare( 0, methodOptLen, argv[i], methodOptLen ) == 0 )
        {
        	methodName.assign( argv[i] + methodOptLen );
        	cout << "  from which we have method=" << methodName << endl;
        }
        else if( argv[i][0] == '-' )
        {
            cerr << "WARNING: Unknown option %s" << argv[i] << endl;
        }
        else
            inputName.assign( argv[i] );
    }

    if( !cascade.load( cascadeName ) )
    {
        cerr << "ERROR: Could not load classifier cascade" << endl;
        help();
        return -1;
    }

    if (methodName == "DETECTDRAWDEMO" || methodName == "DETECTDRAWDEMOFAST") {
    	cvNamedWindow(windowName.c_str(), 1);
    } else if (methodName == "DETECTSORTDEMO") {
    	fs::path posPath = posdir;
    	fs::path negPath = negdir;
    	if (!fs::exists(posPath)) {
    		cout << "Creating positive image directory: " << posPath << endl;
    		fs::create_directory(posPath);
    	}
    	if (!fs::exists(negPath)) {
    		cout << "Creating negative image directory: " << negPath << endl;
    		fs::create_directory(negPath);
    	}
    } else if (methodName == "DETECTMATCHDEMO") {
    	one = "";
    	two = "";
    }

    cout << "In image read" << endl;
    if( !image.empty() )
    {
        detectAndDraw( image, cascade, scale, windowName);
        waitKey(0);
    }
    else if( !inputName.empty() )
    {
    	string dashes = "---------------------------------------------------------";
    	if (methodName == "DETECTDEMO") {
    		cout << dashes << endl;
    		cout << "|	" << "Filename" << "	|   " << "Detected?" << "	| " << "Object#"<< "	|" << endl;
    	}
        FILE* f = fopen( inputName.c_str(), "rt" );
        if( f )
        {
            char buf[1000+1];
            while( fgets( buf, 1000, f ) )
            {
                int len = (int)strlen(buf);
                while( len > 0 && isspace(buf[len-1]) )
                    len--;
                buf[len] = '\0';

                if (methodName != "DETECTDEMO") {
                	//cout << "file " << buf << endl; //Silencing for output
            	}

                image = imread( buf, 1 );
                if( !image.empty() )
                {
                	if(methodName == "DETECTDEMO") {
                		/* //not used right now
                                            	detectAndDraw( image, cascade, nestedCascade, scale, tryflip );
                                            	c = waitKey(0);
                                            	if( c == 27 || c == 'q' || c == 'Q' )
                                                	break;
                		 *\/
                		int detectedN = countDetected(image, cascade, scale);
                		string detected = (detectedN > 0 ? "true" : "false");
                		cout << "|	" << buf << "	|	" << detected << "	|	" << detectedN << "	|" << endl;

                	} else if(methodName == "DETECTSORTDEMO") {
                		//cerr << "METHOD NOT YET IMPLEMENTED: " << methodName << endl;


                		bool succes = detectAndSort(image, cascade, scale, "pos", "neg", buf);
                		if (succes) {
                			//cout << "Images were successfully sorted and moved" << endl;
                		} else {
                			//cout << "Images were NOT successfully sorted and moved\nWARNING: SOME MAY HAVE ALREADY BEEN MOVED, SOME DATALOSS MAY HAVE BEEN CREATED" << endl;
                		}
                	} else if(methodName == "DETECTDRAWDEMO") {
                		detectAndDraw(image, cascade, scale, windowName);
                		char c = waitKey(0);
                		if( c == 27 || c == 'q' || c == 'Q' )
                			break;
                	} else if(methodName == "DETECTDRAWDEMOFAST") {
                		if(isDetected(image, cascade, scale)) {
                			detectAndDraw(image, cascade, scale, windowName);
                			char c = waitKey(0);
                			if( c == 27 || c == 'q' || c == 'Q' ) {
                				break;
                			}
                		}
                	} else if (methodName == "DETECTMATCHDEMO") {
                		if (!one.empty()) {
                			two = buf;
                			Mat mat1 = imread("1.bmp"); //carcrop
                			Mat mat2 = imread("2.bmp"); //samecar
                			Mat mat3 = imread("3.bmp"); //diffcar

                			//Mat detected1 = detectMat(mat1, cascade, scale);
                			//Mat detected2 = detectMat(mat2, cascade, scale);

                			testCropping(mat1, mat2, cascade, scale);
                			testCropping(mat1, mat3, cascade, scale);

                			return 0;
                			//m.templateMatch(mat1, cropped, CV_TM_CCOEFF_NORMED);
                			//m.templateMatch(mat1, cropped, CV_TM_CCOEFF);

                			return 0;
                		} else {
                			one = buf;
                		}
                	}
                	else {
                		cerr << "NO SUCH METHOD: " << methodName << endl;
                		break;
                	}
                }
                else
                {
                    cerr << "Couldn't read image: " << buf << endl; //Silencing for output, just skip it
                }
            }

            if (methodName == "DETECTDEMO") {
            	cout << dashes << endl;
            }

            fclose(f);
        }
    }

    if (methodName == "DETECTDRAWDEMO" || methodName == "DETECTDRAWDEMOFAST") {
        	cvDestroyWindow(windowName.c_str());
    }
    return 0;
}
*/

/*
 * Detect an image with Mat#detect(Mat&, CascadeClassifier&, double)
 * an shows it in a window named windowName
 */
void Det::detectAndDraw( Mat& img, CascadeClassifier& cascade, double scale, string windowName)
{
    Mat result = detectMat(img, cascade, scale);
    cv::imshow(windowName, result);
}

/*
 * Detects objects in img and returns a vector of rectangles of object regions
 */
vector<Rect> Det::detect(Mat &img, CascadeClassifier &cascade, double scale)
{
    vector<Rect> objects;
    Mat gray, smallImg( cvRound (img.rows/scale), cvRound(img.cols/scale), CV_8UC1 );

    cvtColor(img, gray, CV_BGR2GRAY);
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
double Det::probability(Mat &imga, Mat &imgb, CascadeClassifier &cascade, const int scaleLO, const int scaleHI) { //implemented according to whiteboard, needs optimization

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
		cropped = Det::crop(imga, detectedA,dscale);
		scene_corners = Match::sceneCornersGoodMatches(cropped, imgb, true);
		sceneCornersRect = minAreaRect(scene_corners);

		Size2f sizeSCR = Size_<float>(sceneCornersRect.size);
		Size2f sizeCropped = Size_<float>((float) cropped.cols, (float) cropped.rows);

		if(Det::evaluatef(sizeSCR.area(), sizeCropped.area())) {
			probTrue++;
			//cout << "TRUE:	"<< "SizeSCR=" << sizeSCR.area() << "	;SizeCropped=" << sizeCropped.area() << endl;
		}

		counterAll++;
	}

	dscale = (double) scaleLO/100;
	for (int i=scaleLO; i < scaleHI; i++) {
		cropped = Det::crop(imgb, detectedB,dscale);
		scene_corners = Match::sceneCornersGoodMatches(cropped, imga, true);
		sceneCornersRect = minAreaRect(scene_corners);

		Size2f sizeSCR = Size_<float>(sceneCornersRect.size);
		Size2f sizeCropped = Size_<float>((float) cropped.cols, (float) cropped.rows);

		if(Det::evaluatef(sizeSCR.area(), sizeCropped.area())) {
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
Mat Det::detectMat(Mat &img, CascadeClassifier &cascade, double scale) {

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
	            circle( img, center, radius, color, 3, 8, 0 );
	        }
	        else {
	            rectangle( img, cvPoint(cvRound(r->x*scale), cvRound(r->y*scale)),
	                       cvPoint(cvRound((r->x + r->width-1)*scale), cvRound((r->y + r->height-1)*scale)),
	                       color, 3, 8, 0);
	        }
	}
	return img;
}

/*
 * Returns true if detected object is in img, false if not.
 *
 */
bool Det::isDetected(Mat &img, CascadeClassifier &cascade, double scale)
{
	return (countDetected(img, cascade, scale) > 0 ? true : false);
}

/*
 * Returns the number of detected objects in img
 */
int Det::countDetected(Mat &img, CascadeClassifier &cascade, double scale)
{
	return detect(img, cascade, scale).size();
}

/*
 * Returns true if successfully sorted
 */
bool Det::detectAndSort(Mat &img, CascadeClassifier &cascade, double scale, string posdir, string negdir, string filename)
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
Mat Det::crop(Mat &img, Rect &roi, double &scale) {
	Rect scaledROI = scaleRect(roi, scale);
	Mat ret = img(scaledROI);
	return ret;

}

/*
 * Calculates the center point of Rect r
 */
Point2d Det::center(Rect r) {
	Point2d p;
	p.x = r.x+((r.width)/2);
	p.y = r.y+((r.height)/2);
	return p;
}

/*
 * Scales the given rect to keep ~ the same center point, just be scaled
 */
Rect Det::scaleRect(Rect roi, double scale) {
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
void Det::coutp(string name, Rect roi) {
	cout << name.c_str() << ":		" << "Point[" << roi.x << ", " << roi.y << "];height=" << roi.height << ";width=" << roi.width << endl;
}

/*
void Det::testCropping(Mat &forcrop, Mat &comp, CascadeClassifier &cascade, double &scale) {
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

bool Det::evaluatef(const float a, const float b) {
	float absA = fabsf(a);
	float absB = fabsf(b);
	float absdiff = absA > absB ? absA-absB : absB-absA ;

	if (absdiff < 2000) {
		return true;
	}
	else {
		return false;
	}
}
