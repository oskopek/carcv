/*
 * CarCV - Car recognizing and speed calculating platform
 *
 * Authors:
 * Copyright (C) 2012-2013, Ondrej Skopek
 *
 * All rights reserved.
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
