/**
 * 
 */
package org.carcv.impl.core.input;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.carcv.core.input.CarImageLoader;
import org.carcv.impl.core.model.file.FileCarImage;


/**
 * @author oskopek
 *
 */
public final class FileCarImageLoader extends CarImageLoader {
    
    private FileDiscoverer discoverer;

    /**
     * 
     */
    public FileCarImageLoader(FileDiscoverer discoverer) {
        this.discoverer = discoverer;
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
