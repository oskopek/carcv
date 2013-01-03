#include "carcv.hpp"

using namespace std;

int main(int argc, char** argv) {
	string plist;
	plist.assign(argv[1]);
	int index = argv[2][0];

	list<string> dummy;
	dummy = CarCV::parseList(plist);

	list<string>::iterator dummyI = dummy.begin();

	for (int i = 0; dummyI != dummy.end();i++) {
		//cout << "No pointer: "<< dummyI << endl;
		cout << "Pointer: " << *dummyI << endl;


		dummyI++;
	}

	cout << CarCV::listLength(dummy) << endl;
	cout << CarCV::atList(dummy, 0) << ", " << CarCV::atList(dummy, CarCV::listLength(dummy)) << endl;
	cout << CarCV::atList(dummy, 1000);
}

void CarCV::run(fs::path &imgList, method, CascadeClassifier &cascade) {

}

void CarCV::detect(fs::path *imgList, CascadeClassifier &cascade) {

}

void CarCV::sortPOS_AND_NEG(fs::path *imgList) {

}

void CarCV::sortUnique(fs::path &posImgList, fs::path carsDir) {

}

void CarCV::saveCars(list<CarImg>) {

}
list<CarImg> loadCars() {

}

/*
 * Parses the input file plist into a list<string>
 */
list<string> CarCV::parseList(string &plist) {
	list<string> retlist;

	FILE* f = fopen(plist.c_str(), "rt");
	if(f)
	{
		char buf [1000+1];
		for(int i = 0; fgets( buf, 1000, f ); i++)
		{
			int len = (int)strlen(buf);
			while( len > 0 && isspace(buf[len-1]) ) {
				len--;
			}
			buf[len] = '\0';
			retlist.push_back(buf);
		}
	}
	return retlist;
}

/*
 * List item at index
 */
template <class T>
T CarCV::atList(list<T> &tlist, int index) {

	typename list<T>::iterator tlistI = tlist.begin();

	for (int i = 0; tlistI != tlist.end();i++) {
			if (i == index) {
				return *tlistI;
			}
			tlistI++;
		}
	return *--tlistI;
}

/*
 * Length of plist
 */
template <class P>
int CarCV::listLength(list<P> &plist) {
	typename list<P>::iterator plistI = plist.begin();
	int i;

	for (i = 0; plistI != plist.end();i++) {
			plistI++;
		}
	return i;
}
