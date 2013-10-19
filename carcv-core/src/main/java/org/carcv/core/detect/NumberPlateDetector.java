/**
 * 
 */
package org.carcv.core.detect;

import org.carcv.core.model.AbstractCarImage;

/**
 * @author oskopek
 *
 */
public interface NumberPlateDetector extends Detector {

    public String detectPlate(final AbstractCarImage image);
    
}
