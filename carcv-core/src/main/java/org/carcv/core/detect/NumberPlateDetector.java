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
public interface NumberPlateDetector extends Detector {

    /**
     * 
     * @param images
     *            - must be loaded!
     * @return String w/ the text of the plate, or null if an error occured
     */
    public String detectPlateText(final List<? extends AbstractCarImage> images);

    /**
     * 
     * @param images
     *            - must be loaded!
     * @return String w/ the origin of the plate, or null if an error occured
     */
    public String detectPlateOrigin(final List<? extends AbstractCarImage> images);

}
