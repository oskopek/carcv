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
public class NumberPlateDetectorImpl implements NumberPlateDetector {

    @Override
    public String detect(final CarImage image) {
        return detectPlate(image);
    }

    @Override
    public String detectPlate(final CarImage image) {
        // TODO number plate detector implementation
        return null;
    }

}
