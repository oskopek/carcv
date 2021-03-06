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

package org.carcv.core.model.file;

import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Test for {@link FileCarImage}.
 */
public class FileCarImageTest {

    private Path filepath;

    private FileCarImage file;

    @Before
    public void setUp() throws URISyntaxException {
        filepath = Paths.get(getClass().getResource("/img/skoda_oct.jpg").toURI());

        file = new FileCarImage(filepath);
        assertNotNull(file);
        assertNull(file.getImage());
    }

    @After
    public void tearDown() {
        filepath = null;
        file.close();
    }

    /**
     * Test method for {@link FileCarImage#FileCarImage(Path)}.
     *
     * @throws IOException if an IOException occurs
     */
    @Test
    public void testFileCarImage() throws IOException {

        Path tmpFile = null;
        try {
            tmpFile = Files.createTempFile("imageFile", ".carcv.jpg");
        } catch (IOException e) {
            fail("Failed to create temp file");
            e.printStackTrace();
        }
        assertNotNull(tmpFile);

        FileCarImage iFile = new FileCarImage(tmpFile);
        assertNotNull(iFile);

        Exception ex = null;
        try {
            iFile.loadImage();
        } catch (final Exception e) {
            // this should actually occur here:
            ex = e;
        }
        assertNotNull(ex);

        assertNotNull(iFile);
        assertNull(iFile.getImage());
        assertTrue(Files.deleteIfExists(tmpFile));
    }

    /**
     * Test method for {@link FileCarImage#loadImage()}.
     *
     * @throws IOException if an IOException occurs
     */
    @Test
    public void testLoadImage() throws IOException {
        file.loadImage();
        assertNotNull(file);
        assertNotNull(file.getImage());
    }

    /**
     * Test method for {@link FileCarImage#loadFragment(Rectangle)}.
     *
     * @throws IOException if an IOException occurs
     */
    @Test
    public void testLoadFragment() throws IOException {
        Rectangle rect = new Rectangle(10, 10, 10, 10);
        assertNotNull(rect);

        file.loadFragment(rect);

        assertNotNull(file);
        assertNotNull(file.getImage());
        assertEquals(10, file.getImage().getHeight());
        assertEquals(10, file.getImage().getWidth());
    }

    /**
     * Test method for {@link FileCarImage#close()}.
     *
     * @throws IOException if an IOException occurs
     */
    @Test
    public void testCloseNonLoaded() throws IOException {
        Path tmpFile = null;
        try {
            tmpFile = Files.createTempFile("imageFile", ".carcv.jpg");
        } catch (IOException e) {
            e.printStackTrace();
            fail("Failed to create temp file");
        }
        assertNotNull(tmpFile);

        FileCarImage iFile = new FileCarImage(tmpFile);
        assertNotNull(iFile);

        iFile.close();
        assertNotNull(iFile);
        assertNotNull(iFile.getFilepath());
        assertNull(iFile.getImage());

        assertTrue(Files.deleteIfExists(tmpFile));
    }

    /**
     * Test method for {@link FileCarImage#close()}.
     *
     * @throws IOException if an IOException occurs
     */
    @Test
    public void testCloseLoadedNonExisting() throws IOException {
        Path tmpFile = null;
        try {
            tmpFile = Files.createTempFile("imageFile", ".carcv.jpg");
        } catch (IOException e) {
            e.printStackTrace();
            fail("Failed to create temp file");
        }
        assertNotNull(tmpFile);

        FileCarImage iFile = new FileCarImage(tmpFile);
        assertNotNull(iFile);

        Exception ex = null;
        try {
            iFile.loadImage();
        } catch (final Exception e) {
            // this should actually occur:
            ex = e;
        }
        assertNotNull(ex);

        assertNotNull(iFile);
        assertNull(iFile.getImage());

        iFile.close();
        assertNotNull(iFile);
        assertNotNull(iFile.getFilepath());
        assertNull(iFile.getImage());

        assertTrue(Files.deleteIfExists(tmpFile));
    }

    /**
     * Test method for {@link FileCarImage#close()}.
     *
     * @throws IOException if an IOException occurs
     */
    @Test
    public void testCloseLoadedExisting() throws IOException {
        FileCarImage iFile = new FileCarImage(filepath);
        assertNotNull(iFile);

        iFile.loadImage();
        assertNotNull(iFile);
        assertNotNull(iFile.getImage());

        // BufferedImage loaded = iFile.getBufImage();

        iFile.close();
        assertNotNull(iFile);
        assertNotNull(iFile.getFilepath());

        // BufferedImage closed = iFile.getBufImage();

        // TODO 3 How to test if BufferedImage is really flushed?
    }

    @Test
    public void testPath() throws IOException {
        FileCarImage iFile = new FileCarImage(filepath);
        assertNotNull(iFile);

        assertNotNull(iFile.getFilepath());
        assertEquals(filepath, iFile.getFilepath());

        iFile.setFilepath(Files.createTempFile("test", ".carcv.jpg"));
        assertNotEquals(iFile.getFilepath(), filepath);

        assertTrue(Files.deleteIfExists(iFile.getFilepath()));
    }
}
