/**
 * 
 */
package org.carcv.web.beans;

import java.io.IOException;
import java.nio.file.Path;

import javax.ejb.Stateless;

import org.carcv.core.model.file.FileCarImage;
import org.carcv.impl.core.detect.NumberPlateDetectorImpl;

/**
 * @author oskopek
 *
 */
@Stateless
public class AnprBean {
	
    final private static NumberPlateDetectorImpl detector = new NumberPlateDetectorImpl();
    
    public String recognize(Path file) throws IOException, Exception {
        return detector.detectPlateText(new FileCarImage(file));
    }
    

}
