/*
 * Copyright 2012-2014 CarCV Development Team
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

package org.carcv.core.io;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.carcv.core.model.Address;
import org.carcv.core.model.CarData;
import org.carcv.core.model.file.FileCarImage;
import org.carcv.core.model.file.FileEntry;

/**
 * Provides methods for loading CarData from a directory containing a {@link #infoFileName info file}.
 */
public class DirectoryLoader {

    /**
     * An array of lower case String representations of image file suffixes. Without leading dots.
     */
    final public static String[] knownImageFileSuffixes = { "png", "jpg", "jpeg", "bmp" };

    /**
     * The default name of the info file.
     */
    final public static String infoFileName = "info.properties";

    private DirectoryLoader() {
        // intentionally empty
    }

    /**
     * Loads the FileEntry with all {@link #knownImageFileSuffixes known images} and CarData from the {@value #infoFileName}
     * file in the <code>directory</code>.
     *
     * @param directory Path to the directory to load from
     * @return the loaded FileEntry
     * @throws IOException if an error during loading occurs
     */
    public static FileEntry load(Path directory) throws IOException {
        DirectoryStream<Path> contents = Files.newDirectoryStream(directory);

        List<FileCarImage> images = new ArrayList<>();

        for (Path p : contents) {
            if (Files.exists(p) && Files.isRegularFile(p) && isKnownImage(p)) {
                images.add(new FileCarImage(p));
            }
        }

        CarData cd = loadCarData(directory);
        return new FileEntry(cd, images);
    }

    /**
     * Loads the CarData from the {@value #infoFileName} in <code>directory</code>.
     *
     * @param directory from which to load CarData
     * @return a new CarData object from the directory properties
     * @throws IOException if an error during loading occurs
     */
    private static CarData loadCarData(Path directory) throws IOException {
        Properties p = new Properties();

        Path info = Paths.get(directory.toString(), infoFileName);

        if (info == null || !Files.exists(info)) {
            throw new IOException("Failed to load CarData from info.properties in the new directory");
        }

        p.load(new FileInputStream(info.toFile()));

        String latitude = p.getProperty("address-lat");
        String longitude = p.getProperty("address-long");
        String city = p.getProperty("address-city");
        String postalcode = p.getProperty("address-postalCode");
        String street = p.getProperty("address-street");
        String country = p.getProperty("address-country");
        String streetNumber = p.getProperty("address-streetNo");
        String referenceNumber = p.getProperty("address-refNo");

        String time = p.getProperty("timestamp");

        Address address = new Address(Double.parseDouble(latitude), Double.parseDouble(longitude), city, postalcode, street,
            country, Integer.parseInt(streetNumber), Integer.parseInt(referenceNumber));

        Date timestamp = new Date(Long.parseLong(time));

        return new CarData(null, address, null, timestamp);
    }

    /**
     * Checks the file if it is an image with known image file format from {@link #knownImageFileSuffixes}.
     *
     * @param p Path to check
     * @return true if the file at Path p is of a known image file format
     */
    private static boolean isKnownImage(Path p) {
        for (String suffix : knownImageFileSuffixes) {
            if (p.toString().endsWith("." + suffix.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
}