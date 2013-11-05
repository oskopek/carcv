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

package org.carcv.impl.core.input;

import static org.junit.Assert.*;

import java.awt.Rectangle;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.carcv.core.model.file.FileCarImage;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 */
public class FileCarImageTest {

    private Path filepath;

    private FileCarImage file;

    /**
     * @throws URISyntaxException
     */
    @Before
    public void setUp() throws URISyntaxException {
        filepath = Paths.get(getClass().getResource("/img/skoda_oct.jpg").toURI());

        file = new FileCarImage(filepath);
        assertNotNull(file);
        assertNull(file.getImage());
    }

    /**
     *
     */
    @After
    public void tearDown() {
        filepath = null;
        file.close();
    }

    /**
     * Test method for {@link org.carcv.core.model.file.FileCarImage#FileCarImage(java.nio.file.Path)}
     * 
     * @throws IOException
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
        assertNull(ex);

        try {
            iFile.loadImage();
        } catch (final Exception e) {
            // this should actually occur here:
            ex = e;
            // System.err.println(e.getMessage() + " - this is expected");
        }
        assertNotNull(ex);

        assertNotNull(iFile);
        assertNull(iFile.getImage());
        assertTrue(Files.deleteIfExists(tmpFile));
    }

    /**
     * Test method for {@link org.carcv.core.model.file.FileCarImage#loadImage()}.
     * 
     * @throws IOException
     */
    @Test
    public void testLoadImage() throws IOException {
        file.loadImage();
        assertNotNull(file);
        assertNotNull(file.getImage());
    }

    /**
     * Test method for {@link org.carcv.core.model.file.FileCarImage#loadFragment(java.awt.Rectangle)}.
     * 
     * @throws IOException
     */
    @Test
    @Ignore(value = "Fragment loading not supported yet")
    public void testLoadFragment() throws IOException {
        Rectangle rect = new Rectangle(10, 10, 10, 10);
        assertNotNull(rect);

        file.loadFragment(rect);
        assertNotNull(file);
        assertNotNull(file.getImage());
    }

    /**
     * Test method for {@link org.carcv.core.model.file.FileCarImage#close()}.
     * 
     * @throws IOException
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
        assertNotNull(iFile.getPath());
        assertNull(iFile.getImage());

        assertTrue(Files.deleteIfExists(tmpFile));
    }

    /**
     * Test method for {@link org.carcv.core.model.file.FileCarImage#close()}.
     * 
     * @throws IOException
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
        assertNull(ex);

        try {
            iFile.loadImage();
        } catch (final Exception e) {
            // this should actually occur:
            ex = e;
            // System.err.println(e.getMessage() + " - this is expected");

        }
        assertNotNull(ex);

        assertNotNull(iFile);
        assertNull(iFile.getImage());

        iFile.close();
        assertNotNull(iFile);
        assertNotNull(iFile.getPath());
        assertNull(iFile.getImage());

        assertTrue(Files.deleteIfExists(tmpFile));
    }

    /**
     * Test method for {@link org.carcv.core.model.file.FileCarImage#close()}.
     * 
     * @throws IOException
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
        assertNotNull(iFile.getPath());

        // BufferedImage closed = iFile.getBufImage();

        // TODO 3 How to test if BufferedImage is really flushed?
    }

    @Test
    public void testPath() throws IOException {
        FileCarImage iFile = new FileCarImage(filepath);
        assertNotNull(iFile);

        assertNotNull(iFile.getPath());
        assertEquals(filepath, iFile.getPath());

        iFile.setPath(Files.createTempFile("test", ".carcv.jpg"));
        assertNotEquals(iFile.getPath(), filepath);

        assertTrue(Files.deleteIfExists(iFile.getPath()));
    }

}