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
public class NumberPlateDetectorImpl implements NumberPlateDetector {//TODO: test and implement NumberPlateDetectorImpl

    @Override
    public String detect(final AbstractCarImage image) {
        return detectPlateText(image);
    }

    @Override
    public String detectPlateText(final AbstractCarImage image) {
        // TODO number plate detector implementation
        return null;
    }
    
    @Override
    public String detectPlateOrigin(final AbstractCarImage image) {
        // TODO number plate detector implementation
        return null;
    }

}
