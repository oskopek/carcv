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

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Arrays;

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
        image = new FileCarImage(Paths.get(getClass().getResource("/img/skoda_oct.jpg").toURI()));
        assertNotNull(image);

        image.loadImage();
        assertNotNull(image.getImage());

        detector = new NumberPlateDetectorImpl();
        assertNotNull(detector);
    }

    @After
    public void tearDown() {
        image.close();
    }

    /**
     * Test method for {@link org.carcv.impl.core.detect.NumberPlateDetectorImpl#detect(org.carcv.core.model.AbstractCarImage)}
     * .
     */
    @Test
    public void testDetect() {
        String np = detector.detect(Arrays.asList(image));
        assertEquals("2SU358F", np); // actually should be 2SU3588
    }

    /**
     * Test method for
     * {@link org.carcv.impl.core.detect.NumberPlateDetectorImpl#detectPlateText(org.carcv.core.model.file.FileCarImage)} .
     */
    @Test
    public void testDetectPlateText() {

        String np = detector.detectPlateText((Arrays.asList(image)));
        assertEquals("2SU358F", np); // actually should be 2SU3588
    }

    /**
     * Test method for
     * {@link org.carcv.impl.core.detect.NumberPlateDetectorImpl#detectPlateOrigin(org.carcv.core.model.file.FileCarImage)} .
     */
    @Test
    @Ignore(value = "Not yet implemented")
    public void testDetectPlateOrigin() {
        fail("Not yet implemented");
    }

}