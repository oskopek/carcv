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

import org.carcv.core.model.AbstractEntry;
import org.carcv.core.model.file.FileEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides methods for discovery of new files recursively under a root directory.
 */
public class DirectoryWatcher implements Loader {

    private static final Logger LOGGER = LoggerFactory.getLogger(DirectoryWatcher.class);

    private static final String completedMarkerFilename = "completed";

    private Path rootDir;

    private List<Path> knownDirs;

    private List<FileEntry> entries;

    private Integer lastToIndex;

    /**
     * A constructor for DirectoryWatcher that sets a root directory.
     *
     * @param rootDir the Path from which to recursively discover new files
     */
    public DirectoryWatcher(Path rootDir) {
        knownDirs = new ArrayList<>();
        entries = new ArrayList<>();

        this.rootDir = rootDir;
        this.lastToIndex = 0;
    }

    /**
     * Deletes given path (directory) and everything under it recursively.
     * <p/>
     * Similar to <code>rm -rf path</code>
     * <p/>
     * <strong>USE WITH CAUTION!</strong>
     *
     * @param path the path to delete
     * @throws IOException if an IO error occurs in one of the visitors, see {@link Files#walkFileTree(Path,
     * FileVisitor)}
     */
    public static void deleteDirectory(Path path) throws IOException {
        Files.walkFileTree(path, new FileVisitor<Path>() {

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                LOGGER.debug("Deleting directory: {}", dir);
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                LOGGER.debug("Deleting file: {}", file);
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                LOGGER.debug(exc.getMessage());
                return FileVisitResult.CONTINUE;
            }
        });
    }

    /**
     * Checks if directory is empty.
     *
     * @param dir a Path that {@link Files#isDirectory(Path, LinkOption...)}
     * @return true if directory contains no files/directories
     * @throws IOException if an IO error occurs, see {@link Files#newDirectoryStream(Path)}
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

    /**
     * Discovers new batches in the root directory and adds them to the List of Entries. Runs {@link
     * DirectoryLoader#load(Path)}
     * on every newly discovered file.
     *
     * @throws IOException If an error creating a DirectoryStream from rootDir occurs. Doesn't throw if an invalid
     * directory
     * happens to be in rootDir, just ignores it (adds it to knownDirs) and continues
     */
    public void discover() throws IOException {
        DirectoryStream<Path> stream;
        try {
            stream = Files.newDirectoryStream(rootDir);
        } catch (IOException ioe) {
            throw ioe;
        }

        Path completed = null;
        for (Path p : stream) {
            completed = Paths.get(p.toString(), completedMarkerFilename);

            if (!Files.isDirectory(p) || knownDirs.contains(p) || Files.exists(completed)) {
                continue;
            }

            knownDirs.add(p); // adds to known directories
            Files.createFile(completed); // adds a marker file for server restarts, etc..

            try {
                FileEntry e = DirectoryLoader.load(p);
                entries.add(e);
            } catch (IOException ioe) {
                LOGGER.error("Loading FileEntry batch directory failed, discarding it: {} with message: {}", p,
                        ioe.getMessage());
            }
        }
    }

    /**
     * A check if List of Entries has at least one new Entry.
     *
     * @return true if the Entries List contains at least one new Entry
     */
    public boolean hasNewEntries() {
        return lastToIndex < entries.size();
    }

    /**
     * Gets all Entries from the List that haven't yet been returned.
     *
     * @return the list of entries since last call of this method
     */
    public List<FileEntry> getNewEntries() {
        if (!hasNewEntries()) {
            return new ArrayList<>();
        }

        List<FileEntry> newEntries = entries.subList(lastToIndex, entries.size());
        lastToIndex = entries.size();
        return newEntries;
    }

    /**
     * @return the list of entries
     */
    public List<FileEntry> getEntries() {
        return entries;
    }

    /**
     * Calls {@link #getEntries()}.
     */
    @Override
    public List<? extends AbstractEntry> loadAll() throws IOException {
        return getEntries();
    }

    /**
     * Calls {@link #getNewEntries()}.
     */
    @Override
    public List<? extends AbstractEntry> loadNew() throws IOException {
        return getNewEntries();
    }
}
