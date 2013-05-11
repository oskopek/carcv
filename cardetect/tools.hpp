#include <boost/lexical_cast.hpp>
#include <boost/filesystem.hpp>

#include <opencv2/core/core.hpp>

using namespace std;
using namespace cv;

namespace fs = boost::filesystem;


/*
 * This class contains some static methods that are occasionally used.
 *
 * IMPORTANT: If a method uses templates,
 * the implementation of it has to be in the same physical file as the declaration.
 */
class Tools {
public:

	/*
	 * Parses the input file into a list<string>
	 * Used for converting a file full of image filenames into a list of image filenames,
	 * to be later loaded, etc..
	 */
	static list<string> parseList(fs::path &l);


	/*
	 * Returns the index of the biggest double in list
	 * If two doubles are equal, returns the index of the first one
	 */
	static int findMaxIndex(list<double> &l);


	/*
	 * Map item at index
	 * If index is not found in map, returns &(*m.end()).second
	 */
	template <class K, class V>
	static V * atMap(map<K, V> *m, K &index) {

		typename map<K, V>::iterator mapI = m->begin();
		typename map<K, V>::iterator searching = m->find(index);


		for (int i = 0; mapI != m->end();i++) {
			if (mapI == searching) {
				return &(*mapI).second;
			}
			mapI++;
		}
		return &(*m->end()).second;
	};


	/*
	 * List item at index
	 * If index is out of bounds, should return *l.end(), but returns rather unexpected results
	 */
	template <class T>
	static T * atList(list<T> *l, int &index) {

		typename list<T>::iterator listI = l->begin();

		for (int i = 0; listI != l->end();i++) {
			if (i == index) {
				return &(*listI);
			}
			listI++;
		}
		return &(*l->end());//*--listI; was used for returning the last element anyway
	};


	/*
	 * Crops the string to specified length (removes extra trailing characters)
	 */
	static string shorten(string s, int length);


	/*
	 * Replaces object with it's replacement in the list at index
	 */
	template <class T>
	static list<T> replaceObj(list<T> l, T replaceObj, T withObj, int index) {
		typename std::list<T> replaced = l;
		typename std::list<T>::iterator lineIterator = replaced.begin();

		for (int i = 0; i != index; i++) {
			lineIterator++;
		}

		const int lineSize = replaced.size();
		if (lineSize > 1) {
			typename std::list<T>::iterator bIterator = lineIterator;
			typename std::list<T>::iterator eIterator = lineIterator;
			bIterator--;
			eIterator++;
			replace(bIterator, eIterator, replaceObj, withObj);
		}
		else {
			replace(l.begin(), l.end(), replaceObj, withObj);
		}
		return replaced;
	};


	/*
	 * Prints the given message with a debug prefix to cout
	 */
	static void debugMessage(string message);


	/*
	 * Prints the given message with an error prefix to cout
	 */
	static void errorMessage(string message);
};
