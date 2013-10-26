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

#include "opencv2/objdetect/objdetect.hpp"
#include <opencv2/core/core.hpp>

#include <boost/filesystem.hpp>

#include "carimg.hpp"

using namespace std;
using namespace cv;

namespace fs = boost::filesystem;

class FileIO {
public:

	/*
	 * Save CarImg objects to the place their path (USE FOR UNIQUE CARS)
	 */
	static void saveCarImgList(list<CarImg> carList);

	/*
	 * Save CarImg objects to carDir (USE FOR UNIQUE CARS)
	 */
	static void saveCarImgList(list<CarImg> carList, fs::path carListDir);

	/*
	 * Save list<list<CarImg> > objects to carsDir
	 */
	static void saveCars(list<list<CarImg> > cars, fs::path carsDir);

	/*
	 * Load/parse list<CarImg> objects from carsDir
	 */
	static list<list<CarImg> > loadCars(fs::path carsDir);

	/*
	 * Load/parse CarImg objects from carDir
	 */
	static list<CarImg> loadCarImgList(fs::path carDir);

	/*
	 * Load/parse CarImg objects from paths in carList
	 */
	static list<CarImg> loadCarImgList(list<string> carList);
};