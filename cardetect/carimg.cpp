/*
 * CarCV - Car recognizing and speed calculating platform
 *
 * Authors:
 * Copyright (C) 2012-2013, Ondrej Skopek
 *
 * All rights reserved.
 */

#include "carimg.hpp"

#include "opencv2/highgui/highgui.hpp"

CarImg::CarImg(fs::path &path, Mat *img)
{
	this->setPath(path);
	this->setImg(img);
}

CarImg::CarImg(fs::path &path) {
	this->setPath(path);

	this->load();
}

CarImg::CarImg() {

}

fs::path CarImg::getPath() const
{
	return path;
}

void CarImg::setPath(fs::path &path)
{
	this->path = path;
}

void CarImg::setImg(Mat *img)
{
	this->img = img;
}

Mat * CarImg::getImg() const
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
 * It is saved to disk at path this->getPath()
 * Move this to CarImg
 */
void CarImg::save() {
	fs::path path = this->getPath();
	Mat * img = this->getImg();

	imwrite(path.generic_string(), *img);
}

void CarImg::load() {
	fs::path * path = &(this->path);

	Mat img = imread(path->generic_string());

	this->setImg(&img);
}

string CarImg::toString() const {
	string path = this->getPath().generic_string();
	int rows = this->getImg()->rows;
	int cols = this->getImg()->cols;

	ostringstream oss;
	oss << "CarImg[path=" << path << ", img=" << rows << "x" << cols << "]";

	return oss.str();
}

/*
 * Returns the id contained in the filename of CarImg path
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
