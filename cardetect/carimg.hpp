#include <boost/filesystem.hpp>
#include <opencv2/core/core.hpp>

using namespace std;
using namespace cv;
namespace fs = boost::filesystem;

class CarImg {
public:
	CarImg(fs::path &path, string &filename, Mat &img);
	fs::path getPath() const;
	void setFilename(string &filename);
	string getFilename() const;
	void setImg(Mat &img);
	Mat getImg() const;

	bool operator<(const CarImg &car) const;
	bool operator==(const CarImg &car) const;

	long hashCode() const;


	//todo override operator< and operator=

	/*
	static CarImg load(fs::path &fromPath);
	static CarImg load(string &filename);
	*/

//temp outcommented, cause of struct comparision see ^ todo: private:
	CarImg();
	fs::path path;
	string filename;
	Mat img;
};
