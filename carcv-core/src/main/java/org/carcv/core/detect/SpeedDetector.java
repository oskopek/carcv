/**
 * 
 */
package org.carcv.core.detect;

import java.util.List;

import org.carcv.core.model.AbstractCarImage;

/**
 * @author oskopek
 *
 */
public interface SpeedDetector extends Detector {

    public Number detectSpeed(final List<? extends AbstractCarImage> images);
}
