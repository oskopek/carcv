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

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import org.carcv.core.model.Address;
import org.carcv.core.model.CarData;
import org.carcv.core.model.NumberPlate;
import org.carcv.core.model.Speed;
import org.carcv.core.model.file.FileCarImage;
import org.carcv.core.model.file.FileEntry;

/**
 * Generates a random FileEntry with existing images. To be used for testing purposes.
 */
public class FileEntryTool {

    private Random r;

    public FileEntryTool() {
        r = new Random();
    }

    /**
     * The caller is responsible for disposing the object, including deletion of FileCarImages on disk. The images are not
     * loaded.
     *
     * @param resources list of paths to resources
     * @return a randomly generated FileEntry with 2 images
     * @throws IOException
     */
    public FileEntry generate(String[] resources) throws IOException {

        // CarData
        Address add = new Address(Double.valueOf(r.nextDouble() * 100), Double.valueOf(r.nextDouble() * 100), randomString(5),
            randomString(5), randomString(10), randomString(10), randomInteger(3), randomInteger(5));
        Speed sp = new Speed(r.nextDouble() * 100);
        NumberPlate np = new NumberPlate(randomString(5).toUpperCase(), randomString(2).toUpperCase());
        CarData cd = new CarData(sp, add, np, new Date(r.nextLong()));

        // CarImages
        ArrayList<FileCarImage> images = new ArrayList<>();
        
        for(String s : resources) { // TODO 1 abstract this
            InputStream is = getClass().getResourceAsStream(s);
            Path path = Paths.get("/tmp", is.hashCode() + ".jpg");
            Files.copy(is, path);
            FileCarImage f = new FileCarImage(path);
            images.add(f);
        }
        
        // FileEntry

        return new FileEntry(cd, images);
    }

    private Integer randomInteger(int length) {
        String s = "";
        for (int i = 0; i < length; i++) {
            s += r.nextInt(10);
        }
        return Integer.parseInt(s);
    }

    private String randomString(int length) {
        String s = "";
        for (int i = 0; i < length; i++) {
            s += nextLetter();
        }
        return s;
    }

    /**
     * A brute-force Random.nextLetter() method.
     *
     * @return a char that {@link Character#isLetter(char)}
     */
    private char nextLetter() {
        /*
         * char c = '.';
         *
         * while(!Character.isLetter(c)) { c = (char) r.nextInt(); } return c;
         */

        char c = (char) (r.nextInt(25) + 65);
        assert Character.isLetter(c);
        return r.nextBoolean() ? Character.toUpperCase(c) : c;
    }
}