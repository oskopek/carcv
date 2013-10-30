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
 *
 */
public abstract class CarSorter {

    /**
     * Returns a list of fileEntries from given images in a fileentry directory --> this means you should probably remove this
     * fileentry from the list after merging this List in.
     * 
     * @param batchDir the directory where all images reside
     * @return a list of individual real cars in multiple FileEntry-s
     */
    public abstract List<FileEntry> sortIntoCars(FileEntry batchDir) throws IOException;

    public abstract boolean carsEquals(FileCarImage one, FileCarImage two);
    
    public abstract boolean carsEquals(FileCarImage one, String twoPlate);
    
    public abstract boolean carsEquals(String onePlate, String twoPlate);
}