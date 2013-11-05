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

package org.carcv.impl.core.output;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;

import org.carcv.core.model.AbstractEntry;
import org.carcv.core.model.file.FileCarImage;
import org.carcv.core.model.file.FileEntry;
import org.carcv.core.output.SaveBatch;

/**
 *
 */
public class FileSaveBatch implements SaveBatch {

    private Path directory;

    /**
     *
     */
    public FileSaveBatch(Path directory) {
        this.directory = directory;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.carcv.core.output.SaveBatch#save(java.util.ArrayList)
     */
    @Override
    public void save(final List<? extends AbstractEntry> batch) throws IOException {
        @SuppressWarnings("unchecked")
        final List<FileEntry> fileBatch = (List<FileEntry>) batch;

        saveFileBatch(fileBatch);
    }

    public void saveFileBatch(final List<FileEntry> fileBatch) throws IOException {
        for (FileEntry entry : fileBatch) {
            saveFileEntry(entry);
        }
    }

    private void saveFileEntry(FileEntry e) throws IOException {
        Properties p = new Properties();

        p.setProperty("numberplate-origin", e.getCarData().getNumberPlate().getOrigin());
        p.setProperty("numberplate-text", e.getCarData().getNumberPlate().getText());

        p.setProperty("speed-value", e.getCarData().getSpeed().getSpeed().toString());
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
            p.setProperty("filepath" + i, fciList.get(i).getPath().toString());
        }

        Path outFilePath = Paths.get(directory.toString(), fciList.get(0).getPath().getFileName().toString() + ".properties");

        FileOutputStream fous = new FileOutputStream(outFilePath.toFile());
        p.store(fous, fciList.get(0).getPath().getFileName().toString());

    }

}