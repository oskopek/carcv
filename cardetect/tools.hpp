#include "opencv2/objdetect/objdetect.hpp"
#include <opencv2/core/core.hpp>

#include <boost/lexical_cast.hpp>
#include <boost/filesystem.hpp>

#include "carimg.hpp"

#define DEBSTR "DEBUG:	"
#define ERRSTR "ERROR:	"

namespace fs = boost::filesystem;

using namespace std;
using namespace cv;

class Tools {
public:
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

	static void saveCarImgList(list<CarImg> carList);
	static void saveCarImgList(list<CarImg> carList, fs::path carListDir);
	static void saveCars(list<list<CarImg> > cars, fs::path carsDir);
	static list<list<CarImg> > loadCars(fs::path carsDir);
	static list<CarImg> loadCarImgList(fs::path carDir);
	static list<CarImg> loadCarImgList(list<string> carList);
};
