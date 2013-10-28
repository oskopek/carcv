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

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

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

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        inDir = Files.createTempDirectory("inDir");
        outDir = Files.createTempDirectory("outDir");

        recognizer = new FileCarRecognizer(inDir, outDir);
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }

    /**
     * Test method for {@link org.carcv.impl.core.recognize.FileCarRecognizer#recognize()}.
     * 
     * @throws IOException
     */
    @Test
    public void testRecognize() throws IOException {
        assertNotNull(recognizer);
        assertTrue(isDirEmpty(inDir));
        assertTrue(isDirEmpty(outDir));

        recognizer.recognize();

        assertTrue(isDirEmpty(inDir));
        assertTrue(isDirEmpty(outDir));

        // TODO 1 Finish testRecognize() in FileCarRecognizerTest

    }

    private boolean isDirEmpty(Path dir) throws IOException {
        DirectoryStream<Path> ds = Files.newDirectoryStream(dir);

        int counter = 0;
        for (Path p : ds) {
            if (Files.exists(p)) {
                counter++;
            } else {
                throw new IllegalStateException("Path was loaded from dir but the file doesn't exist!");
            }
        }

        if (counter == 0) {
            return true;
        }

        return false;
    }

}