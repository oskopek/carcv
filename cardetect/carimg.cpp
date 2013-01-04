#include "carimg.hpp"

CarImg::CarImg(fs::path &path, string &filename, Mat &img)
{
	this->path = path;
	this->filename = filename;
	this->img = img;
}

fs::path CarImg::getPath() const
{
	return path;
}

void CarImg::setFilename(string &filename)
{
	this->filename = filename;
}

string CarImg::getFilename() const
{
	return filename;
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

	const char* filec = this->getFilename().c_str();
	char c;
	int length;

	length = strlen(filec);
	for (int i = 0; i < length; i++) {
		c = filec[i];
		res += (c*3);
	}


	const char* pathc = this->getPath().c_str();
	length = strlen(pathc);

	for (int i = 0; i < length; i++) {
		c = pathc[i];
		res += (c*5);
	}

	return res;
}
