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
package org.carcv.impl.core.detect;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.carcv.core.input.DirectoryWatcher;
import org.carcv.core.model.file.FileCarImage;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 */
public class CarSorterImplTest {
    
    private Path imagePath;

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
        Path p = Files.createTempDirectory("CarSorterImplTest"); 
        
        imagePath = Paths.get(p.toString(), "testImage" + System.currentTimeMillis() +".jpg");
        
        Files.copy(getClass().getResourceAsStream("/img/skoda_oct.jpg"), imagePath);
        
        assertFalse(DirectoryWatcher.isDirEmpty(imagePath.getParent()));
        assertTrue(Files.exists(imagePath));
        
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
        DirectoryWatcher.deleteDirectory(imagePath.getParent());
    }

    /**
     * Test method for {@link org.carcv.impl.core.detect.CarSorterImpl#sortIntoCars(org.carcv.core.model.file.FileEntry)}.
     */
    @Test
    @Ignore
    public void testSortIntoCars() { // TODO 1 Finish test SortIntoCars
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link org.carcv.impl.core.detect.CarSorterImpl#carsEquals(org.carcv.core.model.file.FileCarImage, org.carcv.core.model.file.FileCarImage)}.
     * @throws IOException 
     */
    @Test
    public void testCarsEqualsFileCarImageFileCarImage() throws IOException {
        Path p = imagePath.getParent();
        
        Path imagePath2 = Paths.get(p.toString(), "testImage2" + System.currentTimeMillis() +".jpg");
        
        Files.copy(getClass().getResourceAsStream("/img/skoda_oct.jpg"), imagePath2);
        
        assertFalse(DirectoryWatcher.isDirEmpty(imagePath2.getParent()));
        assertTrue(Files.exists(imagePath2));
        
        FileCarImage img1 = new FileCarImage(imagePath);
        FileCarImage img2 = new FileCarImage(imagePath2);
        
        img1.loadImage();
        img2.loadImage();
        
        assertTrue(CarSorterImpl.getInstance().carsEquals(img1, img2));
        
        img1.close();
        img2.close();
    }

    /**
     * Test method for {@link org.carcv.impl.core.detect.CarSorterImpl#carsEquals(org.carcv.core.model.file.FileCarImage, java.lang.String)}.
     * @throws IOException 
     */
    @Test
    public void testCarsEqualsFileCarImageString() throws IOException {
        String real = "2SU3588";
        FileCarImage image = new FileCarImage(imagePath);
        
        image.loadImage();
        
        assertTrue(CarSorterImpl.getInstance().carsEquals(image, real));
        
        image.close();
    }

    /**
     * Test method for {@link org.carcv.impl.core.detect.CarSorterImpl#carsEquals(java.lang.String, java.lang.String)}.
     */
    @Test
    public void testCarsEqualsStringString() {
        String real = "2SU3588";
        String detected = "2SU358F";
        assertTrue(CarSorterImpl.getInstance().carsEquals(real, detected));
    }

}
