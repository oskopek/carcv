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

package org.carcv.core.recognize;

import java.io.IOException;

/**
 *
 */
public abstract class CarRecognizer {

    /**
     * This method is supposed to read new <code>Entry</code>-s from one or more data source, detect individual cars, detect
     * their speed and number plate, afterwards writing <code>CarData</code> (possibly the <code>List of CarImage-s</code> too)
     * to one or more data sources. <BR>
     * Getting the data afterwards is the responsibility of your client externally from the input/output datasources.
     * 
     * @throws IOException
     */
    public abstract void recognize() throws IOException;

}