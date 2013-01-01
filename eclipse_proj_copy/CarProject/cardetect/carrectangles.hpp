#include "opencv2/objdetect/objdetect.hpp"
#include "opencv2/highgui/highgui.hpp"
#include "opencv2/imgproc/imgproc.hpp"

using namespace cv;

static void help();
void detectAndSort(Mat&, CascadeClassifier&, double);
int detect(Mat&, CascadeClassifier&, double);
void detectAndDraw(Mat&, CascadeClassifier&, double);
