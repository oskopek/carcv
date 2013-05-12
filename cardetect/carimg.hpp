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
	CarImg(fs::path &path, Mat *img);
	CarImg(fs::path &path);


	fs::path getPath() const;
	void setPath(fs::path &path);
	void setImg(Mat *img);
	Mat * getImg() const;

	bool operator<(const CarImg &car) const;
	bool operator==(const CarImg &car) const;

	long hashCode() const;

	void save();
	void load();

	string toString() const;

	int parseId() const;

	CarImg load(fs::path &path);
	CarImg load(string &filename);

	//temp outcommented, cause of struct comparision see ^ todo: private:
private:
	CarImg();
	fs::path path;
	Mat *img;
};
