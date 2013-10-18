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
import java.util.ArrayList;
import java.util.List;

/**
 * @author oskopek
 * 
 */
public class FileDiscoverer extends SimpleFileVisitor<Path> {

    //private static FileDiscoverer fileDiscoverer;

    final private Path baseDirectory;

    final private List<Path> knownPaths;
    
    final private ImageQueue queue;

    //private Integer lastGottenIndex = -1;

    /**
     * 
     */
    public FileDiscoverer(Path baseDirectory, final ImageQueue queue) {
        this.baseDirectory = baseDirectory;
        this.knownPaths = new ArrayList<>();
        //this.lastGottenIndex = -1;
        this.queue = queue;
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
    
    /*
    public static void init(Path basedir) {
        fileDiscoverer = new FileDiscoverer(basedir);
    }

    
    final public static FileDiscoverer getFileDiscoverer() throws IllegalStateException {
        if (fileDiscoverer == null) {
            throw new IllegalStateException(
                    "Static FileDiscoverer.fileDiscoverer isn't initialized! Use FileDiscoverer.init()");
        }
        return fileDiscoverer;
    }
    */

    /**
     * @return the baseDirectory
     */
    public Path getBaseDirectory() {
        return baseDirectory;
    }

    public void discover() throws IOException {
        Files.walkFileTree(baseDirectory, this);
    }

    /*
    public Collection<Path> getNew() {
        List<Path> newPaths = knownPaths.subList(lastGottenIndex + 1, knownPaths.size());
        lastGottenIndex = knownPaths.size() - 1;
        
        //clone
        List<Path> result = new ArrayList<>();
        
        for(Path p : newPaths) {
            result.add(p);
        }
        
        return result;

    }*/

    /**
     * @return the queue
     */
    public ImageQueue getQueue() {
        return queue;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        if (!Files.isRegularFile(file, LinkOption.NOFOLLOW_LINKS)) {
            System.err.println("DEBUG: Image directory contains a non-regular file!");
            return FileVisitResult.CONTINUE;
        }

        if (knownPaths.contains(file)) {
            return FileVisitResult.CONTINUE;
        } else {
            ImageFile iFile = new ImageFile(file);
            queue.add(iFile);
            knownPaths.add(file);
            return FileVisitResult.CONTINUE;
        }
    }

}