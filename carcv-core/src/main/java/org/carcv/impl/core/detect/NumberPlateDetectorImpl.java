/**
 * 
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
 * Image must be loaded!
 * 
 * @author oskopek
 * 
 */
public class NumberPlateDetectorImpl implements NumberPlateDetector {

    @Override
    public String detect(final List<? extends AbstractCarImage> images) {
        return detectPlateText(images);
    }

    @Override
    public String detectPlateText(final List<? extends AbstractCarImage> images) {

        Intelligence intel = null;
        try {
            intel = new Intelligence();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            return null;
        }

        ArrayList<String> numberPlates = new ArrayList<>();

        for (int i = 0; i < images.size(); i++) {

            try {
                numberPlates.add(i, intel.recognize(new CarSnapshot(images.get(i).getImage())));
            } catch (Exception e) {
                return null;
            }
        }

        return getAverageNumberPlate(numberPlates);
    }

    @Override
    public String detectPlateOrigin(final List<? extends AbstractCarImage> images) {
        return "UN"; //TODO 3 Unimplemented number plate origin
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