/*
 * Copyright [yyyy] [name of copyright owner]
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
package org.carcv.impl.core.output;

import static org.junit.Assert.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.carcv.core.input.DirectoryWatcher;
import org.carcv.core.model.AbstractEntry;
import org.carcv.core.model.Address;
import org.carcv.core.model.CarData;
import org.carcv.core.model.NumberPlate;
import org.carcv.core.model.Speed;
import org.carcv.core.model.file.FileCarImage;
import org.carcv.core.model.file.FileEntry;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 */
public class FileSaveBatchTest { // TODO 1 Test FileSaveBatch

    private Path inDir;

    private Path outDir;

    private List<FileEntry> batch;

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {

        inDir = Files.createTempDirectory("inDir");
        outDir = Files.createTempDirectory("outDir");

        batch = new ArrayList<>();

        for (int i = 0; i < 10; i++) {

            Path dir = Files.createTempDirectory(Paths.get(inDir.toString()), "batch" + i);

            CarData cd = new CarData(new Speed(0d), new Address("Bratislava", "93221", "Hruskova", "Slovakia", 32),
                new NumberPlate(""), new Date(System.currentTimeMillis()));

            List<FileCarImage> images = new ArrayList<>();

            for (int j = 0; j < 10; j++) {
                FileCarImage f = new FileCarImage(Files.createTempFile(dir, "testImage", ".carcv.jpg"));
                images.add(f);
            }

            batch.add(new FileEntry(cd, images));
        }
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
    public void castTest() throws Exception {
        // final List<FileEntry> fileBatch = (List<FileEntry>) batch;
        
        final FileEntry e = batch.get(0);
        final AbstractEntry ae = e;
        assertEquals(e, ae);
        
        final FileEntry fe = (FileEntry) ae;
        assertEquals(e, fe);
    }

    /**
     * Test method for {@link org.carcv.impl.core.output.FileSaveBatch#save(java.util.List)}.
     */
    @Test
    @Ignore
    public void testSave() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link org.carcv.impl.core.output.FileSaveBatch#saveFileBatch(java.util.List)}.
     */
    @Test
    @Ignore
    public void testSaveFileBatch() {
        fail("Not yet implemented");
    }

}
