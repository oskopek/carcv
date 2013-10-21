/**
 * 
 */
package org.carcv.impl.core.recognize;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

import org.carcv.core.model.NumberPlate;
import org.carcv.core.model.Speed;
import org.carcv.core.model.file.FileEntry;
import org.carcv.core.recognize.CarRecognizer;
import org.carcv.impl.core.detect.NumberPlateDetectorImpl;
import org.carcv.impl.core.detect.SpeedDetectorImpl;
import org.carcv.impl.core.input.FileCarImageLoader;
import org.carcv.impl.core.input.FileDiscoverer;
import org.carcv.impl.core.output.FileSaveBatch;

/**
 * @author oskopek
 *
 */
public class FileCarRecognizer extends CarRecognizer { //TODO: Test FileCarRecognizer
    
    private FileCarImageLoader loader;
    
    private FileSaveBatch saver;
    
    
    
    public FileCarRecognizer(Path inDir, Path outDir) {        
        //should load CarData with Address; FileCarImage with filepath
        loader = new FileCarImageLoader(new FileDiscoverer(inDir));
        
        saver = new FileSaveBatch(outDir);
        
    }

    /* (non-Javadoc)
     * @see org.carcv.core.recognize.CarRecognizer#recognize()
     */
    @Override
    public void recognize() throws IOException {
        final ArrayList<FileEntry> batch = (ArrayList<FileEntry>) loader.getBatch();
        
        detectSpeed(batch);
        detectNumberPlate(batch);
        
        saver.save(batch);
    }
    
    private void detectSpeed(final ArrayList<FileEntry> batch) {
        SpeedDetectorImpl sd = new SpeedDetectorImpl();
        
        for(FileEntry entry : batch) {
            Double d = (Double) sd.detectSpeed(entry.getCarImage());
            
            entry.getCarData().setSpeed(new Speed(d));
        }
    }
    
    private void detectNumberPlate(final ArrayList<FileEntry> batch) {
        NumberPlateDetectorImpl npd = new NumberPlateDetectorImpl();
        
        for(FileEntry entry : batch) {
            String text = npd.detectPlateText(entry.getCarImage());
            String origin = npd.detectPlateOrigin(entry.getCarImage());
            
            entry.getCarData().setNumberPlate(new NumberPlate(text, origin));
        }
    }

}
