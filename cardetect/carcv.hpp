#include "carimg.hpp"

#include "opencv2/objdetect/objdetect.hpp"

namespace fs = boost::filesystem;
using namespace std;
using namespace cv;


enum method {CCV_HAAR_SURF, CCV_HAAR_TMPLMATCH};

class CarCV {
public:

	void run(fs::path &imgListPath, method, CascadeClassifier &cascade);

	void detect(list<string> *imgList, CascadeClassifier &cascade);

	void sortPOS_AND_NEG(list<string> *imgList, fs::path &posDirPath, fs::path &negDirPath);

	/*
	 * Supply a list of positive images, and a dir where to place cars
	 */
	void sortUnique(list<string> &posImgList, fs::path carsDir, CascadeClassifier &cascade);

	double calcSpeed(list<CarImg> clist);

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
