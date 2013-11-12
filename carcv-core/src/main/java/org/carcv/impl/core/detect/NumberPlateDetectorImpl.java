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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.xml.parsers.ParserConfigurationException;

import net.sf.javaanpr.imageanalysis.CarSnapshot;
import net.sf.javaanpr.intelligence.Intelligence;

import org.carcv.core.detect.NumberPlateDetector;
import org.carcv.core.model.AbstractCarImage;
import org.xml.sax.SAXException;

/**
 * A Singleton implementation of <code>NumberPlateDetector</code> based on <a
 * href="https://github.com/oskopek/javaanpr.git">JavaANPR</a>.
 *
 * <p>
 * Make sure all images are loaded in advance!
 *
 */
public class NumberPlateDetectorImpl extends NumberPlateDetector {

    private static NumberPlateDetectorImpl detector = new NumberPlateDetectorImpl();

    private NumberPlateDetectorImpl() {

    }

    /**
     * Returns a reference to the static singleton instantiation of NumberPlateDetectorImpl
     *
     * @return reference to static NumberPlateDetectorImpl instance
     */
    public static NumberPlateDetectorImpl getInstance() {
        return detector;
    }

    @Override
    public String detect(final List<? extends AbstractCarImage> images) {
        return detectPlateText(images);
    }

    @Override
    public String detectPlateText(final List<? extends AbstractCarImage> images) {

        Intelligence intel;
        try {
            intel = new Intelligence();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            System.err.println("Error occured while detecting plate text!");
            e.printStackTrace();
            return null;
        }

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
        HashMap<String, Integer> map = new HashMap<>();

        for (String s : numberPlates) {
            Integer count = map.get(s);
            map.put(s, count != null ? count + 1 : 0);
        }

        String popular = Collections.max(map.entrySet(), new Comparator<Entry<String, Integer>>() {

            @Override
            public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        }).getKey();

        return popular;
    }

}