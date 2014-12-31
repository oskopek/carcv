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

package org.carcv.impl.core.io;

import org.carcv.core.io.Saver;
import org.carcv.core.model.AbstractEntry;
import org.carcv.core.model.file.FileCarImage;
import org.carcv.core.model.file.FileEntry;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;

/**
 * An implementation of <code>Saver</code> that stores batches into files.
 * <p/>
 * <strong>Note</strong>: This implementation only stores a properties files containing all needed info about cars in the batch.
 * <p/>
 * Actual paths of {@link FileCarImage}s are referenced inside the properties files, as filepath0, filepath1, ...
 */
public class FileSaver implements Saver {

    private Path directory;

    /**
     * Initializes the FileSaver with an output directory path.
     *
     * @param directory path of the directory where all output will be stored
     */
    public FileSaver(Path directory) {
        this.directory = directory;
    }

    /**
     * Wrapper method for {@link #saveFileBatch(List)}.
     *
     * @throws IOException if an error during the save occurs
     * @see #saveFileBatch(List)
     * @see org.carcv.core.io.Saver#save(java.util.List)
     */
    @Override
    public void save(final List<? extends AbstractEntry> batch) throws IOException {
        @SuppressWarnings("unchecked")
        final List<FileEntry> fileBatch = (List<FileEntry>) batch;

        saveFileBatch(fileBatch);
    }

    /**
     * Invokes {@link #saveFileEntry(FileEntry)} for all {@link FileEntry}-s in the batch.
     *
     * @param fileBatch the batch to save
     * @throws IOException if an error during the save occurs
     * @see #saveFileEntry(FileEntry)
     */
    public void saveFileBatch(final List<FileEntry> fileBatch) throws IOException {
        for (FileEntry entry : fileBatch) {
            saveFileEntry(entry);
        }
    }

    /**
     * Saves the {@link FileEntry} into a properties file into the output directory.
     * <p/>
     * The name of the output file is <code>e.hashCode() + "-" + System.currentTimeMillis()</code>
     * <p/>
     * List of properties:
     * <ul>
     * <li>numberplate-origin
     * <li>numberplate-text
     * <li>speed-value
     * <li>speed-unit
     * <li>address-city
     * <li>address-street
     * <li>address-streetNo
     * <li>address-country
     * <li>address-refNo
     * <li>address-postalCode
     * <li>address-lat
     * <li>address-long
     * <li>timestamp
     * <li>filepath0 -> filepathN
     * </ul>
     *
     * @param e the FileEntry to save
     * @throws IOException if an error during the save occurs
     */
    public void saveFileEntry(FileEntry e) throws IOException {
        Properties p = new Properties();

        String origin = e.getCarData().getNumberPlate().getOrigin();
        p.setProperty("numberplate-origin", origin == null ? "null" : origin);

        String text = e.getCarData().getNumberPlate().getText();
        p.setProperty("numberplate-text", text == null ? "null" : text);

        String value = e.getCarData().getSpeed().getSpeed().toString();
        p.setProperty("speed-value", value == null ? "null" : value);
        p.setProperty("speed-unit", e.getCarData().getSpeed().getUnit().toString());

        p.setProperty("address-city", e.getCarData().getAddress().getCity());
        p.setProperty("address-street", e.getCarData().getAddress().getStreet());
        p.setProperty("address-streetNo", e.getCarData().getAddress().getStreetNumber().toString());
        p.setProperty("address-country", e.getCarData().getAddress().getCountry());
        p.setProperty("address-refNo", e.getCarData().getAddress().getReferenceNumber().toString());
        p.setProperty("address-postalCode", e.getCarData().getAddress().getPostalCode());
        p.setProperty("address-lat", e.getCarData().getAddress().getLatitude().toString());
        p.setProperty("address-long", e.getCarData().getAddress().getLongitude().toString());

        p.setProperty("timestamp", String.valueOf(e.getCarData().getTimestamp().getTime()));

        List<FileCarImage> fciList = e.getCarImages();

        for (int i = 0; i < fciList.size(); i++) {
            p.setProperty("filepath" + i, fciList.get(i).getFilepath().toString());
        }

        Path outFilePath = Paths.get(directory.toString(), e.hashCode() + "-" + System.currentTimeMillis()
                + ".properties");

        FileOutputStream fous = new FileOutputStream(outFilePath.toFile());
        p.store(fous, fciList.get(0).getFilepath().getFileName().toString());

    }
}