#include <opencv2/core/core.hpp>

#include <iostream>
#include <vector>

using namespace cv;
using namespace std;

template <class A>
void saveArray(string &arrayName, A array) {
	FileStorage fst(arrayName + ".yml", FileStorage::WRITE);

	fst << arrayName << array;
	fst.release();

	return;
}

template <class A>
A loadArray(string &arrayName) {
	FileStorage fst(arrayName + ".yml", FileStorage::READ);

	A array;
	fst[arrayName] >> array;
	fst.release();

	return array;
}

//2D arrays have overloaded functions
template <class A>
void save2DArray(string &arrayName, vector< vector<A> > array) {
	FileStorage fst(arrayName + ".yml", FileStorage::WRITE);

	string index;

	for (int i = 0; i < array.size(); ++i) {
		index = i;
		fst << arrayName + index << array.at(i);
	}
	fst.release();

	return;
}


template <class A>
vector< vector<A> > load2DArray(string &arrayName) {
	FileStorage fst(arrayName + ".yml", FileStorage::READ);

	vector< vector<A> > array;

	vector<A> temp;
	string index;

	for (int i = 0; i < array.size(); ++i) {
		index = i;
		fst[arrayName + index] >> temp;

		array.push_back(temp);
		temp.clear();
	}
	fst.release();

	return array;
}
