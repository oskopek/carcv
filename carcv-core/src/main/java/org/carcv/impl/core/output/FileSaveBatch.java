/**
 * 
 */
package org.carcv.impl.core.output;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Properties;

import org.carcv.core.model.AbstractEntry;
import org.carcv.core.model.file.FileEntry;
import org.carcv.core.output.SaveBatch;

/**
 * @author oskopek
 *
 */
public class FileSaveBatch implements SaveBatch { //TODO: test FileSaveBatch

    private Path directory;
    
    /**
     * 
     */
    public FileSaveBatch(Path directory) {
        this.directory = directory;
    }

    /* (non-Javadoc)
     * @see org.carcv.core.output.SaveBatch#save(java.util.ArrayList)
     */
    @Override
    public boolean save(final ArrayList<? extends AbstractEntry> batch) { 
        final ArrayList<FileEntry> fileBatch = (ArrayList<FileEntry>) batch;
        
        return saveFileEntry(fileBatch);
    }
    
    public boolean saveFileEntry(final ArrayList<FileEntry> fileBatch) {
        for(FileEntry entry : fileBatch) {
            try {
                saveFileEntry(entry);
            } catch (IOException e) {
                //e.printStackTrace();
                return false;
            }
        }
        
        return true;
    }
    
    private void saveFileEntry(FileEntry e) throws IOException {
        Properties p = new Properties();
        
        p.setProperty("filepath"    , e.getCarImage().getFilepath().toString());
        
        
        p.setProperty("numberplate-origin"   , e.getCarData().getNumberPlate().getOrigin());
        p.setProperty("numberplate-text"     , e.getCarData().getNumberPlate().getText());
        
        p.setProperty("speed-value" , e.getCarData().getSpeed().getSpeed().toString());
        p.setProperty("speed-unit"  , e.getCarData().getSpeed().getUnit().toString());
        
        p.setProperty("address-city", e.getCarData().getAddress().getCity());
        p.setProperty("address-street", e.getCarData().getAddress().getStreet());
        p.setProperty("address-streetNo", e.getCarData().getAddress().getStreetNumber().toString());
        p.setProperty("address-country", e.getCarData().getAddress().getCountry());
        p.setProperty("address-refNo", e.getCarData().getAddress().getReferenceNumber().toString());
        p.setProperty("address-postalCode", e.getCarData().getAddress().getPostalCode());
        p.setProperty("address-lat", e.getCarData().getAddress().getLatitude().toString());
        p.setProperty("address-long", e.getCarData().getAddress().getLongitude().toString());
        
        p.setProperty("timestamp", e.getCarData().getTimestamp().toString());
        
        //TODO: add everything here -- should be done
        
        Path outFilePath = Paths.get(directory.toString(), e.getCarImage().getFilepath().getFileName().toString());
        
        FileOutputStream fous = new FileOutputStream(outFilePath.toFile());
        p.store(fous, e.getCarImage().getFilepath().getFileName().toString());
        
    }

}
