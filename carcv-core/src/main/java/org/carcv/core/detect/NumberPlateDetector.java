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

import org.carcv.core.model.AbstractCarImage;

import java.util.List;

/**
 * Abstraction of a Number Plate detector. Detects the text of the plate, and the country of origin.
 * <p>
 * The list of input images should try to be of the same (in real life) car.
 */
public abstract class NumberPlateDetector implements Detector {

    /**
     * Detects the text (usually an alpha-numerical String) of the Number Plate. The text is a unique identification
     * number (plate number) registered in a specific country.
     *
     * @param images Must be non-null, loaded externally!
     * @return A String containing the text of the plate, or null if an error occurred
     */
    public abstract String detectPlateText(List<? extends AbstractCarImage> images);

    /**
     * Detects the country where the Number Plate is registered.
     *
     * @param images Must be non-null, loaded externally!
     * @return A String containing the shortcut for the country of origin of the plate, or null if an error occurred
     */
    public abstract String detectPlateOrigin(List<? extends AbstractCarImage> images);

}
