#include <opencv2/core/core.hpp>

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

	return array;
}
