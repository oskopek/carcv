/**
 * 
 */
package org.carcv.core.detect;

import java.util.List;

import org.carcv.core.model.file.FileCarImage;

/**
 * @author oskopek
 *
 */
public interface NumberPlateDetector extends Detector {

    /**
     * 
     * @param images - must be loaded!
     * @return String w/ the text of the plate, or null if an error occured
     */
    public String detectPlateText(final List<FileCarImage> images); //TODO: fix to abstract it
    
    /**
     * 
     * @param images - must be loaded!
     * @return String w/ the origin of the plate, or null if an error occured
     */
    public String detectPlateOrigin(final List<FileCarImage> images);//TODO: fix to abstract it
    
}
