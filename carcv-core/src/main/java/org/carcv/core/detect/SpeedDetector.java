/**
 * 
 */
package org.carcv.core.detect;

import org.carcv.core.model.AbstractCarImage;

/**
 * @author oskopek
 *
 */
public interface SpeedDetector extends Detector {

    public Number detectSpeed(final AbstractCarImage image);
}
