#include "opencv2/objdetect/objdetect.hpp"
#include <opencv2/core/core.hpp>

#include <boost/filesystem.hpp>

#include "carimg.hpp"

using namespace std;
using namespace cv;

namespace fs = boost::filesystem;

class FileIO {
public:

	/*
	 * Save CarImg objects to the place their path (USE FOR UNIQUE CARS)
	 */
	static void saveCarImgList(list<CarImg> carList);

	/*
	 * Save CarImg objects to carDir (USE FOR UNIQUE CARS)
	 */
	static void saveCarImgList(list<CarImg> carList, fs::path carListDir);

	/*
	 * Save list<list<CarImg> > objects to carsDir
	 */
	static void saveCars(list<list<CarImg> > cars, fs::path carsDir);

	/*
	 * Load/parse list<CarImg> objects from carsDir
	 */
	static list<list<CarImg> > loadCars(fs::path carsDir);

	/*
	 * Load/parse CarImg objects from carDir
	 */
	static list<CarImg> loadCarImgList(fs::path carDir);

	/*
	 * Load/parse CarImg objects from paths in carList
	 */
	static list<CarImg> loadCarImgList(list<string> carList);
};
