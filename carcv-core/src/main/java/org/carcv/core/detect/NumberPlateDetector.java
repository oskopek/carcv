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

    public String detectPlateText(final AbstractCarImage image);
    
    public String detectPlateOrigin(final AbstractCarImage image);
    
}
