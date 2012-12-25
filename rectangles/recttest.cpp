/******************detect.c*************************/
/*
opencv implementation of object detection using haar classifier.

author: achu_wilson@rediffmail.com
*/


#include <stdio.h>
#include "cv.h"
#include "highgui.h"

CvHaarClassifierCascade *cascade;
CvMemStorage            *storage;

void detect( IplImage *img );

int main( int argc, char** argv )
{
    IplImage  *frame;
    int       key;
    char      *filename = "/home/odenkos/recttest.xml"; //put the name of your classifier here
    char      *fileImg = "/home/odenkos/recttest.jpeg";
    std::cout << "lol1";
    frame=cvLoadImage(fileImg, 1);
    std::cout << "lol";

    cascade = ( CvHaarClassifierCascade* ) cvLoad( filename, 0, 0, 0 );
    storage = cvCreateMemStorage(0);
    std::cout << "lol2";

    assert( cascade && storage);

    cvNamedWindow("rectangle", 1);

        detect(frame);

        key = cvWaitKey(50);

    cvReleaseImage(&frame);
    cvDestroyWindow("rectangle");
    cvReleaseHaarClassifierCascade(&cascade);
    cvReleaseMemStorage(&storage);

    return 0;
}

void detect(IplImage *img)
{
    int i;

    CvSeq *object = cvHaarDetectObjects(
            img,
            cascade,
            storage,
            1.5, //-------------------SCALE FACTOR
            2,//------------------MIN NEIGHBOURS
            1,//----------------------
                      // CV_HAAR_DO_CANNY_PRUNING,
            cvSize( 30,30), // ------MINSIZE
            cvSize(640,480) );//---------MAXSIZE

    for( i = 0 ; i < ( object ? object->total : 0 ) ; i++ ) 
        {
            CvRect *r = ( CvRect* )cvGetSeqElem( object, i );
            cvRectangle( img,
                     cvPoint( r->x, r->y ),
                     cvPoint( r->x + r->width, r->y + r->height ),
                     CV_RGB( 255, 0, 0 ), 2, 8, 0 );
                    
            //printf("%d,%d\nnumber =%d\n",r->x,r->y,object->total);


        }

    cvShowImage( "rectangle", img );
}
