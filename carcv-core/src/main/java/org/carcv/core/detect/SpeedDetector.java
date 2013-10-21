/**
 * 
 */
package org.carcv.core.detect;

import org.carcv.core.model.CarImage;

/**
 * @author oskopek
 *
 */
public interface SpeedDetector extends Detector {

    public Number detectSpeed(final CarImage image);
}
