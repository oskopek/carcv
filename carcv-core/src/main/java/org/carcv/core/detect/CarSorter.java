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

package org.carcv.core.detect;

import java.io.IOException;
import java.util.List;

import org.carcv.core.model.file.FileCarImage;
import org.carcv.core.model.file.FileEntry;

/**
 * Provides methods for arranging a sequence of images (video frames) into groups
 * of individual real cars depicted in them.
 * 
 */
public abstract class CarSorter {

    /**
     * Turns the FileEntry directly loaded from input directory (containing all images from a given video)
     * into several FileEntry-s, corresponding to the individual real cars on the video. 
     * 
     * <p>Be careful to clone the CarData when assigning it from <code>batchEntry</code> to all new FileEntries.
     *
     * @param batchEntry <code>FileEntry</code> containing all images from the new batch
     * 
     * @return individual real cars as a list of <code>FileEntry</code>-s
     */
    public abstract List<FileEntry> sortIntoCars(FileEntry batchEntry) throws IOException;

    /**
     * Wrapper method for {@link CarSorter#carsEquals(String, String)}.
     * 
     * @param one <code>A FileCarImage</code>, has to have a valid filePath set, not null
     * @param two <code>A FileCarImage</code>, has to have a valid filePath set, not null
     * @return true if it is probable that the cars on the two <code>FileCarImage</code>s are equal
     */
    public abstract boolean carsEquals(FileCarImage one, FileCarImage two);

    /**
     * Wrapper method for {@link CarSorter#carsEquals(String, String)}.
     *
     * @param one <code>A FileCarImage</code>, has to have a valid filePath set, not null
     * @param twoPlate Text of the number plate of the second Car to compare
     */
    public abstract boolean carsEquals(FileCarImage one, String twoPlate);

    /**
     * Compares two images, returns true if the two images are of the same car.
     * 
     * @param onePlate Text of the number plate of the first Car to compare
     * @param twoPlate Text of the number plate of the second Car to compare
     * @return true if the two images are of the same car
     */
    public abstract boolean carsEquals(String onePlate, String twoPlate);
}