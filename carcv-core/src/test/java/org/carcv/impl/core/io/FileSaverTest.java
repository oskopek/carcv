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
package org.carcv.impl.core.io;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import org.carcv.core.io.DirectoryWatcher;
import org.carcv.core.model.AbstractEntry;
import org.carcv.core.model.Address;
import org.carcv.core.model.CarData;
import org.carcv.core.model.NumberPlate;
import org.carcv.core.model.Speed;
import org.carcv.core.model.file.FileCarImage;
import org.carcv.core.model.file.FileEntry;
import org.carcv.impl.core.io.FileSaver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 *
 */
public class FileSaverTest {

    private Path inDir;

    private Path outDir;

    private List<FileEntry> batch;

    private FileSaver saver;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {

        Random r = new Random();

        inDir = Files.createTempDirectory("inDir");
        outDir = Files.createTempDirectory("outDir");

        batch = new ArrayList<>();

        Path dir = Files.createTempDirectory(Paths.get(inDir.toString()), "testBatch");

        for (int i = 0; i < 10; i++) {

            CarData cd =
                new CarData(new Speed(r.nextDouble() * 50), new Address("Bratislava", "93221", "Hrušková", "Slovakia", 32),
                    new NumberPlate("SK" + r.nextInt(900) + 100 + "AA"), new Date(System.currentTimeMillis()));

            List<FileCarImage> images = new ArrayList<>();

            for (int j = 0; j < 10; j++) {
                FileCarImage f = new FileCarImage(Files.createTempFile(dir, "testImage-", ".carcv.jpg"));
                images.add(f);
            }

            batch.add(new FileEntry(cd, images));

        }

        saver = new FileSaver(outDir);
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

    @Test
    public void castTest() {
        final ArrayList<FileEntry> fileBatch = (ArrayList<FileEntry>) batch;
        assertEquals(batch.size(), fileBatch.size());
        for (int i = 0; i < batch.size(); i++) {
            assertEquals(batch.get(i), fileBatch.get(i));
        }

        final FileEntry e = batch.get(0);
        final AbstractEntry ae = e;
        assertEquals(e, ae);

        final FileEntry fe = (FileEntry) ae;
        assertEquals(e, fe);
    }

    /**
     * Test method for {@link org.carcv.impl.core.io.FileSaver#saveFileBatch(java.util.List)}.
     *
     * @throws IOException
     */
    @Test
    public void testSaveFileBatch() throws IOException {
        assertFalse(DirectoryWatcher.isDirEmpty(inDir));
        assertTrue(DirectoryWatcher.isDirEmpty(outDir));

        saver.save(batch);

        assertFalse(DirectoryWatcher.isDirEmpty(inDir));
        assertFalse(DirectoryWatcher.isDirEmpty(outDir));

        DirectoryStream<Path> ds = Files.newDirectoryStream(outDir);
        int counter = 0;
        for (Path p : ds) {
            if (!Files.exists(p) || !Files.isRegularFile(p)) {
                fail("Path " + p + " was supposed to be a properties file, but an error occured while checking.");
            }
            counter++;

            Properties props = new Properties();
            props.load(Files.newInputStream(p));

            final String base = "filepath";
            for (int i = 0; i < 100; i++) {
                if (i >= 10) {
                    assertNull(props.getProperty(base + i));
                } else {
                    assertNotNull(props.getProperty(base + i));
                }
            }
        }
        assertEquals(batch.size(), counter);
    }

}