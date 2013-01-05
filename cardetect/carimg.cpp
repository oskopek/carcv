#include "carimg.hpp"

#include "opencv2/highgui/highgui.hpp"

CarImg::CarImg(fs::path &path, Mat &img)
{
	this->path = path;
	this->img = img;
}

CarImg::CarImg() {

}

fs::path CarImg::getPath() const
{
	return path;
}

void CarImg::setPath(fs::path newPath)
{
	path = newPath;
}

void CarImg::setImg(Mat &img)
{
	this->img = img;
}

Mat CarImg::getImg() const
{
	return img;
}

bool CarImg::operator<(const CarImg &car) const {
	return (this->hashCode() < car.hashCode());
}

bool CarImg::operator==(const CarImg &car) const {
	return (this->hashCode() == car.hashCode());
}

/*
 * Hashes filename and path into a hash
 */
long CarImg::hashCode() const
{
	long res = 0;

	char c;
	const char* pathc = this->getPath().c_str();
	const int length = strlen(pathc);

	for (int i = 0; i < length; i++) {
		c = pathc[i];
		res += (c*5);
	}

	return res;
}

/*
 * It is saved to disk at path this.getPath()
 * Move this to CarImg
 */
void CarImg::save() {
	fs::path p = this->getPath();
	Mat img = this->getImg();

	imwrite(p.generic_string(), img);
}

void CarImg::load() {
	fs::path p = this->getPath();

	Mat img = imread(p.generic_string());

	this->setImg(img);
}

string CarImg::toString() {
	string path = this->getPath().generic_string();
	int rows = this->getImg().rows;
	int cols = this->getImg().cols;
	ostringstream oss;

	oss << "CarImg[path=" << path << ", img=" << rows << "x" << cols << "]";

	return oss.str();
}
