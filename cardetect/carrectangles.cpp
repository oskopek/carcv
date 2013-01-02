#include "carrectangles.hpp"

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

int main(int argc, const char** argv)
{
	Det d;
	return d.run(argc, argv);
}

int Det::run(int argc, const char** argv)
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
    	/*posPath = fs::absolute(posPath);
    	negPath = fs::absolute(negPath);*/
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
        /* assume it is a text file containing the
         *            list of the image filenames to be processed - one per line */
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
                		 */
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
                			Mat mat1 = imread(one);
                			Mat mat2 = imread(two);

                			Mat detected1 = detectMat(mat1, cascade, scale);
                			Mat detected2 = detectMat(mat2, cascade, scale);
                			vector<Rect> objects1 = detect(mat1, cascade, scale);
                			vector<Rect> objects2 = detect(mat2, cascade, scale);

                			Rect_<int> roi = objects1.front();

                			Mat cropped = mat1(roi);

                			Match m;
                			m.match(mat1, mat2);
                			m.templateMatch(mat1, cropped, CV_TM_CCOEFF_NORMED);
                			m.templateMatch(mat1, cropped, CV_TM_CCOEFF);

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
vector<Rect> Det::detect( Mat& img, CascadeClassifier& cascade, double scale)
{
    vector<Rect> objects;
    Mat gray, smallImg( cvRound (img.rows/scale), cvRound(img.cols/scale), CV_8UC1 );

    cvtColor( img, gray, CV_BGR2GRAY );
    resize( gray, smallImg, smallImg.size(), 0, 0, INTER_LINEAR );
    equalizeHist( smallImg, smallImg );

    cascade.detectMultiScale( smallImg, objects,
                              1.1, 2, 0
                              //|CV_HAAR_FIND_BIGGEST_OBJECT
                              //|CV_HAAR_DO_ROUGH_SEARCH
                              |CV_HAAR_SCALE_IMAGE
                              ,
                              Size(30, 30) );
    return objects;
}

/*
 * Detects objects in img, draws rectangles around them and returns the img
 */
Mat Det::detectMat( Mat& img, CascadeClassifier& cascade, double scale) {

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
 * <b>Prerequisite:</b> If a posdir and negdir arent created, create them!
 */
bool Det::detectAndSort(Mat &img, CascadeClassifier &cascade, double scale, string posdir, string negdir, string filename)
{
	fs::path filenamePath = filename;
	//filenamePath = fs::absolute(filenamePath);
	fs::path posdirPath = posdir;
	//posdirPath = fs::absolute(posdirPath);
	fs::path negdirPath = negdir;
	//negdirPath = fs::absolute(negdirPath);

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
