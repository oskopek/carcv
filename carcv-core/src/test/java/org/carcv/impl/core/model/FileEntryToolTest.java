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
package org.carcv.impl.core.model;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import org.carcv.core.model.file.FileCarImage;
import org.carcv.core.model.file.FileEntry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test for {@link FileEntryTool}
 */
public class FileEntryToolTest {

    private FileEntryTool tool;

    private ArrayList<FileEntry> list;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        tool = new FileEntryTool();
        list = new ArrayList<>();
        assertNotNull(tool);
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
        for (FileEntry e : list) {
            for (FileCarImage fci : e.getCarImages()) {
                Files.delete(fci.getFilepath());
            }
        }
    }

    /**
     * Test for {@link FileEntryTool#generate(String[])}
     *
     * @throws IOException
     */
    @Test
    public void testGenerate() throws IOException {
        Random r = new Random();
        for (int i = 0; i < r.nextInt(20) + 10; i++) {
            FileEntry e = tool.generate(Arrays.asList("/img/skoda_oct.jpg", "/img/test_041.jpg").toArray(new String[2]));
            assertFileEntry(e);
            
            list.add(e);
        }
    }

    /**
     * A semi-deep non-null assertion for FileEntry
     *
     * @param e the FileEntry to check
     */
    private void assertFileEntry(FileEntry e) {
        // FileEntry
        assertNotNull(e);
        assertNotNull(e.getCarData());
        assertNotNull(e.getCarImages());
        assertNotEquals(0, e.getCarImages().size());

        // CarData
        assertNotNull(e.getCarData().getAddress());
        assertNotNull(e.getCarData().getNumberPlate());
        assertNotNull(e.getCarData().getSpeed());

        // FileCarImage
        for (FileCarImage f : e.getCarImages()) {
            assertNotNull(f);
            assertNotNull(f.getFilepath());
            assertTrue(Files.exists(f.getFilepath()));
        }
    }
}