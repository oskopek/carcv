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

package org.carcv.impl.core.detect;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import net.sf.javaanpr.imageanalysis.CarSnapshot;
import net.sf.javaanpr.intelligence.Intelligence;

import org.carcv.core.detect.NumberPlateDetector;
import org.carcv.core.model.AbstractCarImage;
import org.carcv.impl.core.util.CollectionUtils;
import org.xml.sax.SAXException;

/**
 * An implementation of <code>NumberPlateDetector</code> based on <a
 * href="https://github.com/oskopek/javaanpr.git">JavaANPR</a>.
 *
 * <p>
 * Make sure all images are loaded in advance!
 */
public class NumberPlateDetectorImpl extends NumberPlateDetector {

    private static NumberPlateDetectorImpl detector;
    private Intelligence intel;

    public NumberPlateDetectorImpl() throws IllegalStateException {
        try {
            intel = new Intelligence();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            IllegalStateException ise = new IllegalStateException("Error occurred while initializing detector");
            ise.addSuppressed(e);
            throw ise;
        }
    }

    /**
     * Returns a reference to the static singleton instantiation of NumberPlateDetectorImpl
     *
     * @return reference to static NumberPlateDetectorImpl instance, null if an error occurs during initialization
     */
    public static NumberPlateDetectorImpl getInstance() throws IllegalStateException {
        if (detector == null) {
            detector = new NumberPlateDetectorImpl();
        }
        return detector;
    }

    @Override
    public String detect(final List<? extends AbstractCarImage> images) {
        return detectPlateText(images);
    }

    @Override
    public String detectPlateText(final List<? extends AbstractCarImage> images) {
        ArrayList<String> numberPlates = new ArrayList<>();
        for (AbstractCarImage image : images) {
            String plate = intel.recognize(new CarSnapshot(image.getImage()));
            if (plate == null) {
                plate = "null";
            }
            numberPlates.add(plate);
        }
        return CollectionUtils.highestCountElement(numberPlates);
    }

    @Override
    public String detectPlateOrigin(final List<? extends AbstractCarImage> images) {
        return "UN"; // TODO 3 Unimplemented number plate origin
    }
}
