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
package org.carcv.impl.core.detect;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;

import org.carcv.core.input.DirectoryWatcher;
import org.carcv.core.model.Address;
import org.carcv.core.model.CarData;
import org.carcv.core.model.Speed;
import org.carcv.core.model.file.FileCarImage;
import org.carcv.core.model.file.FileEntry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 *
 */
public class CarSorterImplTest {
    
    private Path imagePath, imagePath2;

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
        
        imagePath2 = Paths.get(p.toString(), "testImage2" + System.currentTimeMillis() +".jpg");
        
        Files.copy(getClass().getResourceAsStream("/img/skoda_oct.jpg"), imagePath2);
        
        assertFalse(DirectoryWatcher.isDirEmpty(imagePath2.getParent()));
        assertTrue(Files.exists(imagePath2));
        
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
     * @throws IOException 
     */
    @Test
    public void testSortIntoCars() throws IOException {
        //Add a third image, that isn't of the same car as the two before
        Path imagePath3 = Paths.get(imagePath.getParent().toString(), "testImage3" + System.currentTimeMillis() +".jpg");
        
        Files.copy(getClass().getResourceAsStream("/img/test_041.jpg"), imagePath3);
        
        assertFalse(DirectoryWatcher.isDirEmpty(imagePath3.getParent()));
        assertTrue(Files.exists(imagePath3));
        
        FileCarImage fci1 = new FileCarImage(imagePath);
        FileCarImage fci2 = new FileCarImage(imagePath2);
        FileCarImage fci3 = new FileCarImage(imagePath3);
        
        ArrayList<FileCarImage> images = new ArrayList<>(3);
        images.add(fci1);
        images.add(fci2);
        images.add(fci3);
        
        assertEquals(3, images.size());
        
        CarData carData = new CarData(new Speed(20.1), 
                                new Address("Bratislava", "92231", "Hrušková", "Slovakia", 32), 
                                null, 
                                new Date(System.currentTimeMillis()));        
        FileEntry batch = new FileEntry(carData, images);
        
        assertEquals(3, batch.getCarImages().size());
        assertNotNull(batch.getCarData());
        
        ArrayList<FileEntry> result = (ArrayList<FileEntry>) CarSorterImpl.getInstance().sortIntoCars(batch);
        
        assertEquals(2, result.size());
        
        int counter = 0;
        for(FileEntry f : result) {
            counter += f.getCarImages().size();
        }
        assertEquals(3, batch.getCarImages().size());
        assertEquals(batch.getCarImages().size(), counter);
        
        assertEquals(2, result.get(0).getCarImages().size());
        assertEquals(imagePath, result.get(0).getCarImages().get(0).getPath());
        assertEquals(imagePath2, result.get(0).getCarImages().get(1).getPath());
        
        
        assertEquals(1, result.get(1).getCarImages().size());
        assertEquals(imagePath3, result.get(1).getCarImages().get(0).getPath());
        
        fci1.close();
        fci2.close();
        fci3.close();        
    }

    /**
     * Test method for {@link org.carcv.impl.core.detect.CarSorterImpl#carsEquals(org.carcv.core.model.file.FileCarImage, org.carcv.core.model.file.FileCarImage)}.
     * @throws IOException 
     */
    @Test
    public void testCarsEqualsFileCarImageFileCarImage() throws IOException {
        
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
