/**
 * 
 */
package org.carcv.impl.core.detect;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import net.sf.javaanpr.imageanalysis.CarSnapshot;
import net.sf.javaanpr.intelligence.Intelligence;

import org.carcv.core.detect.NumberPlateDetector;
import org.carcv.core.model.AbstractCarImage;
import org.carcv.core.model.file.FileCarImage;
import org.xml.sax.SAXException;

/**
 * @author oskopek
 *
 */
public class NumberPlateDetectorImpl implements NumberPlateDetector {//TODO: test and implement NumberPlateDetectorImpl

    @Override
    public String detect(final AbstractCarImage image) {
        return detectPlateText((FileCarImage) image);
    }

    @Override
    public String detectPlateText(final FileCarImage image) {
        Intelligence intel = null;
        try {
            intel = new Intelligence();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            return null;
        }
        String lp = "";
        try {
            lp = intel.recognize(new CarSnapshot(image.getImage()));
        } catch (Exception e) {
            return null;
        }
        return lp;
    }
    
    @Override
    public String detectPlateOrigin(final FileCarImage image) {
        return "UN"; //TODO: Unimplemented number plate origin
    }

}
