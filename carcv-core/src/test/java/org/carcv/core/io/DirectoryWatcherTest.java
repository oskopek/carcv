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

package org.carcv.core.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.carcv.core.io.DirectoryWatcher;
import org.carcv.core.model.file.FileCarImage;
import org.carcv.core.model.file.FileEntry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test for {@link DirectoryWatcher}.
 */
public class DirectoryWatcherTest {

    private Path rootPath;

    private DirectoryWatcher watcher;

    private Properties properties;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        rootPath = Files.createTempDirectory("directoryWatcherTestDir");

        watcher = new DirectoryWatcher(rootPath);

        // properties
        properties = new Properties();

        properties.setProperty("address-lat", Double.toString(48.5));
        properties.setProperty("address-long", Double.toString(17.8));
        properties.setProperty("address-city", "Bratislava");
        properties.setProperty("address-postalCode", "93221");
        properties.setProperty("address-street", "Hrušková");
        properties.setProperty("address-country", "Slovakia");
        properties.setProperty("address-streetNo", "32");
        properties.setProperty("address-refNo", "1010");
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
        DirectoryWatcher.deleteDirectory(rootPath);
        assertFalse(Files.exists(rootPath));
        assertFalse(Files.isDirectory(rootPath));
        // */
    }

    /**
     * Test method for {@link org.carcv.core.io.DirectoryWatcher#discover()}.
     *
     * @throws IOException
     */
    @Test
    public void testDiscover() throws IOException {
        assertNotNull(watcher);

        // 1
        Path newDir1 = Files.createTempDirectory(rootPath, "testDir");
        Path newFile1 = Files.createTempFile(newDir1, "testFileDirWatching", ".carcv.jpg");
        Path newPermissions1 = Paths.get(newDir1.toString(), "info.properties");
        properties.store(new FileOutputStream(newPermissions1.toFile()), "");
        assertNotNull(newFile1);

        watcher.discover();

        List<FileEntry> list1 = watcher.getNewEntries();
        assertEquals(1, list1.size());
        assertEquals(0, watcher.getNewEntries().size());
        assertFalse(watcher.hasNewEntries());

        FileEntry newEntry1 = list1.get(0);
        assertNotNull(newEntry1);
        assertNotNull(newEntry1.getCarData());
        FileCarImage newImage1 = newEntry1.getCarImages().get(0);
        assertNotNull(newImage1);
        assertNotNull(newImage1.getFilepath());
        assertNull(newImage1.getImage());

        // 2
        Path newDir2 = Files.createTempDirectory(rootPath, "testDir");
        Path newFile2 = Files.createTempFile(newDir2, "testFileDirWatching", ".carcv.jpg");
        Path newPermissions2 = Paths.get(newDir2.toString(), "info.properties");
        properties.store(new FileOutputStream(newPermissions2.toFile()), "");
        assertNotNull(newFile2);

        watcher.discover();

        List<FileEntry> list2 = watcher.getNewEntries();
        assertEquals(1, list2.size());
        assertEquals(0, watcher.getNewEntries().size());
        assertFalse(watcher.hasNewEntries());

        FileEntry newEntry2 = list2.get(0);
        assertNotNull(newEntry2);
        assertNotNull(newEntry2.getCarData());
        FileCarImage newImage2 = newEntry2.getCarImages().get(0);
        assertNotNull(newImage2);
        assertNotNull(newImage2.getFilepath());
        assertNull(newImage2.getImage());

        assertFalse(watcher.hasNewEntries());

        assertNotEquals(newEntry1, newEntry2);
        assertEquals(newEntry1.getCarData(), newEntry2.getCarData());
        assertNotEquals(newImage1, newImage2);
        assertNotEquals(newImage1.getFilepath(), newImage2.getFilepath());
        assertNotEquals(newFile1, newFile2);
        assertFalse(watcher.hasNewEntries());

        // 3
        watcher.discover();
        assertFalse(watcher.hasNewEntries());
    }
}