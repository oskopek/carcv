#include <iostream>
#include <iterator>
#include <stdio.h>

#include "carrectangles.hpp"


using namespace std;
using namespace cv;

static void help()
{
    cout << "\nThis program demonstrates the cascade recognizer. Now you can use Haar or LBP features.\n"
    "This classifier can recognize many kinds of rigid objects, once the appropriate classifier is trained.\n"
    "It's most known use is for cars.\n"
    "Usage:\n"
    "./cardtc [--cascade=<cascade_path> this is the primary trained classifier such as cars]\n"
    "   [--nested-cascade[=nested_cascade_path this an optional secondary classifier such as headlights]]\n"
    "   [--scale=<image scale greater or equal to 1, try 1.3 for example>]\n"
    "   [--try-flip]\n"
    "   [filename|camera_index]\n\n"
    "example call:\n"
    "./facedetect --cascade=\"haarcascade_cars.xml\" --nested-cascade=\"haarcascade_headlights.xml\" --scale=1.3\n\n"
    "During execution:\n\tHit any key to quit.\n"
    "\tUsing OpenCV version " << CV_VERSION << "\n" << endl;
}

string cascadeName = "../../data/haarcascades/haarcascade_frontalface_alt.xml";
string nestedCascadeName = "../../data/haarcascades/haarcascade_eye_tree_eyeglasses.xml";
Ptr<CvHaarClassifierCascade> oldCascade;
int main( int argc, const char** argv )
{
    Mat frame, frameCopy, image;
    const string scaleOpt = "--scale=";
    size_t scaleOptLen = scaleOpt.length();
    const string cascadeOpt = "--cascade=";
    size_t cascadeOptLen = cascadeOpt.length();
    const string nestedCascadeOpt = "--nested-cascade";
    size_t nestedCascadeOptLen = nestedCascadeOpt.length();
    const string tryFlipOpt = "--try-flip";
    size_t tryFlipOptLen = tryFlipOpt.length();
    string inputName;
    bool tryflip = false;
    
    help();
    
    CascadeClassifier cascade, nestedCascade;
    double scale = 1;
    
    for( int i = 1; i < argc; i++ )
    {
        cout << "Processing " << i << " " <<  argv[i] << endl;
        if( cascadeOpt.compare( 0, cascadeOptLen, argv[i], cascadeOptLen ) == 0 )
        {
            cascadeName.assign( argv[i] + cascadeOptLen );
            cout << "  from which we have cascadeName= " << cascadeName << endl;
        }
        else if( nestedCascadeOpt.compare( 0, nestedCascadeOptLen, argv[i], nestedCascadeOptLen ) == 0 )
        {
            if( argv[i][nestedCascadeOpt.length()] == '=' )
                nestedCascadeName.assign( argv[i] + nestedCascadeOpt.length() + 1 );
            if( !nestedCascade.load( nestedCascadeName ) )
                cerr << "WARNING: Could not load classifier cascade for nested objects" << endl;
        }
        else if( scaleOpt.compare( 0, scaleOptLen, argv[i], scaleOptLen ) == 0 )
        {
            if( !sscanf( argv[i] + scaleOpt.length(), "%lf", &scale ) || scale < 1 )
                scale = 1;
            cout << " from which we read scale = " << scale << endl;
        }
        else if( tryFlipOpt.compare( 0, tryFlipOptLen, argv[i], tryFlipOptLen ) == 0 )
        {
            tryflip = true;
            cout << " will try to flip image horizontally to detect assymetric objects\n";
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
    cout << "test1" << endl;
    //oldCascade = Ptr<CvHaarClassifierCascade>((CvHaarClassifierCascade*) cvLoad(cascadeName.c_str(), 0, 0, 0));//
    cout << "test2" << endl;
    //cvNamedWindow( "result", 1 );
    
    cout << "In image read" << endl;
    if( !image.empty() )
    {
        detectAndDraw( image, cascade, nestedCascade, scale, tryflip );
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
            while( fgets( buf, 1000, f ) )
            {
                int len = (int)strlen(buf), c;
                while( len > 0 && isspace(buf[len-1]) )
                    len--;
                buf[len] = '\0';
                cout << "file " << buf << endl;
                image = imread( buf, 1 );
                if( !image.empty() )
                {
                    /*
                                            detectAndDraw( image, cascade, nestedCascade, scale, tryflip );
                                            c = waitKey(0);
                                            if( c == 27 || c == 'q' || c == 'Q' )
                                                break;
                     */
                    detect(image, cascade, nestedCascade, scale, tryflip);
                }
                else
                {
                    cerr << "Aw snap, couldn't read image " << buf << endl;
                }
            }
            fclose(f);
        }
    }
    
    //cvDestroyWindow("result");
    
    return 0;
}


void detectAndDraw( Mat& img, CascadeClassifier& cascade,
                    CascadeClassifier& nestedCascade,
                    double scale, bool tryflip )
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
    if( tryflip )
    {
        flip(smallImg, smallImg, 1);
        cascade.detectMultiScale( smallImg, objects2,
                                  1.1, 2, 0
                                  //|CV_HAAR_FIND_BIGGEST_OBJECT
                                  //|CV_HAAR_DO_ROUGH_SEARCH
                                  |CV_HAAR_SCALE_IMAGE
                                  ,
                                  Size(30, 30) );
        for( vector<Rect>::const_iterator r = objects2.begin(); r != objects2.end(); r++ )
        {
            objects.push_back(Rect(smallImg.cols - r->x - r->width, r->y, r->width, r->height));
        }
    }
    t = (double)cvGetTickCount() - t;
    printf( "detection time = %g ms\n", t/((double)cvGetTickFrequency()*1000.) );
    for( vector<Rect>::const_iterator r = objects.begin(); r != objects.end(); r++, i++ )
    {
        Mat smallImgROI;
        vector<Rect> nestedObjects;
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
            if( nestedCascade.empty() )
                continue;
            smallImgROI = smallImg(*r);
        nestedCascade.detectMultiScale( smallImgROI, nestedObjects,
                                        1.1, 2, 0
                                        //|CV_HAAR_FIND_BIGGEST_OBJECT
                                        //|CV_HAAR_DO_ROUGH_SEARCH
                                        //|CV_HAAR_DO_CANNY_PRUNING
                                        |CV_HAAR_SCALE_IMAGE
                                        ,
                                        Size(30, 30) );
        for( vector<Rect>::const_iterator nr = nestedObjects.begin(); nr != nestedObjects.end(); nr++ )
        {
            center.x = cvRound((r->x + nr->x + nr->width*0.5)*scale);
            center.y = cvRound((r->y + nr->y + nr->height*0.5)*scale);
            radius = cvRound((nr->width + nr->height)*0.25*scale);
            circle( img, center, radius, color, 3, 8, 0 );
        }
    }
    cv::imshow( "result", img );
}

