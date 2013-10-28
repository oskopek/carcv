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

package org.carcv.core.impl.input;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.util.Set;

import org.junit.After;
import org.junit.Ignore;

/**
 *
 */
@Ignore
public class DirectoryWatcherTest { // TODO 1 Make into DirectoryWatcher and Loader test

    Path rootPath;
    /*
     * private FileDiscoverer fileDiscoverer;
     * 
     * private FileImageQueue queue;
     */
    @SuppressWarnings("unused")
    private FileAttribute<Set<PosixFilePermission>> permissions;

    /**
     * @throws java.lang.Exception
     */
    /*
     * @Before public void setUp() throws Exception { queue = new FileImageQueue();
     * 
     * rootPath = Files.createTempDirectory("fileDiscovererTestDir");
     * 
     * fileDiscoverer = new FileDiscoverer(rootPath, queue);
     * 
     * TreeSet<PosixFilePermission> permissions = new TreeSet<>(); PosixFilePermission[] perms = PosixFilePermission.values();
     * 
     * for (PosixFilePermission p : perms) { permissions.add(p); }
     * 
     * this.permissions = PosixFilePermissions.asFileAttribute(permissions); }
     */

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
     * Test method for {@link org.carcv.impl.core.input.FileDiscoverer#FileDiscoverer(java.nio.file.Path)}.
     */
    /*
     * @Test public void testFileDiscoverer() { FileDiscoverer d = new FileDiscoverer(rootPath, queue);
     * 
     * assertNotNull(d); assertNotNull(d.getBaseDirectory()); assertEquals(rootPath, d.getBaseDirectory()); }
     */

    /**
     * Test method for {@link org.carcv.impl.core.input.FileDiscoverer#getResourceAsStream(java.lang.String)}.
     */
    /*
     * @SuppressWarnings("deprecation")
     * 
     * @Test public void testGetResourceAsStream() { InputStream is = fileDiscoverer.getResourceAsStream("/img/skoda_oct.jpg");
     * 
     * assertNotNull(is); }
     */

    /**
     * Test method for {@link org.carcv.core.input.FileDiscoverer#getFileDiscoverer()}.
     */
    /*
     * @Test public void testGetFileDiscoverer() { FileDiscoverer test = null;
     * 
     * try { test = FileDiscoverer.getFileDiscoverer(); } catch (IllegalStateException e) { assertNull(test); }
     * 
     * FileDiscoverer.init(rootPath);
     * 
     * test = FileDiscoverer.getFileDiscoverer();
     * 
     * assertNotNull(test.getBaseDirectory()); assertNotNull(FileDiscoverer.getFileDiscoverer()); }
     */

    /**
     * Test method for {@link org.carcv.impl.core.input.FileDiscoverer#discover()}.
     * 
     * @throws IOException
     */
    /*
     * @Test public void testFileDiscovery() throws IOException { assertNotNull(fileDiscoverer);
     * 
     * Path newFile1 = Files.createTempFile(rootPath, "testFileDiscovery", ".carcv.jpg", permissions); assertNotNull(newFile1);
     * 
     * fileDiscoverer.discover();
     * 
     * FileCarImage newPath1 = queue.poll(); assertNotNull(newPath1); assertNotNull(newPath1.getPersistablePath());
     * assertNull(newPath1.getImage());
     * 
     * Path newFile2 = Files.createTempFile(rootPath, "testFileDiscovery", ".carcv.jpg", permissions); assertNotNull(newFile2);
     * 
     * fileDiscoverer.discover();
     * 
     * FileCarImage newPath2 = queue.poll(); assertNotNull(newPath2); assertNotNull(newPath2.getPersistablePath());
     * assertNull(newPath2.getImage());
     * 
     * assertNotEquals(newPath1, newPath2); assertNotEquals(newPath1.getPersistablePath(), newPath2.getPersistablePath());
     * assertNotEquals(newFile1, newFile2); }
     */
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