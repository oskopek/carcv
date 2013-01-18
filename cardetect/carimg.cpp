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
	int compare = this->getPath().generic_string().compare(car.getPath().generic_string());

	if (compare < 0) {
		return true;
	} else {
		return false;
	}
}

bool CarImg::operator==(const CarImg &car) const {
	return (this->hashCode() == car.hashCode());
}

/*
 * Hashes filename and path into a hash
 */
long CarImg::hashCode() const
{
	const long prime = 7;

	long res = 0;

	char c;

	const char* pathc = this->getPath().c_str();
	const int length = strlen(pathc);

	for (int i = 0; i < length; i++) {
		c = pathc[i];
		res += (c*prime);
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

string CarImg::toString() const {
	string path = this->getPath().generic_string();
	int rows = this->getImg().rows;
	int cols = this->getImg().cols;
	ostringstream oss;

	oss << "CarImg[path=" << path << ", img=" << rows << "x" << cols << "]";

	return oss.str();
}

/*
 * Returns id in filename of CarImg
 */
int CarImg::parseId() const {
	string thisFilename = getPath().filename().generic_string();

	const char* FNchar = thisFilename.c_str();
	char IDchar[thisFilename.length()];

	int index = 0;
	for (int i = 0; i < thisFilename.length(); i++) {
		if (FNchar[i] == '-') {
			for (int j = ++i; j < thisFilename.length(); j++) {
				if (FNchar[j] == '.') {
					break;
				}

				IDchar[index] = FNchar[j];
				index++;
			}
			break;
		}
	}

	int res = atoi(IDchar);

	return  res;
}
