/**
 * 
 */
package org.carcv.impl.core.detect;

import org.carcv.core.detect.SpeedDetector;
import org.carcv.core.input.CarImage;

/**
 * @author oskopek
 *
 */
public class SpeedDetectorImpl implements SpeedDetector {

    /**
     * 
     */
    public SpeedDetectorImpl() {
        
    }

    /* (non-Javadoc)
     * @see org.carcv.core.detect.Detector#detect(org.carcv.core.input.CarImage)
     */
    @Override
    public String detect(CarImage image) {
        return detectSpeed(image).toString();
    }

    /* (non-Javadoc)
     * @see org.carcv.core.detect.SpeedDetector#detectSpeed(org.carcv.core.input.CarImage)
     */
    @Override
    public Number detectSpeed(CarImage image) {
        Integer speed = 0;
        
        // TODO speed detector implementation
        return speed;
    }

}
