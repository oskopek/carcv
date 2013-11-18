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

package org.carcv.impl.core.detect;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.carcv.core.io.DirectoryWatcher;
import org.carcv.core.model.file.FileCarImage;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 */
public class NumberPlateDetectorImplTest {

    private FileCarImage image;

    private NumberPlateDetectorImpl detector;

    @Before
    public void setUp() throws IOException, URISyntaxException {
        Path tmpDir = Files.createTempDirectory("NumberPlateDetectorImplTest");
        assertTrue(Files.exists(tmpDir) && Files.isDirectory(tmpDir));

        Path tmpImage = Paths.get(tmpDir.toString(), "testImage.jpg");
        assertFalse(Files.exists(tmpImage) && Files.isRegularFile(tmpImage));

        Files.copy(getClass().getResourceAsStream("/img/skoda_oct.jpg"), tmpImage);
        assertTrue(Files.exists(tmpImage) && Files.isRegularFile(tmpImage));

        image = new FileCarImage(tmpImage);
        assertNotNull(image);
        assertNull(image.getImage());

        image.loadImage();
        assertNotNull(image.getImage());

        detector = NumberPlateDetectorImpl.getInstance();
        assertNotNull(detector);
    }

    @After
    public void tearDown() throws IOException {
        image.close();
        DirectoryWatcher.deleteDirectory(image.getFilepath().getParent());
    }

    /**
     * Test method for {@link org.carcv.impl.core.detect.NumberPlateDetectorImpl#detect(java.util.List)} .
     */
    @Test
    public void testDetect() {
        String np = detector.detect(Arrays.asList(image));
        assertEquals("2SU358F", np); // actually should be 2SU3588
    }

    /**
     * Test method for {@link org.carcv.impl.core.detect.NumberPlateDetectorImpl#detectPlateText(java.util.List)} .
     */
    @Test
    public void testDetectPlateText() {

        String np = detector.detectPlateText((Arrays.asList(image)));
        assertEquals("2SU358F", np); // actually should be 2SU3588
    }

    /**
     * Test method for {@link org.carcv.impl.core.detect.NumberPlateDetectorImpl#detectPlateOrigin(java.util.List)} .
     */
    @Test
    @Ignore(value = "Not yet implemented")
    public void testDetectPlateOrigin() {
        fail("Not yet implemented");
    }

}