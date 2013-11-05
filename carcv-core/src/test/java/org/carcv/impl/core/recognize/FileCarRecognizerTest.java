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

package org.carcv.impl.core.recognize;

import static org.junit.Assert.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Properties;

import org.carcv.core.input.DirectoryWatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 *
 */
public class FileCarRecognizerTest {

    private Path inDir;

    private Path outDir;

    private FileCarRecognizer recognizer;

    private Properties properties;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        inDir = Files.createTempDirectory("inDir");
        outDir = Files.createTempDirectory("outDir");

        recognizer = new FileCarRecognizer(inDir, outDir);

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
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
        // /* // uncomment this to view results in /tmp - NOTE: pollutes tmp directory!
        DirectoryWatcher.deleteDirectory(inDir);
        assertFalse(Files.exists(inDir));
        assertFalse(Files.isDirectory(inDir));

        DirectoryWatcher.deleteDirectory(outDir);
        assertFalse(Files.exists(outDir));
        assertFalse(Files.isDirectory(outDir));
        // */
    }

    /**
     * Test of recognizing one input image in one batch<BR>
     * Test method for {@link org.carcv.impl.core.recognize.FileCarRecognizer#recognize()}.
     * 
     * @throws IOException
     */
    @Test
    public void testRecognize() throws IOException {
        assertNotNull(recognizer);
        assertTrue(DirectoryWatcher.isDirEmpty(inDir));
        assertTrue(DirectoryWatcher.isDirEmpty(outDir));

        recognizer.recognize();

        assertTrue(DirectoryWatcher.isDirEmpty(inDir));
        assertTrue(DirectoryWatcher.isDirEmpty(outDir));

        // Add a new batch
        Path dir1 = Files.createTempDirectory(inDir, "testDir");
        Path file1 = Paths.get(dir1.toString(), "dirWatchingTestFile-" + System.currentTimeMillis() + ".carcv.jpg");

        Files.copy(getClass().getResourceAsStream("/img/skoda_oct.jpg"), file1); // uses the actual skoda_oct.jpg image

        Path permissions1 = Paths.get(dir1.toString(), "info.properties");
        properties.store(new FileOutputStream(permissions1.toFile()), "");
        assertNotNull(file1);

        assertFalse(DirectoryWatcher.isDirEmpty(inDir));
        assertTrue(DirectoryWatcher.isDirEmpty(outDir));

        recognizer.recognize();

        assertFalse(DirectoryWatcher.isDirEmpty(inDir));
        assertFalse(DirectoryWatcher.isDirEmpty(outDir));

        // TODO 3 Assert that the output is indeed what we expect - for now, checked only manual in /tmp
    }

    /**
     * Test of recognizing multiple (three) input images in one batch
     * 
     * @throws IOException
     */
    @Test
    public void testMultipleRecognize() throws IOException {
        assertNotNull(recognizer);
        assertTrue(DirectoryWatcher.isDirEmpty(inDir));
        assertTrue(DirectoryWatcher.isDirEmpty(outDir));

        // new batch dir
        Path testDir = Files.createTempDirectory(inDir, "testBatchDir");

        // First image
        Path imagePath1 = Paths.get(testDir.toString(), "testImage1-" + System.currentTimeMillis() + ".jpg");

        Files.copy(getClass().getResourceAsStream("/img/skoda_oct.jpg"), imagePath1);

        assertFalse(DirectoryWatcher.isDirEmpty(imagePath1.getParent()));
        assertTrue(Files.exists(imagePath1));

        // Second image equal to first but different path and file
        Path imagePath2 = Paths.get(testDir.toString(), "testImage2-" + System.currentTimeMillis() + ".jpg");

        Files.copy(getClass().getResourceAsStream("/img/skoda_oct.jpg"), imagePath2);

        assertFalse(DirectoryWatcher.isDirEmpty(imagePath2.getParent()));
        assertTrue(Files.exists(imagePath2));

        // Add a third image, that isn't of the same car as the two before
        Path imagePath3 = Paths.get(testDir.toString(), "testImage3-" + System.currentTimeMillis() + ".jpg");

        Files.copy(getClass().getResourceAsStream("/img/test_041.jpg"), imagePath3);

        assertFalse(DirectoryWatcher.isDirEmpty(imagePath3.getParent()));
        assertTrue(Files.exists(imagePath3));

        // Properties
        Path permissions1 = Paths.get(testDir.toString(), "info.properties");
        properties.store(new FileOutputStream(permissions1.toFile()), "");
        assertTrue(Files.exists(permissions1));

        // Discover
        recognizer.recognize();

        assertFalse(DirectoryWatcher.isDirEmpty(inDir));
        assertFalse(DirectoryWatcher.isDirEmpty(outDir));

        // TODO 3 Assert that the output is indeed what we expect - for now, checked only manual in /tmp
    }
}