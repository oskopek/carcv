#include "carimg.hpp"

#include "opencv2/objdetect/objdetect.hpp"

namespace fs = boost::filesystem;
using namespace std;
using namespace cv;

/*
 * CCV_HAAR_SURF = use Viola-Jones detection and match it with SURF
 * CCV_HAAR_TMPLMATCH = use Viola-Jones detection and match it with template matching
 */
enum method {CCV_HAAR_SURF = 1, CCV_HAAR_TMPLMATCH = 2};

/*
 * CCV_SP_FROMSORTEDFILES=Uses only the current car dir, calculates the range
 * of file ids where the car is in speedrectangle and calculate number of frames / framerate
 *
 * CCV_SP_FROMALLFILES=Uses the whole all dir to find images of the given car, calculates the range
 * of file ids where the car is in speedrectangle and calculate number of frames / framerate
 */
enum speed_method {CCV_SP_FROMSORTEDFILES = 1, CCV_SP_FROMALLFILES = 2};

class CarCV {
public:

	void run(fs::path &imgListPath, int method, CascadeClassifier &cascade);

	void detect(list<string> *imgList, CascadeClassifier &cascade);

	void sortPOS_AND_NEG(list<string> *imgList, fs::path &posDirPath, fs::path &negDirPath);

	/*
	 * Supply a list of positive images, and a dir where to place cars
	 */
	void sortUnique(list<string> &posImgList, fs::path carsDir, CascadeClassifier &cascade);

	double calcSpeed(list<CarImg> clist, int speed_method);

	static list<string> parseList(fs::path &list);

	static int findMaxIndex(list<double> &mlist);

	template <class T>
	static T atList(list<T> &tlist, int index);

	template <class P>
	static int listSize(list<P> &plist);

	template <class K, class V>
	static V atMap(map<K, V> &tmap, K index);

	template <class K, class V>
	static int mapSize(map<K, V> &pmap);

private:

	void saveCars(list<CarImg>, fs::path carsDir);
	static list<CarImg> loadCars();
	static list<CarImg> loadCars(list<string> carList);

};
