/**
 * 
 */
package org.carcv.impl.core.input;

import org.carcv.core.input.CarImageLoader;
import org.carcv.core.model.CarData;


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
    public CarData fetchAllCarData() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @return the discoverer
     */
    @Override
    public FileDiscoverer getDiscoverer() {
        return discoverer;
    }
    
}
