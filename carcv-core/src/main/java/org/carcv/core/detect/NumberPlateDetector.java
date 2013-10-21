/**
 * 
 */
package org.carcv.core.detect;

import org.carcv.core.model.CarImage;

/**
 * @author oskopek
 *
 */
public interface NumberPlateDetector extends Detector {

    public String detectPlate(final CarImage image);
    
}
