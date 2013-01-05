#include <boost/filesystem.hpp>
#include <opencv2/core/core.hpp>

using namespace std;
using namespace cv;
namespace fs = boost::filesystem;

class CarImg {
public:
	CarImg(fs::path &path, Mat &img);
	fs::path getPath() const;
	void setPath(fs::path path);
	void setImg(Mat &img);
	Mat getImg() const;

	bool operator<(const CarImg &car) const;
	bool operator==(const CarImg &car) const;

	long hashCode() const;

	void save();
	void load();

	string toString() const;


	//todo override operator< and operator=

	/*
	static CarImg load(fs::path &fromPath);
	static CarImg load(string &filename);
	*/

//temp outcommented, cause of struct comparision see ^ todo: private:
	CarImg();
	fs::path path;
	Mat img;
};
