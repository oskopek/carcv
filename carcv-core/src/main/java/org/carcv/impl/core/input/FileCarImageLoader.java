/**
 * 
 */
package org.carcv.impl.core.input;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.carcv.core.input.CarImageLoader;


/**
 * @author oskopek
 *
 */
public final class FileCarImageLoader extends CarImageLoader {
    
    private static FileCarImageLoader loader;
    
    private FileDiscoverer discoverer;

    /**
     * 
     */
    public FileCarImageLoader(FileDiscoverer discoverer) {
        this.discoverer = discoverer;
        
        FileCarImageLoader.loader = this;
    }
    
    /**
     * 
     * @return static loader instance, or null if not initialized
     */
    public static FileCarImageLoader getLoader() {
        if(loader == null) {
            return null;
        } 
        return loader;
    }
    
    @Override
    public List<FileCarImage> getBatch() throws IOException {

        discoverer.discover();

        List<FileCarImage> list = new ArrayList<>();

        while (!discoverer.getQueue().isEmpty()) {
            list.add(discoverer.getQueue().poll());
        }

        return list;
    }

    @Override
    public List<FileCarImage> getBatchNoDiscover() {

        List<FileCarImage> list = new ArrayList<>();

        while (!discoverer.getQueue().isEmpty()) {
            list.add(discoverer.getQueue().poll());
        }

        return list;
    }

}
