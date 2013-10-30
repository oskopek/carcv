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
import org.junit.Ignore;
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

        //properties
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
        // /*
        DirectoryWatcher.deleteDirectory(inDir);
        assertFalse(Files.exists(inDir));
        assertFalse(Files.isDirectory(inDir));

        DirectoryWatcher.deleteDirectory(outDir);
        assertFalse(Files.exists(outDir));
        assertFalse(Files.isDirectory(outDir));
        // */
    }

    /**
     * Test method for {@link org.carcv.impl.core.recognize.FileCarRecognizer#recognize()}.
     * 
     * @throws IOException
     */
    @Test
    @Ignore
    public void testRecognize() throws IOException {
        assertNotNull(recognizer);
        assertTrue(DirectoryWatcher.isDirEmpty(inDir));
        assertTrue(DirectoryWatcher.isDirEmpty(outDir));

        recognizer.recognize();

        assertTrue(DirectoryWatcher.isDirEmpty(inDir));
        assertTrue(DirectoryWatcher.isDirEmpty(outDir));

        // Add a new batch
        Path dir1 = Files.createTempDirectory(inDir, "testDir");
        // TODO 1 Test a real image (skoda_oct.jpg) in FileCarRecognizerTest
        Path file1 = Files.createTempFile(dir1, "testFileDirWatching", ".carcv.jpg");
        Path permissions1 = Paths.get(dir1.toString(), "info.properties");
        properties.store(new FileOutputStream(permissions1.toFile()), "");
        assertNotNull(file1);

        assertFalse(DirectoryWatcher.isDirEmpty(inDir));
        assertTrue(DirectoryWatcher.isDirEmpty(outDir));

        recognizer.recognize();

        assertFalse(DirectoryWatcher.isDirEmpty(inDir));
        assertFalse(DirectoryWatcher.isDirEmpty(outDir));
    }
}