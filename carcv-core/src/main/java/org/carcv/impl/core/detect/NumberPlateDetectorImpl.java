/**
 * 
 */
package org.carcv.impl.core.detect;

import org.carcv.core.detect.NumberPlateDetector;
import org.carcv.core.model.AbstractCarImage;

/**
 * @author oskopek
 *
 */
public class NumberPlateDetectorImpl implements NumberPlateDetector {

    @Override
    public String detect(final AbstractCarImage image) {
        return detectPlate(image);
    }

    @Override
    public String detectPlate(final AbstractCarImage image) {
        // TODO number plate detector implementation
        return null;
    }

}
