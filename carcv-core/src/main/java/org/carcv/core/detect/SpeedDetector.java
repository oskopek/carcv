/**
 * 
 */
package org.carcv.core.detect;

import org.carcv.core.input.CarImage;

/**
 * @author oskopek
 *
 */
public interface SpeedDetector extends Detector {

    public Number detectSpeed(final CarImage image);
}
