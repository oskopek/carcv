/**
 * 
 */
package org.carcv.impl.core.detect;

import java.util.List;

import org.carcv.core.detect.SpeedDetector;
import org.carcv.core.model.AbstractCarImage;

/**
 * @author oskopek
 * 
 */
public class SpeedDetectorImpl implements SpeedDetector { //TODO: test and implement SpeedDetectorImpl

    /**
     * 
     */
    public SpeedDetectorImpl() {

    }

    /* (non-Javadoc)
     * @see org.carcv.core.detect.Detector#detect(org.carcv.core.input.CarImage)
     */
    @Override
    public String detect(final List<? extends AbstractCarImage> images) {
        return detectSpeed(images).toString();
    }

    /* (non-Javadoc)
     * @see org.carcv.core.detect.SpeedDetector#detectSpeed(org.carcv.core.input.CarImage)
     */
    @Override
    public Number detectSpeed(final List<? extends AbstractCarImage> images) {
        Integer speed = 0;

        // TODO speed detector implementation
        return speed;
    }

}
