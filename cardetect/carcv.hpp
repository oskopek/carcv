#include "carrectangles.hpp"
#include "carimg.hpp"

#include <boost/filesystem.hpp>

namespace fs = boost::filesystem;
using namespace std;
using namespace cv;


enum method {CCV_HAAR_SURF, CCV_HAAR_TMPLMATCH};

class CarCV {
public:

	void run(fs::path &imgList, method, CascadeClassifier &cascade);

	void detect(fs::path *imgList, CascadeClassifier &cascade);

	void sortPOS_AND_NEG(fs::path *imgList);

	/*
	 * Supply a list of positive images, and a dir where to place cars
	 */
	void sortUnique(fs::path &posImgList, fs::path carsDir);

	static list<string> parseList(string &list);

	template <class T>
	static T atList(list<T> &tlist, int index);

	template <class P>
	static int listLength(list<P> &plist);

private:

	void saveCars(list<CarImg>);
	list<CarImg> loadCars();

};
