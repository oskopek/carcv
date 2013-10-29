/*
 * Copyright 2013 CarCV Development Team
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

package org.carcv.core.impl.input;

import static org.junit.Assert.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Date;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import org.carcv.core.input.DirectoryWatcher;
import org.carcv.core.model.file.FileCarImage;
import org.carcv.core.model.file.FileEntry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 *
 */
public class DirectoryWatcherTest { // TODO 1 Make into DirectoryWatcher and Loader test

    private Path rootPath;

    private DirectoryWatcher watcher;

    private FileAttribute<Set<PosixFilePermission>> permissions;
    
    private Properties properties;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        rootPath = Files.createTempDirectory("directoryWatcherTestDir");

        watcher = new DirectoryWatcher(rootPath);

        TreeSet<PosixFilePermission> permissions = new TreeSet<>();
        PosixFilePermission[] perms = PosixFilePermission.values();

        for (PosixFilePermission p : perms) {
            permissions.add(p);
        }

        this.permissions = PosixFilePermissions.asFileAttribute(permissions);
        
        //properties
        properties = new Properties();
        
        properties.setProperty("address-lat", new Double(48.5).toString());
        properties.setProperty("address-long", new Double(17.8).toString());
        properties.setProperty("address-city", "Myjava");
        properties.setProperty("address-postalCode", "90701");
        properties.setProperty("address-street", "Jablonska");
        properties.setProperty("address-country", "Slovakia");
        properties.setProperty("address-streetNo", "27");
        properties.setProperty("address-refNo", "860");        
        properties.setProperty("timestamp", String.valueOf(new Date(System.currentTimeMillis()).getTime()));
    }

    /**
     * Deletes the temp directory
     * 
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
        // /*
        deleteDirectory(rootPath);
        assertFalse(Files.exists(rootPath));
        assertFalse(Files.isDirectory(rootPath));
        // */
    }

    /**
     * Test method for {@link org.carcv.impl.core.input.FileDiscoverer#discover()}.
     * 
     * @throws IOException
     */

    @Test
    public void testFileDiscovery() throws IOException {
        assertNotNull(watcher);

        //1
        Path newDir1 = Files.createTempDirectory(rootPath, "testDir", permissions);
        Path newFile1 = Files.createTempFile(newDir1, "testFileDirWatching", ".carcv.jpg", permissions);
        Path newPermissions1 = Paths.get(newDir1.toString(), "info.properties");
        properties.store(new FileOutputStream(newPermissions1.toFile()), "");
        assertNotNull(newFile1);

        watcher.discover();

        FileEntry newEntry1 = watcher.getNewEntries().get(0);
        FileCarImage newImage1 = newEntry1.getCarImages().get(0);
        assertNotNull(newImage1);
        assertNotNull(newImage1.getPersistablePath());
        assertNull(newImage1.getImage());

        //2
        Path newDir2 = Files.createTempDirectory(rootPath, "testDir", permissions);
        Path newFile2 = Files.createTempFile(newDir2, "testFileDirWatching", ".carcv.jpg", permissions);
        Path newPermissions2 = Paths.get(newDir2.toString(), "info.properties");
        properties.store(new FileOutputStream(newPermissions2.toFile()), "");
        assertNotNull(newFile2);

        watcher.discover();

        FileCarImage newImage2 = watcher.getNewEntries().get(0).getCarImages().get(0);
        assertNotNull(newImage2);
        assertNotNull(newImage2.getPersistablePath());
        assertNull(newImage2.getImage());

        assertNotEquals(newImage1, newImage2);
        assertNotEquals(newImage1.getPersistablePath(), newImage2.getPersistablePath());
        assertNotEquals(newFile1, newFile2);
    }

    private static void deleteDirectory(Path path) throws IOException {
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

}