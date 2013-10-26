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

package org.carcv.impl.core.detect;

import java.util.List;

import org.carcv.core.detect.SpeedDetector;
import org.carcv.core.model.AbstractCarImage;

/**
 * 
 */
public class SpeedDetectorImpl implements SpeedDetector { //TODO 1 Test and implement SpeedDetectorImpl

    /**
     * 
     */
    public SpeedDetectorImpl() {

    }

    /* (non-Javadoc)
     * @see org.carcv.core.detect.Detector#detect(org.carcv.core.input.CarImage)
     */
    @Override
    public String detect(final List<? extends AbstractCarImage> images) {
        return detectSpeed(images).toString();
    }

    /* (non-Javadoc)
     * @see org.carcv.core.detect.SpeedDetector#detectSpeed(org.carcv.core.input.CarImage)
     */
    @Override
    public Number detectSpeed(final List<? extends AbstractCarImage> images) {
        Integer speed = 0;

        return speed;
    }

}