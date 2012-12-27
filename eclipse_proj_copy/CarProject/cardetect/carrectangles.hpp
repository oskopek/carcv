#include "opencv2/objdetect/objdetect.hpp"
#include "opencv2/highgui/highgui.hpp"
#include "opencv2/imgproc/imgproc.hpp"

using namespace cv;

static void help();
void detectAndSort( Mat&, CascadeClassifier&, CascadeClassifier&, double, bool);
bool detect( Mat&, CascadeClassifier&, CascadeClassifier&, double, bool);
void detectAndDraw( Mat&, CascadeClassifier&, CascadeClassifier&, double, bool);