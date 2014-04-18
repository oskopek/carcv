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

import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import com.google.common.collect.TreeMultiset;
import net.sf.javaanpr.imageanalysis.CarSnapshot;
import net.sf.javaanpr.intelligence.Intelligence;

import org.carcv.core.detect.NumberPlateDetector;
import org.carcv.core.model.AbstractCarImage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

/**
 * An implementation of <code>NumberPlateDetector</code> based on <a
 * href="https://github.com/oskopek/javaanpr.git">JavaANPR</a>.
 *
 * <p>
 * Make sure all images are loaded in advance!
 */
public class NumberPlateDetectorImpl extends NumberPlateDetector {

    final private static Logger LOGGER = LoggerFactory.getLogger(NumberPlateDetectorImpl.class);

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
            numberPlates.add(intel.recognize(new CarSnapshot(image.getImage())));
        }
        return getAverageNumberPlate(numberPlates);
    }

    @Override
    public String detectPlateOrigin(final List<? extends AbstractCarImage> images) {
        return "UN"; // TODO 3 Unimplemented number plate origin
    }

    private static String getAverageNumberPlate(final List<String> numberPlates) {
        final Multiset<String> plateSet = TreeMultiset.create();
        plateSet.addAll(numberPlates);
        String popular = Multisets.copyHighestCountFirst(plateSet).iterator().next();
        LOGGER.debug("Most popular plate is {}, occurrences {}", popular, plateSet.count(popular));
        return popular;
    }
}