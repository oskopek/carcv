#include <opencv2/core/core.hpp>
#include <boost/lexical_cast.hpp>

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

	int size = array.size();

	fst  << arrayName << size; //writes size of first level of array

	string index;
	for (int i = 0; i < size; ++i) {
		index = boost::lexical_cast<string>(i);
		cout << arrayName + index << endl;

		fst << arrayName + index << array.at(i);
	}

	fst.release();

	return;
}


template <class A>
vector< vector<A> > load2DArray(string &arrayName) {
	FileStorage fst(arrayName + ".yml", FileStorage::READ);

	vector< vector<A> > array;

	int size;
	fst[arrayName] >> size; //reads size of first level

	vector<A> temp;
	string index;
	for (int i = 0; i < size; ++i) {
		index = boost::lexical_cast<string>(i);
		
		fst[(arrayName + index)] >> temp;

		array.push_back(temp);
		temp.clear();
	}
	fst.release();

	return array;
}
