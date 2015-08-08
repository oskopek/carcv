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

import org.carcv.core.detect.CarSorter;
import org.carcv.core.detect.NumberPlateDetector;
import org.carcv.core.detect.SpeedDetector;
import org.carcv.core.io.DirectoryWatcher;
import org.carcv.core.io.Saver;
import org.carcv.core.model.AbstractEntry;

import java.io.IOException;

/**
 * An abstraction of the recognition process.
 */
public abstract class CarRecognizer {

    /**
     * This method is reads new <code>Entry</code>-s (any object that extends {@link AbstractEntry}) from one or more
     * data source, detects individual cars, detects their speed and number plate, afterwards saves them
     * to one or more data sources.
     * <p>
     * Getting the data afterwards is the responsibility of your client externally from the input/output data sources.
     *
     * @throws IOException if an IOException occurs
     * @see DirectoryWatcher#getNewEntries()
     * @see CarSorter#sortIntoCars(org.carcv.core.model.file.FileEntry)
     * @see NumberPlateDetector#detectPlateOrigin(java.util.List)
     * @see SpeedDetector#detectSpeed(java.util.List)
     * @see Saver#save(java.util.List)
     */
    public abstract void recognize() throws IOException;
}
