#include <boost/filesystem.hpp>
#include <opencv2/core/core.hpp>

using namespace std;
using namespace cv;
namespace fs = boost::filesystem;

class CarImg {
public:
	CarImg(fs::path &path, string &filename, Mat &img) {CarImg x = CarImg(); x.path = path; x.filename = filename; x.img = img;};
	fs::path getPath() 		{return path;};
	void setFilename(string &filename)	{this->filename = filename;};
	string getFilename()	{return filename;};
	void setImg(Mat &img) 	{this->img = img;};
	Mat getImg()			{return img;};

	/*
	static CarImg load(fs::path &fromPath);
	static CarImg load(string &filename);
	*/

private:
	CarImg();
	fs::path path;
	string filename;
	Mat img;
};
