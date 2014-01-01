/*
 * Copyright 2012-2014 CarCV Development Team
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

#include <boost/filesystem.hpp>
#include <opencv2/core/core.hpp>

using namespace std;
using namespace cv;
namespace fs = boost::filesystem;

class CarImg {
public:
	CarImg(fs::path &path, Mat &img);
	CarImg(fs::path &path);


	fs::path *getPath(void);
	void setPath(fs::path &path);
	void setImg(Mat &img);
	Mat *getImg(void);

	bool operator<(const CarImg &car) const;
	bool operator==(const CarImg &car) const;

	long hashCode(void) const;

	void save(void);
	void load(void);
	void destroy(void);

	string toString(void) const;

	int parseId(void) const;

	CarImg load(fs::path &path);
	CarImg load(string &filename);

private:
	CarImg(void);
	fs::path path;
	Mat img;
};