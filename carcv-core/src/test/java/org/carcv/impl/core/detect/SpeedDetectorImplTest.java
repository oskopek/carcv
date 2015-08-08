/*
 * Copyright 2013-2014 CarCV Development Team
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
package org.carcv.impl.core.detect;

import org.carcv.core.io.DirectoryWatcher;
import org.carcv.core.model.file.FileCarImage;
import org.carcv.impl.core.io.FFMPEGVideoHandler;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Test for {@link SpeedDetectorImpl}.
 */
public class SpeedDetectorImplTest {

    private SpeedDetectorImpl detector;
    private ArrayList<FileCarImage> images;
    private Path tmpDir;

    @Before
    public void setUp() throws Exception {
        detector = SpeedDetectorImpl.getInstance();
        images = new ArrayList<>();
        tmpDir = Files.createTempDirectory("SpeedDetectorImplTestDir");

        for (int j = 0; j < 15; j++) {
            // Would be useful if the SpeedDetector actually looked at the image file
            Path p = Paths.get(tmpDir.toString(), "testImage - " + j + ".carcv.jpg");
            Files.copy(getClass().getResourceAsStream("/img/test_041.jpg"), p);
            assertTrue(Files.exists(p));
            assertTrue(Files.isRegularFile(p));
            FileCarImage f = new FileCarImage(p);
            images.add(f);
            images.add(f);
        }
    }

    @After
    public void tearDown() throws Exception {
        DirectoryWatcher.deleteDirectory(tmpDir);
    }

    /**
     * Test method for {@link SpeedDetectorImpl#detect(java.util.List)}.
     */
    @Test
    public void testDetect() {
        assertEquals(detector.detect(images), detector.detectSpeed(images).toString());
        assertEquals(Double.valueOf(detector.detect(images)), detector.detectSpeed(images));
    }

    /**
     * Test method for {@link SpeedDetectorImpl#detectSpeed(java.util.List)}.
     */
    @Test
    public void testDetectSpeed() {
        Double res = detector.detectSpeed(images);
        assertNotEquals(0d, res);

        Double expected = (images.size() / (double) FFMPEGVideoHandler.defaultFrameRate) * 3.6d * 10;

        assertEquals(res, detector.detectSpeed(images));
        assertEquals(expected, res);
    }
}
