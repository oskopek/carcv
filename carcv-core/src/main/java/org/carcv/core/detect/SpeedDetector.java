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
import org.carcv.core.model.SpeedUnit;

/**
 * Abstraction of a speed detector. Detects (calculates) the speed of a given car on the list of input images.
 * 
 * <p>
 * The list of input images should try to be of the same (in real life) car.
 * 
 * <p>
 * The default speed measuring unit is {@link SpeedUnit#KPH} (kilometers per hour, km/h, kmh^-1, ...)
 */
public abstract class SpeedDetector implements Detector {

    /**
     * Calculates the speed of a given car on the list of images.
     * 
     * @param images Should be non-null, don't have to be loaded
     * @return A Double value representing the speed in {@link SpeedUnit#KPH}
     */
    public abstract Double detectSpeed(final List<? extends AbstractCarImage> images);
}