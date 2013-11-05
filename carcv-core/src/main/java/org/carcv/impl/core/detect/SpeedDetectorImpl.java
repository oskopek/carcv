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
import org.carcv.impl.core.input.FFMPEG_VideoHandler;

/**
 * A Singleton implementation of a SpeedDetector based on a simple, non-precise equation:
 * 
 * <p>
 * <code>speed = (numberOfImagesInList / {@link FFMPEG_VideoHandler#defaultFrameRate defaultFrameRate})
 * * 10 (meters; default value) * 3.6 (conversion rate from ms to kph)</code>
 */
public class SpeedDetectorImpl extends SpeedDetector { // TODO 2 Add the complex speed calculation method

    private static SpeedDetectorImpl detector = new SpeedDetectorImpl();

    private SpeedDetectorImpl() {

    }

    /**
     * Returns a reference to the static singleton instantiation of SpeedDetectorImpl
     * 
     * @return reference to static SpeedDetectorImpl instance
     */
    public static SpeedDetectorImpl getInstance() {
        return detector;
    }

    @Override
    public String detect(final List<? extends AbstractCarImage> images) {
        return detectSpeed(images).toString();
    }

    @Override
    public Double detectSpeed(final List<? extends AbstractCarImage> images) {
        // speed = (numOfImages/frameRate) * 3.6 (conversion rate from ms^-1 to kph) * 10 (meters, in which car is in speed box)
        return ((double) images.size() / (double) FFMPEG_VideoHandler.defaultFrameRate) * 3.6d * 10d;
    }

}