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

template <class V>
Mat vector2Mat1D(vector<V> vec) {
	Mat result;

	Mat_<V> templ = Mat_<V>(vec.size(), 1);

	for(typename vector<V>::iterator i = vec.begin(); i != vec.end(); ++i) {
		templ << *i;
	}

	result = (templ);

	return result;
}

template <class V>
Mat vector2Mat2D(vector< vector<V> > vec) {
	Mat result;

	Mat_<V> templ = Mat_<V>(vec.size(), vec.at(0).size()); //todo: vec.at(0).size() remove this hack

	for(typename vector< vector<V> >::iterator i = vec.begin(); i != vec.end(); ++i) {
		for(typename vector<V>::iterator j = (*i).begin(); j != (*i).end(); ++j) {
			templ << *j;
		}
	}

	result = (templ);

	return result;
}