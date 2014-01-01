/*
 * Copyright 2012-2014 CarCV Development Team
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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