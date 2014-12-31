/*
 * Copyright 2012 CarCV Development Team
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

#include "tools.hpp"

#include <stdio.h>

#define DEBSTR "DEBUG:	"
#define ERRSTR "ERROR:	"

using namespace std;
namespace fs = boost::filesystem;


/*
 * Crops the string to specified length (removes extra trailing characters)
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
 * Parses the input file at path p into a list<string>
 * Used for converting a file full of image filenames into a list of image filenames,
 * to be later loaded, etc..
 */
list<string> Tools::parseList(fs::path &p) {
	list<string> retlist;

	FILE* f = fopen(p.c_str(), "rt");
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
 * Prints the given message with a debug prefix to cout
 */
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


/*
 * Prints the given message with an error prefix to cout
 */
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

	cerr << "[" << timestamp << "]" << prefix << message << endl;
}


/*
 * Returns the index of the biggest double in list
 * If two doubles are equal, returns the index of the first one
 */
int Tools::findMaxIndex(list<double> &l) {
	list<double>::iterator listI = l.begin();
	double probmax;
	int index;

	for (int i = 0; listI != l.end();i++) {
		if(*listI > probmax) {
			probmax = *listI;
			index = i;
		}

		listI++;
	}
	return index;
}