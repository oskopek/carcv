/**
 * 
 */
package org.carcv.web.beans;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.ejb.Stateless;

import org.carcv.core.model.file.FileCarImage;
import org.carcv.impl.core.detect.NumberPlateDetectorImpl;

/**
 * @author oskopek
 * @deprecated
 */
@Stateless
public class AnprBean {
	
    final private static NumberPlateDetectorImpl detector = new NumberPlateDetectorImpl();
    
    public String recognize(Path file) throws IOException, Exception {
        FileCarImage f = new FileCarImage(file);
        
        f.loadImage();
        
        String s = detector.detectPlateText(f);
        
        f.close();
        
        return s;
    }
    
    public String recognize(InputStream is) throws IOException {
        FileCarImage f = new FileCarImage(Paths.get(""));
        f.loadImage(is);
        
        String s = detector.detectPlateText(f);
        
        f.close();
        
        return s;
    }

}
