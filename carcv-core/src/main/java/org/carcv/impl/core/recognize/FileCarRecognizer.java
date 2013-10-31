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

package org.carcv.impl.core.recognize;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

import org.carcv.core.input.DirectoryWatcher;
import org.carcv.core.model.NumberPlate;
import org.carcv.core.model.Speed;
import org.carcv.core.model.file.FileCarImage;
import org.carcv.core.model.file.FileEntry;
import org.carcv.core.recognize.CarRecognizer;
import org.carcv.impl.core.detect.CarSorterImpl;
import org.carcv.impl.core.detect.NumberPlateDetectorImpl;
import org.carcv.impl.core.detect.SpeedDetectorImpl;
import org.carcv.impl.core.output.FileSaveBatch;

/**
 *
 */
public class FileCarRecognizer extends CarRecognizer {

    private DirectoryWatcher watcher;

    private FileSaveBatch saver;

    public FileCarRecognizer(Path inDir, Path outDir) {
        watcher = new DirectoryWatcher(inDir);

        saver = new FileSaveBatch(outDir);

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.carcv.core.recognize.CarRecognizer#recognize()
     */
    @Override
    public void recognize() throws IOException {
        watcher.discover();

        // this is a batch that contains an entry for every directory -> need to separate it into individual cars
        final ArrayList<FileEntry> batch = new ArrayList<>(watcher.getNewEntries());

        final ArrayList<FileEntry> result = new ArrayList<>();

        ArrayList<FileEntry> directory;
        
        for (FileEntry entry : batch) {
            directory = new ArrayList<>();
            directory.add(entry);

            sortIntoCars(directory);

            detectNumberPlates(directory);
            detectSpeeds(directory);

            result.addAll(directory);
        }

        saver.save(result);
    }

    private void detectSpeeds(ArrayList<FileEntry> batch) {
        SpeedDetectorImpl sd = new SpeedDetectorImpl();
        
        for (FileEntry entry : batch) {
            Double d = sd.detectSpeed(entry.getCarImages());
            
            entry.getCarData().setSpeed(new Speed(d));
        }
    }

    private void detectNumberPlates(ArrayList<FileEntry> batch) {
        NumberPlateDetectorImpl npd = new NumberPlateDetectorImpl();

        for (FileEntry entry : batch) {
            for (FileCarImage image : entry.getCarImages()) {
                try {
                    image.loadImage();
                } catch (IOException e) {
                    e.printStackTrace();
                    System.err.println("Failed to load image: " + image.toString());
                }
            }

            String text = npd.detectPlateText(entry.getCarImages());
            String origin = npd.detectPlateOrigin(entry.getCarImages());

            for (FileCarImage image : entry.getCarImages()) {
                image.close();
            }
            entry.getCarData().setNumberPlate(new NumberPlate(text, origin));
        }
    }

    private void sortIntoCars(ArrayList<FileEntry> batch) {
        ArrayList<FileEntry> res = new ArrayList<>(); // prevents ConcurrentModificationException
        for (FileEntry dir : batch) {
            try {
                res.addAll(CarSorterImpl.getInstance().sortIntoCars(dir));
            } catch (IOException e) {
                System.err.println("Failed to load images in FileEntry at " + dir.getCarImages().get(0).getPath());
                e.printStackTrace();
            }
        }
        
        batch.clear();
        batch.addAll(res); // puts the new entries back to original ArrayList
    }

}