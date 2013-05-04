#include "carimg.hpp"

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

	static list<string> parseList(fs::path &list);

	static int findMaxIndex(list<double> &mlist);

	template <class T>
	static T * atList(list<T> *tlist, int index);

	template <class P>
	static int listSize(list<P> &plist);

	template <class K, class V>
	static V * atMap(map<K, V> *tmap, K index);

	template <class K, class V>
	static int mapSize(map<K, V> &pmap);

	static void test(int argc, char** argv);

	static string shorten(string s, int length);

	template <class T>
	static list<T> replaceObj(list<T> list, T replaceObj, T withObj, int index);

	static void debugMessage(string message);

	static void errorMessage(string message);

private:

	static void saveCarImgList(list<CarImg> carList);
	static void saveCarImgList(list<CarImg> carList, fs::path carListDir);
	static void saveCars(list<list<CarImg> > cars, fs::path carsDir);
	static list<list<CarImg> > loadCars(fs::path carsDir);
	static list<CarImg> loadCarImgList(fs::path carDir);
	static list<CarImg> loadCarImgList(list<string> carList);

};
