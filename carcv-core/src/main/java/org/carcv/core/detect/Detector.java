/**
 * 
 */
package org.carcv.core.detect;

import org.carcv.core.input.CarImage;

/**
 * @author oskopek
 *
 */
public interface Detector {

    public String detect(final CarImage image);
}
