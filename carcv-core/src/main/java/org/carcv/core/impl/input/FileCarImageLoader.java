/**
 * 
 */
package org.carcv.core.impl.input;

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

}
