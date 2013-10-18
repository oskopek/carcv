/**
 * 
 */
package org.carcv.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collection;
import java.util.List;

/**
 * @author oskopek
 *
 */
public class FileDiscoverer extends SimpleFileVisitor<Path> { //TODO: test everything
    
    private static FileDiscoverer fileDiscoverer;
    
    private static Path baseDirectory;
    
    private List<Path> knownPaths;
    
    private Integer lastGottenId;

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
    final public static FileDiscoverer getFileDiscoverer() throws IllegalStateException {
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
    
    
    public void discover() throws IOException {
        Files.walkFileTree(baseDirectory, this);
    }
    
    public Collection<? extends Path> getNew() {
        if(lastGottenId == null) {
            return knownPaths;     
        } else {
            return knownPaths.subList(lastGottenId+1, knownPaths.size()-1);
        }  
    }
    
    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        if(!Files.isRegularFile(file, LinkOption.NOFOLLOW_LINKS)) {
            System.err.println("DEBUG: Image directory contains a non-regular file!");
            return FileVisitResult.CONTINUE;
        }
        
        if(knownPaths.contains(file)) {
            return FileVisitResult.CONTINUE;
        }
        else {
            ImageFile iFile = new ImageFile(file);
            ImageQueue.getQueue().add(iFile);
            knownPaths.add(file);
            return FileVisitResult.CONTINUE;
        }
    }
        
}