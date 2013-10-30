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

import java.util.ArrayList;
import java.util.List;

import org.carcv.core.model.file.FileCarImage;
import org.carcv.core.model.file.FileEntry;

/**
 *
 */
public class CarSorter {

    /**
     * Returns a list of fileEntries from given images in a fileentry directory --> this means you should probably remove this
     * fileentry from the list after merging this List in.
     * 
     * @param batchDir
     * @return
     */
    public static List<FileEntry> sortIntoCars(FileEntry batchDir) {// TODO 1 Implement: sort them into multiple FileEntries
        List<FileEntry> list = new ArrayList<>();

        List<FileCarImage> images = batchDir.getCarImages();

        for(int i = 1; i < images.size(); i++) {
            boolean equals = carsEquals(images.get(i-1), images.get(i));

            if(equals) {

            }

        }

        return list;
    }

    public static boolean carsEquals(FileCarImage one, FileCarImage two) {
        return numberPlateProbabilityCompare(one. two);
    }

    private static boolean numberPlateProbabilityCompare(FileCarImage one, FileCarImage two) {
        return true;
    }

}