#include "opencv2/objdetect/objdetect.hpp"
#include "opencv2/highgui/highgui.hpp"
#include "opencv2/imgproc/imgproc.hpp"

using namespace cv;

static void help();
bool detectAndSort(Mat &img, CascadeClassifier &cascade, double scale, string posdir, string negdir, string filename);
int countDetected(Mat &img, CascadeClassifier &cascade, double scale);
void detectAndDraw(Mat &img, CascadeClassifier &cascade, double scale, string windowName);
vector<Rect> detect(Mat &img, CascadeClassifier& cascade, double scale);
Mat detectMat(Mat &img, CascadeClassifier &cascade, double scale);
