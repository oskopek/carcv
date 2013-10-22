/**
 * 
 */
package org.carcv.web.beans;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.ejb.Stateless;

import org.carcv.impl.core.recognize.FileCarRecognizer;

/**
 * @author oskopek
 *
 */
@Stateless
public class RecognizerBean {
    
    //TODO: somehow load these
    final private static Path inDir = Paths.get("/home/oskopek/dev/java/carcv_data/in");    
    final private static Path outDir = Paths.get("/home/oskopek/dev/java/carcv_data/out");
    
    
    private FileCarRecognizer recognizer = new FileCarRecognizer(inDir, outDir);
    
    public void recognize() throws IOException {
        recognizer.recognize();
    }
    

}
