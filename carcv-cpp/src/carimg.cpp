/*
 * Copyright 2012 CarCV Development Team
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

#include "carimg.hpp"

#include "opencv2/highgui/highgui.hpp"

CarImg::CarImg(fs::path &path, Mat &img)
{
	this->setPath(path);
	this->setImg(img);
}

CarImg::CarImg(fs::path &path) {
	this->setPath(path);

	this->load();
}

CarImg::CarImg(void) {

}

fs::path *CarImg::getPath(void)
{
	return &path;
}

void CarImg::setPath(fs::path &path)
{
	this->path = path;
}

void CarImg::setImg(Mat &img)
{
	this->img = img;
}

Mat *CarImg::getImg(void)
{
	return &img;
}

bool CarImg::operator<(const CarImg &car) const {
	string p1 = this->path.generic_string();
	string p2 = car.path.generic_string();
	int compare = p1.compare(p2);

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
long CarImg::hashCode(void) const
{
	const long prime = 31;

	long res = 0;

	char c;

	const char* pathc = this->path.c_str();
	const int length = strlen(pathc);


	const int rows = this->img.rows;
	const int cols = this->img.cols;

	for (int i = 0; i < length; i++) {
		c = pathc[i];
		res += (c*prime)+rows+cols;
	}

	return res;
}

/*
 * It is saved to disk at path this->getPath()
 * Move this to CarImg
 */
void CarImg::save(void) {
	fs::path *path = this->getPath();
	Mat *img = this->getImg();

	imwrite(path->generic_string(), *img);
}

void CarImg::load(void) {
	fs::path *path = this->getPath();

	Mat img = imread(path->generic_string());

	this->setImg(img);
}

void CarImg::destroy(void) {
	delete this->getImg();
	delete this->getPath();
	delete this;
}

string CarImg::toString(void) const {
	string path = this->path.generic_string();
	const int rows = this->img.rows;
	const int cols = this->img.cols;

	ostringstream oss;
	oss << "CarImg[path=" << path << ", img=" << rows << "x" << cols << "]";

	return oss.str();
}

/*
 * Returns the id contained in the filename of CarImg path
 */
int CarImg::parseId(void) const {
	string thisFilename = this->path.filename().generic_string();

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