/*
 * Returns true if detected object is in frame, false if not.
 * 
 */
bool detect( Mat& img, CascadeClassifier& cascade,
                    CascadeClassifier& nestedCascade,
                    double scale, bool tryflip )
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
    double scaleFactor = 1.1;
    int minNeighbors = 1;
    int flags = 0 | CV_HAAR_SCALE_IMAGE;
    Size minObjectSize = Size(30, 30);
    Size maxObjectSize = Size(5000, 5000);
    vector<int> fakeLevels;
    vector<double> fakeWeights;
    bool outputRejectLevels = false;
    CvSeq* rectangles;
    Mat _image = smallImg;
    MemStorage storage(cvCreateMemStorage(0));
    CascadeClassifier _casc = cascade;
//    CvHaarClassifierCascade* oldCascade = Ptr<CvHaarClassifierCascade>((CvHaarClassifierCascade*) cvLoad(filename.c_str(), 0, 0, 0));;
    cascade.detectMultiScale( smallImg, objects, fakeLevels, fakeWeights, scaleFactor, minNeighbors, flags, minObjectSize, maxObjectSize, outputRejectLevels);
    int beforeTotal = rectangles->total;
    cerr << beforeTotal << endl;
    rectangles = cvHaarDetectObjectsForROC( &_image, oldCascade, storage, fakeLevels, fakeWeights, scaleFactor, minNeighbors, flags, minObjectSize, maxObjectSize, outputRejectLevels);
    cerr << rectangles->total << endl;
    if (rectangles->total>beforeTotal) {
        cout << "This img is positive" << endl;
    }
    else {
        cout << "This img is negative" << endl;
    }
    /*
    //
    if( tryflip )
    {
        flip(smallImg, smallImg, 1);
        cascade.detectMultiScale( smallImg, objects2,
                                  1.1, 2, 0
                                  //|CV_HAAR_FIND_BIGGEST_OBJECT
                                  //|CV_HAAR_DO_ROUGH_SEARCH
                                  |CV_HAAR_SCALE_IMAGE
                                  ,
                                  Size(30, 30) );
        for( vector<Rect>::const_iterator r = objects2.begin(); r != objects2.end(); r++ )
        {
            objects.push_back(Rect(smallImg.cols - r->x - r->width, r->y, r->width, r->height));
        }
    }
    t = (double)cvGetTickCount() - t;
    printf( "detection time = %g ms\n", t/((double)cvGetTickFrequency()*1000.) );
    for( vector<Rect>::const_iterator r = objects.begin(); r != objects.end(); r++, i++ )
    {
        Mat smallImgROI;
        vector<Rect> nestedObjects;
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
            if( nestedCascade.empty() )
                continue;
            smallImgROI = smallImg(*r);
        nestedCascade.detectMultiScale( smallImgROI, nestedObjects,
                                        1.1, 2, 0
                                        //|CV_HAAR_FIND_BIGGEST_OBJECT
                                        //|CV_HAAR_DO_ROUGH_SEARCH
                                        //|CV_HAAR_DO_CANNY_PRUNING
                                        |CV_HAAR_SCALE_IMAGE
                                        ,
                                        Size(30, 30) );
        for( vector<Rect>::const_iterator nr = nestedObjects.begin(); nr != nestedObjects.end(); nr++ )
        {
            center.x = cvRound((r->x + nr->x + nr->width*0.5)*scale);
            center.y = cvRound((r->y + nr->y + nr->height*0.5)*scale);
            radius = cvRound((nr->width + nr->height)*0.25*scale);
            circle( img, center, radius, color, 3, 8, 0 );
        }
    }
    cv::imshow( "result", img );
    */
}


