/**
 * 
 */
package org.carcv.core.recognize;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

import org.carcv.core.model.Entry;
import org.carcv.impl.core.input.FileCarImageLoader;
import org.carcv.impl.core.input.FileDiscoverer;

/**
 * @author oskopek
 *
 */
public class CarRecognizerImpl extends CarRecognizer {
    
    private Path inDir;
    private Path outDir;
    
    private FileCarImageLoader loader;
    
    private ArrayList<Entry> batch;
    
    
    
    public CarRecognizerImpl(Path inDir, Path outDir) {
        this.inDir = inDir;
        this.outDir = outDir;
        
        loader = new FileCarImageLoader(new FileDiscoverer(inDir));
        
        
    }

    /* (non-Javadoc)
     * @see org.carcv.core.recognize.CarRecognizer#recognize()
     */
    @Override
    public void recognize() throws IOException {
        batch = (ArrayList<Entry>) loader.getBatch();
    }

}
