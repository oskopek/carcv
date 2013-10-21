/**
 * 
 */
package org.carcv.impl.core.detect;

import org.carcv.core.detect.NumberPlateDetector;
import org.carcv.core.model.CarImage;

/**
 * @author oskopek
 *
 */
public class NumberPlateDetectorImpl implements NumberPlateDetector {//TODO: test and implement NumberPlateDetectorImpl

    @Override
    public String detect(final CarImage image) {
        return detectPlateText(image);
    }

    @Override
    public String detectPlateText(final CarImage image) {
        // TODO number plate detector implementation
        return null;
    }
    
    @Override
    public String detectPlateOrigin(final CarImage image) {
        // TODO number plate detector implementation
        return null;
    }

}
