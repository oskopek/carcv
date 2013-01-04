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
void CarCV::run(fs::path &imgListPath, method, CascadeClassifier &cascade) {
	fs::path posDirPath = "pos";
	fs::path negDirPath = "neg";

	list<string> imgList = CarCV::parseList(imgListPath);

	CarCV c;
	c.detect(&imgList, cascade);
	c.sortPOS_AND_NEG(&imgList, posDirPath, negDirPath);


	fs::path carsDir = "cars";
	if (!fs::exists(carsDir) || !fs::is_directory(carsDir)) {
		fs::create_directory(carsDir);
	}

	c.sortUnique(imgList, carsDir);

	list<CarImg> carlist;

	double speed = c.calcSpeed(carlist);

}

/*
 * //TODO: to be implemented
 */
void CarCV::detect(list<string> *imgList, CascadeClassifier &cascade) { //TODO: mix with sortPOS_AND_NEG()

}

/*
 * //TODO: to be implemented
 */
void CarCV::sortPOS_AND_NEG(list<string> *imgList, fs::path &posDirPath, fs::path &negDirPath) { //detect and sort? w/ detect()

}

/*
 * Sort images from posImgList into unique car subdirectiories of carsDir
 * Uses <sarcasm> Ondrej Skopek Sort Algorithm (OSSA) </sarcasm>
 */
void CarCV::sortUnique(list<string> &posImgList, fs::path carsDir) { //TODO: implement super algorithm 3000

	//temp
	struct cmp_str
	{
		bool operator()(const CarImg *a, const CarImg *b) const
		{
			const char* ac = a->filename.c_str();
			const char* bc = b->filename.c_str();
			return std::strcmp(ac, bc) < 0;
		}
	};
	//temp


	list<CarImg> posCarImgList; //=converted from posImgList

	map<CarImg, double, cmp_str> probability; //flushed at every iteration over posImgList

	list<list<CarImg> > cars; //sorted list

	list<double> carProbabilty; //result probabilities for every potential unique car

	for (int i = 0; i < CarCV::listSize(posCarImgList); i++) { //iterate over posImgList
		probability.clear();
		carProbabilty.clear();

		if (i == 0 && cars.size() == 0) { //first iteration
			CarCV::atList(cars, 0).push_back(CarCV::atList(posCarImgList, i));
			continue;
		}

		for (int j = 0; j < CarCV::listSize(cars); j++) { //iterate over the main list of cars
			int k;

			for (k = 0; k < CarCV::atList(cars, j).size(); k++) {
				double prob = 0;
				list<CarImg> li = CarCV::atList(cars, j);
				CarImg t = CarCV::atList(li, k);
				probability.insert(std::pair<CarImg, double>(t, prob));
			}
		}

		for (int l = 0; l < cars.size(); l++) {
			double prob;
			int m;
			for (m = 0; m < CarCV::atList(cars, l).size(); m++) {
				list<CarImg> li = CarCV::atList(cars, l);
				CarImg t = CarCV::atList(li, m);
				prob += CarCV::atMap(probability, t);
			}
			prob /= CarCV::atList(cars, l).size();
			carProbabilty.push_back(prob);
		}

		int carProbId = CarCV::findMaxIndex(carProbabilty); //TODO: 1 iterate over, find max, print not max but id of max
		CarCV::atList(cars, carProbId).push_back(CarCV::atList(posCarImgList, i));
	}
}

/*
 * Should return the index of the biggest double in mlist
 */
int CarCV::findMaxIndex(list<double> &mlist) {
	list<double>::iterator mlistI = mlist.begin();
	double probmax;
	int index;

	for (int i = 0; mlistI != mlist.end();i++) {
		if(*mlistI > probmax) {
			probmax = *mlistI;
			index = i;
		}

		mlistI++;
	}
	return index;
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
	list<CarImg> carImgList; //todo not good

	return carImgList;
}

/*
 * Load/parse CarImg from carList
 */
list<CarImg> loadCars(list<string> carList) { //TODO: create a loader/parser for CarImg
	list<CarImg> carImgList;

	return carImgList;
}

/*
 * Parses the input file plist into a list<string>
 */
list<string> CarCV::parseList(fs::path &plist) {
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
V CarCV::atMap(map<K, V> &tmap, K index) {

	typename map<K, V>::iterator tmapI = tmap.begin();
	typename map<K, V>::iterator searching = tmap.find(index);


	for (int i = 0; tmapI != tmap.end();i++) {
			if (tmapI == searching) {
				return (*tmapI).second;
			}
			tmapI++;
		}
	return (*--tmapI).second;
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
