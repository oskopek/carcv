/* 
 * Copyright 2012 CarCV Development Team
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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