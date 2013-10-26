/**
 * 
 */
package org.carcv.core.input;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.carcv.core.model.file.FileEntry;

/**
 * @author oskopek
 *
 */
public class DirectoryWatcher {
    
    private Path rootDir;
    
    private List<Path> knownDirs;
    
    private List<FileEntry> entries;
    
    private Integer lastToIndex;
    
    public DirectoryWatcher(Path rootDir) {
        knownDirs = new ArrayList<>();
        entries = new ArrayList<>();
        
        this.rootDir = rootDir;
        this.lastToIndex = 0;
    }
    
    
    
    public void discover() throws IOException {
        DirectoryStream<Path> stream = Files.newDirectoryStream(rootDir);
        
        for(Path p : stream) {
            if(!Files.isDirectory(p)) {
                continue;
            }
            
            if(knownDirs.contains(p)) {
                continue;
            }
            
            knownDirs.add(p);
            FileEntry e = DirectoryLoader.load(p);
            entries.add(e);
        }
    }
    
    public boolean hasNewEntries() {
        if(lastToIndex>=entries.size()) {
            return false;
        } else {
            return true;
        }
    }
    
    public List<FileEntry> getNewEntries() {
        if(!hasNewEntries()) {
            return new ArrayList<FileEntry>();
        }
        
        List<FileEntry> newEntries = entries.subList(lastToIndex, entries.size());
        lastToIndex = entries.size();
        return newEntries;
    }
    
    public List<FileEntry> getEntries() {
        return entries;
    }
}
