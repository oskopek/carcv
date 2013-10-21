/**
 * 
 */
package org.carcv.impl.core.input;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.carcv.core.input.CarImageLoader;
import org.carcv.core.model.CarData;
import org.carcv.core.model.file.FileCarImage;
import org.carcv.core.model.file.FileEntry;


/**
 * @author oskopek
 *
 */
public final class FileCarImageLoader extends CarImageLoader { //TODO: test FileCarImageLoader and finish impl
    
    private FileDiscoverer discoverer;

    /**
     * 
     */
    public FileCarImageLoader(FileDiscoverer discoverer) {
        this.discoverer = discoverer;
    }
    
    @Override
    public CarData fetchAllCarData() {
        // TODO implement fetchAllCarData
        return null;
    }

    /**
     * @return the discoverer
     */
    @Override
    public FileDiscoverer getDiscoverer() {
        return discoverer;
    }
    
    @Override
    public List<FileEntry> getBatch() throws IOException {
        discoverer.discover();
        
        return getBatchNoDiscover();
    }

    @Override
    public List<FileEntry> getBatchNoDiscover() {
        CarData cd = fetchAllCarData();

        List<FileEntry> list = new ArrayList<>();

        FileEntry e = null;
        
        while (!getDiscoverer().getQueue().isEmpty()) {
            FileCarImage ci = getDiscoverer().getQueue().poll();
            
            e = new FileEntry(cd, ci); //TODO: fix cannot instantiate abstract class error
            
            list.add(e);
        }

        return list;
    }
    
}
