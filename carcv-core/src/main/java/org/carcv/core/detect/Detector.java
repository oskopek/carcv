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

package org.carcv.core.detect;

import java.util.List;

import org.carcv.core.model.AbstractCarImage;

/**
 * Abstraction of a detector, given a list of <code>CarImage</code>s, detects what it should and returns a String
 * representation.
 */
public interface Detector {

    /**
     * Detects a value based on the input list of images.
     *
     * @param images A non-null list of <code>CarImage</code>s
     * @return A String representation of the detected value
     */
    public String detect(final List<? extends AbstractCarImage> images);
}