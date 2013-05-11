#include "opencv2/objdetect/objdetect.hpp"
#include <opencv2/core/core.hpp>
#include <boost/filesystem.hpp>

#include "carimg.hpp"

using namespace std;
using namespace cv;

namespace fs = boost::filesystem;

class FileIO {
public:
	static void saveCarImgList(list<CarImg> carList);
	static void saveCarImgList(list<CarImg> carList, fs::path carListDir);
	static void saveCars(list<list<CarImg> > cars, fs::path carsDir);
	static list<list<CarImg> > loadCars(fs::path carsDir);
	static list<CarImg> loadCarImgList(fs::path carDir);
	static list<CarImg> loadCarImgList(list<string> carList);
};
