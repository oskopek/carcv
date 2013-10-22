/**
 * 
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
import org.carcv.core.model.file.FileCarImage;
import org.xml.sax.SAXException;

/**
 * Image must be loaded!
 * 
 * @author oskopek
 * 
 */
public class NumberPlateDetectorImpl implements NumberPlateDetector {//TODO: test NumberPlateDetectorImpl

    @Override
    public String detect(final List<? extends AbstractCarImage> images) {
        @SuppressWarnings("unchecked")
        List<FileCarImage> fList = (List<FileCarImage>) images;

        return detectPlateText(fList);
    }

    @Override
    public String detectPlateText(final List<FileCarImage> images) {

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

        return NumberPlateProbabilityMatcher.getAverageNumberPlate(numberPlates);
    }

    @Override
    public String detectPlateOrigin(final List<FileCarImage> images) {
        return "UN"; //TODO: Unimplemented number plate origin
    }

}