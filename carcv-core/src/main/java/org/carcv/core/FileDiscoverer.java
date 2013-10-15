/**
 * 
 */
package org.carcv.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;

/**
 * @author oskopek
 *
 */
public class FileDiscoverer { //TODO: finish
    
    private static FileDiscoverer fileDiscoverer;
    
    private static Path baseDirectory;
    
    private List<Path> knownPaths;

    /**
     * 
     */
    public FileDiscoverer(Path baseDirectory) {
        FileDiscoverer.setBaseDirectory(baseDirectory);
    }
    
    /**
     * @deprecated use java.nio
     * @param filename
     * @return
     */
    public InputStream getResourceAsStream(String filename) {
        String corrected = filename;

        URL f = this.getClass().getResource(corrected);
        if (f != null) {
            return this.getClass().getResourceAsStream(corrected);
        }

        if (filename.startsWith("/")) {
            corrected = filename.substring(1);
        } else if (filename.startsWith("./")) {
            corrected = filename.substring(2);
        } else {
            corrected = "/" + filename;
        }

        f = this.getClass().getResource(corrected);

        if (f != null) {
            return this.getClass().getResourceAsStream(corrected);
        }

        File file = new File(filename);
        if (file.exists()) {
            FileInputStream fis = null;

            try {
                fis = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            return fis;
        }
        return null;
    }


    public static void init(Path basedir) {
        fileDiscoverer =  new FileDiscoverer(basedir);        
    }


    /**
     * @return the filed
     */
    final public static FileDiscoverer getFileDiscoverer() {
        if(fileDiscoverer == null) {
            throw new IllegalStateException("Static FileDiscoverer.fileDiscoverer isn't initialized! Use FileDiscoverer.init()");
        }
        return fileDiscoverer;
    }

    /**
     * @return the baseDirectory
     */
    public static Path getBaseDirectory() {
        return baseDirectory;
    }

    /**
     * @param baseDirectory the baseDirectory to set
     */
    public static void setBaseDirectory(Path baseDirectory) {
        FileDiscoverer.baseDirectory = baseDirectory;
    }
    
    
    public void discover() { //TODO
        
    }
    
    public Collection<? extends Path> getNew() {//TODO
        return knownPaths;        
    }

}
