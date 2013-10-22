/**
 * 
 */
package org.carcv.core.detect;

import org.carcv.core.model.file.FileCarImage;

/**
 * @author oskopek
 *
 */
public interface NumberPlateDetector extends Detector {

    /**
     * 
     * @param image - must be loaded!
     * @return String w/ the text of the plate, or null if an error occured
     */
    public String detectPlateText(final FileCarImage image); //TODO: fix to abstract it
    
    /**
     * 
     * @param image - must be loaded!
     * @return String w/ the origin of the plate, or null if an error occured
     */
    public String detectPlateOrigin(final FileCarImage image);//TODO: fix to abstract it
    
}
