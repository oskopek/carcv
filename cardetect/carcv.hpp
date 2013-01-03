#include "carmatcher.hpp"
#include "carrectangles.hpp"
#include "carimg.hpp"

#include <boost/filesystem.hpp>

namespace fs = boost::filesystem;
using namespace std;
using namespace cv;

class {
public:
	const enum method {CCV_HAAR_SURF, CCV_HAAR_TMPLMATCH};

	void run(fs::path &imgList, method, CascadeClassifier &cascade);

	void detect(fs::path &imgList, CascadeClassifier &cascade);

	void sortPOS_AND_NEG(fs::path &imgList);

	void sortUnique(fs::path &posImgList);


};