void detectAndSort( Mat& img, CascadeClassifier& cascade,
                    CascadeClassifier& nestedCascade,
                    double scale, bool tryflip )
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
    if( tryflip )
    {
        flip(smallImg, smallImg, 1);
        cascade.detectMultiScale( smallImg, objects2,
                                  1.1, 2, 0
                                  //|CV_HAAR_FIND_BIGGEST_OBJECT
                                  //|CV_HAAR_DO_ROUGH_SEARCH
                                  |CV_HAAR_SCALE_IMAGE
                                  ,
                                  Size(30, 30) );
        for( vector<Rect>::const_iterator r = objects2.begin(); r != objects2.end(); r++ )
        {
            objects.push_back(Rect(smallImg.cols - r->x - r->width, r->y, r->width, r->height));
        }
    }
    t = (double)cvGetTickCount() - t;
    printf( "detection time = %g ms\n", t/((double)cvGetTickFrequency()*1000.) );
    for( vector<Rect>::const_iterator r = objects.begin(); r != objects.end(); r++, i++ )
    {
        Mat smallImgROI;
        vector<Rect> nestedObjects;
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
            if( nestedCascade.empty() )
                continue;
            smallImgROI = smallImg(*r);
        nestedCascade.detectMultiScale( smallImgROI, nestedObjects,
                                        1.1, 2, 0
                                        //|CV_HAAR_FIND_BIGGEST_OBJECT
                                        //|CV_HAAR_DO_ROUGH_SEARCH
                                        //|CV_HAAR_DO_CANNY_PRUNING
                                        |CV_HAAR_SCALE_IMAGE
                                        ,
                                        Size(30, 30) );
        for( vector<Rect>::const_iterator nr = nestedObjects.begin(); nr != nestedObjects.end(); nr++ )
        {
            center.x = cvRound((r->x + nr->x + nr->width*0.5)*scale);
            center.y = cvRound((r->y + nr->y + nr->height*0.5)*scale);
            radius = cvRound((nr->width + nr->height)*0.25*scale);
            circle( img, center, radius, color, 3, 8, 0 );
        }
    }
    cv::imshow( "result", img );
}
