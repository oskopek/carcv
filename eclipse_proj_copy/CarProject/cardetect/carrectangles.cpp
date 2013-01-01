#include <iostream>
#include <iterator>
#include <stdio.h>

#include "carrectangles.hpp"

#define RESET_COLOR "\e[m"
#define MAKE_RED "\e[31m"

using namespace std;
using namespace cv;

static void help()
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

int main( int argc, const char** argv )
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

    if (methodName == "DETECTDRAWDEMO") {
    	cvNamedWindow( "result", 1 );
    }

    cout << "In image read" << endl;
    if( !image.empty() )
    {
        detectAndDraw( image, cascade, scale);
        waitKey(0);
    }
    else if( !inputName.empty() )
    {
        /* assume it is a text file containing the
         *            list of the image filenames to be processed - one per line */
        FILE* f = fopen( inputName.c_str(), "rt" );
        if( f )
        {
            char buf[1000+1];
            string dashes = "---------------------------------------------------------";
            cout << dashes << endl;
            cout << "|	" << "Filename" << "	|   " << "Detected?" << "	| " << "Object#"<< "	|" << endl;
            while( fgets( buf, 1000, f ) )
            {
                int len = (int)strlen(buf);
                while( len > 0 && isspace(buf[len-1]) )
                    len--;
                buf[len] = '\0';
                //cout << "file " << buf << endl; //Silencing for output
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
                		int detectedN = detect(image, cascade, scale);
                		string detected;
                		if (detectedN > 0) {
                			detected = "true";
                			cout << MAKE_RED << "|	" << buf << "	|	" << detected << "	|	" << detectedN << "	|" << RESET_COLOR <<  endl;
                		}
                		else {
                			detected = "false";
                			cout << "|	" << buf << "	|	" << detected << "	|	" << detectedN << "	|" << endl;
                		}
                	} else if(methodName == "DETECTSORTDEMO") {
                		cerr << "METHOD NOT YET IMPLEMENTED: " << methodName << endl;
                	} else if(methodName == "DETECTDRAWDEMO") {
                		cerr << "METHOD NOT YET IMPLEMENTED: " << methodName << endl;
                	}
                	else {
                		cerr << "NO SUCH METHOD: " << methodName << endl;
                	}
                }
                else
                {
                    //cerr << "Aw snap, couldn't read image " << buf << endl; //Silencing for output, just skip it
                }
            }
            cout << dashes << endl;
            fclose(f);
        }
    }

    if (methodName == "DETECTDRAWDEMO") {
        	cvDestroyWindow("result");
    }
    return 0;
}


void detectAndDraw( Mat& img, CascadeClassifier& cascade, double scale)
{
    int i = 0;
    double t = 0;
    vector<Rect> objects, objects2;
    const static Scalar colors[] =  { CV_RGB(0,0,255),
    CV_RGB(0,128,255),
    CV_RGB(0,255,255),
    CV_RGB(0,255,0),
    CV_RGB(255,128,0),
    CV_RGB(255,255,0),
    CV_RGB(255,0,0),
    CV_RGB(255,0,255)} ;
    Mat gray, smallImg( cvRound (img.rows/scale), cvRound(img.cols/scale), CV_8UC1 );
    
    cvtColor( img, gray, CV_BGR2GRAY );
    resize( gray, smallImg, smallImg.size(), 0, 0, INTER_LINEAR );
    equalizeHist( smallImg, smallImg );
    
    t = (double)cvGetTickCount();
    cascade.detectMultiScale( smallImg, objects,
                              1.1, 2, 0
                              //|CV_HAAR_FIND_BIGGEST_OBJECT
                              //|CV_HAAR_DO_ROUGH_SEARCH
                              |CV_HAAR_SCALE_IMAGE
                              ,
                              Size(30, 30) );

    t = (double)cvGetTickCount() - t;
    printf( "detection time = %g ms\n", t/((double)cvGetTickFrequency()*1000.) );
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
        else
            rectangle( img, cvPoint(cvRound(r->x*scale), cvRound(r->y*scale)),
                       cvPoint(cvRound((r->x + r->width-1)*scale), cvRound((r->y + r->height-1)*scale)),
                       color, 3, 8, 0);

    }
    cv::imshow( "result", img );
}

/*
 * Returns true if detected object is in frame, false if not.
 * 
 */
int detect( Mat& img, CascadeClassifier& cascade, double scale)
{
	    //double t = 0;
	    vector<Rect> objects;
	    Mat gray, smallImg( cvRound (img.rows/scale), cvRound(img.cols/scale), CV_8UC1 );

	    cvtColor( img, gray, CV_BGR2GRAY );
	    resize( gray, smallImg, smallImg.size(), 0, 0, INTER_LINEAR );
	    equalizeHist( smallImg, smallImg );

	    //t = (double)cvGetTickCount();
	    cascade.detectMultiScale( smallImg, objects,
	                              1.1, 2, 0
	                              //|CV_HAAR_FIND_BIGGEST_OBJECT
	                              //|CV_HAAR_DO_ROUGH_SEARCH
	                              |CV_HAAR_SCALE_IMAGE
	                              ,
	                              Size(30, 30) );
	    //t = (double)cvGetTickCount() - t;
	    //printf( "detection time = %g ms\n", t/((double)cvGetTickFrequency()*1000.) );

	    return objects.size();
}


void detectAndSort( Mat& img, CascadeClassifier& cascade, double scale)
{
    int i = 0;
    double t = 0;
    vector<Rect> objects, objects2;
    const static Scalar colors[] =  { CV_RGB(0,0,255),
    CV_RGB(0,128,255),
    CV_RGB(0,255,255),
    CV_RGB(0,255,0),
    CV_RGB(255,128,0),
    CV_RGB(255,255,0),
    CV_RGB(255,0,0),
    CV_RGB(255,0,255)} ;
    Mat gray, smallImg( cvRound (img.rows/scale), cvRound(img.cols/scale), CV_8UC1 );

    cvtColor( img, gray, CV_BGR2GRAY );
    resize( gray, smallImg, smallImg.size(), 0, 0, INTER_LINEAR );
    equalizeHist( smallImg, smallImg );

    t = (double)cvGetTickCount();
    cascade.detectMultiScale( smallImg, objects,
                              1.1, 2, 0
                              //|CV_HAAR_FIND_BIGGEST_OBJECT
                              //|CV_HAAR_DO_ROUGH_SEARCH
                              |CV_HAAR_SCALE_IMAGE
                              ,
                              Size(30, 30) );


    t = (double)cvGetTickCount() - t;
    printf( "detection time = %g ms\n", t/((double)cvGetTickFrequency()*1000.) );
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
        else
            rectangle( img, cvPoint(cvRound(r->x*scale), cvRound(r->y*scale)),
                       cvPoint(cvRound((r->x + r->width-1)*scale), cvRound((r->y + r->height-1)*scale)),
                       color, 3, 8, 0);

    }
    cv::imshow( "result", img );
}
