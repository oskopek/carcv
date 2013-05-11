#include <boost/lexical_cast.hpp>
#include <boost/filesystem.hpp>

#include <opencv2/core/core.hpp>

#define DEBSTR "DEBUG:	"
#define ERRSTR "ERROR:	"


using namespace std;
using namespace cv;
namespace fs = boost::filesystem;

/*
 * IMPORTANT: If method uses templates, has to be
 */
class Tools {
public:
	static list<string> parseList(fs::path &list);

	static int findMaxIndex(list<double> &mlist);

	/*
	 * Length of plist
	 * Useless: use plist.size()
	 */
	template <class P>
	static int listSize(list<P> &plist) {
		typename list<P>::iterator plistI = plist.begin();
			int i;

			for (i = 0; plistI != plist.end();i++) {
				plistI++;
			}
			return i;
	};

	/*
	 * Map item at index
	 * If index is not found in map, returns &(*tmap.end()).second
	 */
	template <class K, class V>
	static V * atMap(map<K, V> *tmap, K &index) {

		typename map<K, V>::iterator tmapI = tmap->begin();
		typename map<K, V>::iterator searching = tmap->find(index);


		for (int i = 0; tmapI != tmap->end();i++) {
			if (tmapI == searching) {
				return &(*tmapI).second;
			}
			tmapI++;
		}
		return &(*tmap->end()).second;
	};


	/*
	 * List item at index
	 * If index is out of bounds, should return *tlist.end(), but returns rather unexpected results
	 */
	template <class T>
	static T * atList(list<T> *tlist, int &index) {

		typename list<T>::iterator tlistI = tlist->begin();

		for (int i = 0; tlistI != tlist->end();i++) {
			if (i == index) {
				return &(*tlistI);
			}
			tlistI++;
		}
		return &(*tlist->end());//*--tlistI; was used for returning the last element anyway
	};

	/*
	 * Size of pmap
	 * Useless, use pmap.size()
	 */
	template <class K, class V>
	static int mapSize(map<K, V> &pmap) {
		typename map<K, V>::iterator pmapI = pmap.begin();
			int i;

			for (i = 0; pmapI != pmap.end();i++) {
				pmapI++;
			}
			return i;
	};

	static void test(int argc, char** argv);

	static string shorten(string s, int length);

	template <class T>
	static list<T> replaceObj(list<T> list, T replaceObj, T withObj, int index) {
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
	};

	static void debugMessage(string message);

	static void errorMessage(string message);
};
