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

import java.util.List;

import org.carcv.core.model.AbstractCarImage;

/**
 *
 */
public interface NumberPlateDetector extends Detector {

    /**
     *
     * @param images - must be loaded!
     * @return String w/ the text of the plate, or null if an error occured
     */
    public String detectPlateText(final List<? extends AbstractCarImage> images);

    /**
     *
     * @param images - must be loaded!
     * @return String w/ the origin of the plate, or null if an error occured
     */
    public String detectPlateOrigin(final List<? extends AbstractCarImage> images);

}