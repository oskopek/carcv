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

    public String detectPlateText(final CarImage image);
    
    public String detectPlateOrigin(final CarImage image);
    
}
