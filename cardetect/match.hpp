#include "opencv2/objdetect/objdetect.hpp"
#include "opencv2/highgui/highgui.hpp"
#include "opencv2/imgproc/imgproc.hpp"
#include "opencv2/calib3d/calib3d.hpp"
#include "opencv2/core/core.hpp"
#include "opencv2/features2d/features2d.hpp"
#include "opencv2/nonfree/features2d.hpp"
#include "opencv2/flann/flann.hpp"

#include <boost/filesystem.hpp>

using namespace std;
using namespace cv;

namespace fs = boost::filesystem;

/*
 * Matching class Match
 */
class Match {
public:
	static vector<DMatch> vecMatches(Mat img1, Mat img2, Mat &descriptors_object, vector<KeyPoint> &keypoints_object, vector<KeyPoint> &keypoints_scene);
	static double match(fs::path path1, fs::path path2);
	static double match(string path1, string path2);
	static double match(Mat img1, Mat img2);
	static vector<DMatch> vecGoodMatches(Mat img1, Mat img2, Mat &descriptors_object, vector<KeyPoint> &keypoints_object, vector<KeyPoint> &keypoints_scene);
	static Mat matGoodMatches(Mat img1, Mat img2, bool good);
	static bool templateMatch(Mat img, Mat templ, int match_method);
	static vector<Point2f> sceneCornersGoodMatches(Mat img1, Mat img2, bool good);
};
