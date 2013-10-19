/**
 * 
 */
package org.carcv.core.detect;

import org.carcv.core.input.CarImage;

/**
 * @author oskopek
 *
 */
public interface NumberPlateDetector extends Detector {

    public String detectPlate(final CarImage image);
    
}
