#include "carcv.hpp"

using namespace std;

int main(int argc, char** argv) {
	CarCV c;

	CascadeClassifier cascade;
	cascade.load(argv[2]);

	fs::path dir(argv[1]);

	c.run(dir, CCV_HAAR_SURF, cascade);
}

/*
 * Main run method
 */
void CarCV::run(fs::path &imgList, method, CascadeClassifier &cascade) {
	fs::path posImgDir = "pos";
	fs::path negImgDir = "neg";
	CarCV c;
	c.detect(&imgList, cascade);
	c.sortPOS_AND_NEG(&imgList);


	fs::path carsDir = "cars";
	c.sortUnique(posImgDir, carsDir);

	list<CarImg> carlist;

	double speed = c.calcSpeed(carlist);

}

/*
 * //TODO: to be implemented
 */
void CarCV::detect(fs::path *imgList, CascadeClassifier &cascade) { //TODO: mix with sortPOS_AND_NEG()

}

/*
 * //TODO: to be implemented
 */
void CarCV::sortPOS_AND_NEG(fs::path *imgList) { //detect and sort? w/ detect()

}

/*
 * Sort images from posImgList into unique car subdirectiories of carsDir
 * Uses <sarcasm> Ondrej Skopek Sort Algorithm (OSSA) </sarcasm>
 */
void CarCV::sortUnique(fs::path &posImgList, fs::path carsDir) { //TODO: implement super algorithm 3000

}

/*
 * Calculates speed of a given unique car from the list of CarImg
 */
double CarCV::calcSpeed(list<CarImg> clist) { //TODO: leave empty for now

}

/*
 * Save CarImg objects to carsDir
 */
void CarCV::saveCars(list<CarImg>, fs::path carsDir) { //create a saver for CarImg

}

/*
 * Load/parse CarImg objects from carsDir
 */
list<CarImg> loadCars(fs::path carsDir) { //create a loader/parser for CarImg

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
int CarCV::listSize(list<P> &plist) {
	typename list<P>::iterator plistI = plist.begin();
	int i;

	for (i = 0; plistI != plist.end();i++) {
			plistI++;
		}
	return i;
}


/*
 * Map item at index
 */
template <class K, class V>
V CarCV::atMap(map<K, V> &tmap, V index) {

	typename map<K, V>::iterator tmapI = tmap.begin();

	for (int i = 0; tmapI != tmap.end();i++) {
			if (i == index) {
				return *tmapI;
			}
			tmapI++;
		}
	return *--tmapI;
}

/*
 * Size of pmap
 */
template <class K, class V>
int CarCV::mapSize(map<K, V> &pmap) {
	typename map<K, V>::iterator pmapI = pmap.begin();
	int i;

	for (i = 0; pmapI != pmap.end();i++) {
			pmapI++;
		}
	return i;
}
