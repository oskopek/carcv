#include "tools.hpp"

using namespace std;
namespace fs = boost::filesystem;



/*
 * int length = length of return shortened sstring
 */
string Tools::shorten(string s, int length) {
	int len = length+1;
	const char* schar = s.c_str();

	char sshort[len];

	for(int i = 0; i < len; i++) {
		sshort[i] = schar[i];
	}
	sshort[len-1] = '\0';

	string shortString = sshort;
	return shortString;

}





/*
 * Parses the input file plist into a list<string>
 */
list<string> Tools::parseList(fs::path &plist) { //tested, should work
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
 * Map item at index
 * If index is not found in map, returns &(*tmap.end()).second
 */
/*
template <class K, class V>
V * Tools::atMap(map<K, V> *tmap, K &index) { //tested, works

	typename map<K, V>::iterator tmapI = tmap->begin();
	typename map<K, V>::iterator searching = tmap->find(index);


	for (int i = 0; tmapI != tmap->end();i++) {
		if (tmapI == searching) {
			return &(*tmapI).second;
		}
		tmapI++;
	}
	return &(*tmap->end()).second;
}*/

/*
 * List item at index
 * If index is out of bounds, should return *tlist.end(), but returns rather unexpected results
 */
/*
template <class T>
T * Tools::atList(list<T> *tlist, int &index) { //

	typename list<T>::iterator tlistI = tlist->begin();

	for (int i = 0; tlistI != tlist->end();i++) {
		if (i == index) {
			return &(*tlistI);
		}
		tlistI++;
	}
	return &(*tlist->end());//*--tlistI; was used for returning the last element anyway
}*/


/*
 * Length of plist
 * Useless: use plist.size()
 */
/*template <class P>
int Tools::listSize(list<P> &plist) { //useless, use plist.size()
	typename list<P>::iterator plistI = plist.begin();
	int i;

	for (i = 0; plistI != plist.end();i++) {
		plistI++;
	}
	return i;
}*/


/*
 * Size of pmap
 * Useless, use pmap.size()
 */
/*template <class K, class V>
int Tools::mapSize(map<K, V> &pmap) {
	typename map<K, V>::iterator pmapI = pmap.begin();
	int i;

	for (i = 0; pmapI != pmap.end();i++) {
		pmapI++;
	}
	return i;
}*/

/*
list<T> Tools::replaceObj(list<T> list, T replaceObj, T withObj, int index) {
	typename std::list<T> replaced = list;
	typename std::list<T>::iterator lineIte = replaced.begin();

	for (int i = 0; i != index; i++) {
		lineIte++;
	}

	const int lineSize = replaced.size();
	if (lineSize > 1) {
		typename std::list<T>::iterator bIt = lineIte;
		typename std::list<T>::iterator eIt = lineIte;
		bIt--;
		eIt++;
		replace(bIt, eIt, replaceObj, withObj);
	}
	else {
		replace(list.begin(), list.end(), replaceObj, withObj);
	}
	return replaced;
}*/

/*void grabKVparams(char **argv) { //just for testing reference, erase later
	for (int i = 1; i <= 5; i++) {
		istringstream source(argv[i]);
		string token;
		string key;
		double n;
		for (int i = 0; getline( source, token, '=' ); i++) {
			if (i == 0) {
				key = token;
			}
			istringstream ss(token);
			ss >> n;

		}
		cout << key << endl;
		cout << n << endl;
		// do something with n
		cout << "----------" << endl;
	}
}*/

/*void Tools::test(int argc, char** argv) {
	/*fs::path imgPath1(argv[1]);
	CarImg car1;
	car1.setPath(fs::absolute(imgPath1));
	car1.load();

	fs::path imgPath2(argv[2]);
	CarImg car2;
	car2.setPath(fs::absolute(imgPath2));
	car2.load();

	fs::path imgPath3(argv[3]);
	CarImg car3;
	car3.setPath(fs::absolute(imgPath3));
	car3.load();

	fs::path imgPath4(argv[4]);
	CarImg car4;
	car4.setPath(fs::absolute(imgPath4));
	car4.load();

	fs::path carDir = argv[5];

	list<list<CarImg> > cars;

	list<CarImg> c1;
	c1.push_back(car1);
	c1.push_back(car2);

	list<CarImg> c2;
	c2.push_back(car3);
	c2.push_back(car4);

	cars.push_front(c1);
	cars.push_back(c2);

	//Tools::saveCars(cars, carDir);

	list<list<CarImg> > set = Tools::loadCars(carDir);
	list<CarImg> *loaded = Tools::atList(&set, atoi(argv[6]));

	cvNamedWindow("Images");
	for(list<CarImg>::iterator i = loaded->begin(); i != loaded->end(); i++) {
		imshow("Images", (*i).getImg());
		waitKey(0);
	}
	cvDestroyWindow("Images");*/ /*

	Rect r1, r2;

	r1.x = 5;
	r1.y = 5;
	r1.width = 10;
	r1.height = 10;

	r2.x = 5;
	r2.y = 5;
	r2.width = 5;
	r2.width = 5;

	cout << Det::isInRect(r1, r2) << endl;

	r2.x = 11;
	r2.y = 11;
	r2.width = 5;
	r2.width = 5;

	cout << Det::isInRect(r1, r2) << endl;

	r2.x = 10;
	r2.y = 10;
	r2.width = 5;
	r2.width = 5;

	cout << Det::isInRect(r1, r2) << endl;

}*/

void Tools::debugMessage(string message) {
	time_t rawtime;
	struct tm * timeinfo;

	time ( &rawtime );
	timeinfo = localtime ( &rawtime );
	string timestamp = asctime (timeinfo);
	char* timestampC = const_cast<char *> (timestamp.c_str());
	string prefix = DEBSTR;

	for(int i = 0; i < timestamp.size(); i++) { //removes the nextline char
		if (timestampC[i] == '\n') {
			timestampC[i] = '\0';
			break;
		}
	}
	timestamp = timestampC;

	cout << "[" << timestamp << "]" << prefix << message << endl;
	//printf("[%s]%s%s", timestamp.c_str(), prefix.c_str(), message.c_str());
}

void Tools::errorMessage(string message) {
	time_t rawtime;
	struct tm * timeinfo;

	time ( &rawtime );
	timeinfo = localtime ( &rawtime );
	string timestamp = asctime (timeinfo);
	char* timestampC = const_cast<char *> (timestamp.c_str());
	string prefix = ERRSTR;

	for(int i = 0; i < timestamp.size(); i++) { //removes the nextline char
		if (timestampC[i] == '\n') {
			timestampC[i] = '\0';
			break;
		}
	}
	timestamp = timestampC;

	cout << "[" << timestamp << "]" << prefix << message << endl;
}


/*
 * Should return the index of the biggest double in mlist
 * If two are equal, returns the index of the first one
 *
 */
int Tools::findMaxIndex(list<double> &mlist) { //tested, works
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
