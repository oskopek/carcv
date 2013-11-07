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

package org.carcv.core.io;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
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

        for (Path p : stream) {
            if (!Files.isDirectory(p)) {
                continue;
            }

            if (knownDirs.contains(p)) {
                continue;
            }

            knownDirs.add(p);
            FileEntry e = DirectoryLoader.load(p);
            entries.add(e);
        }
    }

    public boolean hasNewEntries() {
        return lastToIndex < entries.size();
    }

    public List<FileEntry> getNewEntries() {
        if (!hasNewEntries()) {
            return new ArrayList<>();
        }

        List<FileEntry> newEntries = entries.subList(lastToIndex, entries.size());
        lastToIndex = entries.size();
        return newEntries;
    }

    public List<FileEntry> getEntries() {
        return entries;
    }

    /**
     * Deletes given path (directory) and everything under it recursively.
     *
     * <p>
     * Similar to <code>rm -rf path</code>
     * </p>
     *
     * <p>
     * <strong>USE WITH CAUTION!</strong>
     * </p>
     *
     * @param path the path to delete
     * @throws IOException
     */
    public static void deleteDirectory(Path path) throws IOException {
        Files.walkFileTree(path, new FileVisitor<Path>() {

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                // System.out.println("Deleting directory :" + dir);
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                // System.out.println("Deleting file: " + file);
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                // System.out.println(exc.toString());
                return FileVisitResult.CONTINUE;
            }
        });
    }

    /**
     * Check if directory is empty
     *
     * @param dir a Path that isDirectory()
     * @return true if directory contains no files/directories
     * @throws IOException
     */
    public static boolean isDirEmpty(Path dir) throws IOException {
        if (!Files.isDirectory(dir)) {
            throw new IllegalArgumentException("Path " + dir + " is not a directory");
        }

        DirectoryStream<Path> ds = Files.newDirectoryStream(dir);

        int counter = 0;
        for (Path p : ds) {
            if (Files.exists(p)) {
                counter++;
            } else {
                throw new IllegalStateException("Path was loaded from dir but the file doesn't exist!");
            }
        }

        return counter == 0;
    }
}