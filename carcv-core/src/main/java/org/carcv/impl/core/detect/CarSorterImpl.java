/*
 * Copyright 2013-2014 CarCV Development Team
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.carcv.core.detect.CarSorter;
import org.carcv.core.model.CarData;
import org.carcv.core.model.file.FileCarImage;
import org.carcv.core.model.file.FileEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provides a Singleton probabilistic implementation of <code>CarSorter</code> based on the <a
 * href="http://en.wikipedia.org/wiki/Levenshtein_distance#Iterative_with_two_matrix_rows">Levenshtein Distance</a> between the
 * two number plate Strings.
 *
 * <p>
 * This class is a Singleton, not meant to be instantiated.
 *
 * @see CarSorterImpl#getInstance()
 */
public class CarSorterImpl extends CarSorter {

    final private static Logger LOGGER = LoggerFactory.getLogger(CarSorterImpl.class);

    final private static CarSorterImpl instance = new CarSorterImpl();

    final private static int equalityDistanceCoef = 3;

    private CarSorterImpl() {

    }

    /**
     * Returns a reference to the static singleton instantiation of CarSorterImpl
     *
     * @return reference to static CarSorterImpl instance
     */
    public static CarSorterImpl getInstance() {
        return instance;
    }

    /**
     * Sorts cars into separate FileEntries. Automatically loads and closes images internally on-demand.
     */
    @Override
    public List<FileEntry> sortIntoCars(FileEntry batchDir) throws IOException {
        ArrayList<FileEntry> list = new ArrayList<>();

        ArrayList<FileCarImage> images = (ArrayList<FileCarImage>) batchDir.getCarImages();

        if (images.isEmpty()) {
            return new ArrayList<FileEntry>();
        }

        // Before for-loop:
        images.get(0).loadImage(); // Load first image
        ArrayList<FileCarImage> temp = new ArrayList<>();
        temp.add(images.get(0));

        CarData cData = null;
        try {
            cData = ((CarData) batchDir.getCarData().clone());
        } catch (CloneNotSupportedException e) {
            LOGGER.error("Clone not supported! Assigning null to the CarData instance at {}.", 0);
            e.printStackTrace();
        }

        list.add(new FileEntry(cData, temp)); // Add first FileEntry

        for (int i = 1; i < images.size(); i++) {
            images.get(i).loadImage();

            boolean equals = this.carsEquals(images.get(i - 1), images.get(i));

            if (equals) { // if images are of same car, add the image to the last known car
                ArrayList<FileCarImage> f = (ArrayList<FileCarImage>) list.get(list.size() - 1).getCarImages();
                f.add(images.get(i));
            } else { // else create a new entry with the image
                ArrayList<FileCarImage> temp2 = new ArrayList<>();
                temp2.add(images.get(i));

                CarData cData2 = null;
                try {
                    cData2 = (CarData) batchDir.getCarData().clone();
                } catch (CloneNotSupportedException e) {
                    LOGGER.error("Clone not supported! Assigning null to this CarData instance at {}.", i);
                    e.printStackTrace();
                }

                list.add(new FileEntry(cData2, temp2));
            }

            images.get(i - 1).close();
        }
        images.get(images.size() - 1).close();

        // TODO 2 Should close all images in List<FileEntry> list or are they closed already?
        for (FileEntry f : list) {
            for (FileCarImage i : f.getCarImages()) {
                i.close();
            }
        }

        return list;
    }

    @Override
    public boolean carsEquals(FileCarImage one, FileCarImage two) {
        return numberPlateProbabilityEquals(one, two);
    }

    @Override
    public boolean carsEquals(FileCarImage one, String twoPlate) {
        return numberPlateProbabilityEquals(one, twoPlate);
    }

    @Override
    public boolean carsEquals(String onePlate, String twoPlate) {
        return numberPlateProbabilityEquals(onePlate, twoPlate);
    }

    private boolean numberPlateProbabilityEquals(String onePlate, String twoPlate) {
        int dist = levenshteinDistance(onePlate, twoPlate);

        return dist < CarSorterImpl.equalityDistanceCoef;
    }

    private boolean numberPlateProbabilityEquals(FileCarImage one, String twoPlate) {
        try {
            one.loadImage();
        } catch (IOException e) {
            LOGGER.error("Failed to load image for CarSorterImpl!");
            e.printStackTrace();
        }

        NumberPlateDetectorImpl npd = NumberPlateDetectorImpl.getInstance();
        ArrayList<FileCarImage> oneList = new ArrayList<>();
        oneList.add(one);
        String onePlate = npd.detectPlateText(oneList);

        one.close();

        return numberPlateProbabilityEquals(onePlate, twoPlate);
    }

    private boolean numberPlateProbabilityEquals(FileCarImage one, FileCarImage two) {

        try {
            one.loadImage();
            two.loadImage();
        } catch (IOException e) {
            LOGGER.error("Failed to load images for CarSorterImpl!");
            e.printStackTrace();
        }

        if (one.getImage() == null || two.getImage() == null) {
            LOGGER.error("Images are null in CarSorterImpl!");
        }

        NumberPlateDetectorImpl npd = NumberPlateDetectorImpl.getInstance();

        ArrayList<FileCarImage> oneList = new ArrayList<>();
        ArrayList<FileCarImage> twoList = new ArrayList<>();

        oneList.add(one);
        twoList.add(two);

        String onePlate = npd.detectPlateText(oneList);
        String twoPlate = npd.detectPlateText(twoList);

        one.close();
        two.close();

        return numberPlateProbabilityEquals(onePlate, twoPlate);
    }

    private static int levenshteinDistance(String s, String t)
    {
        // degenerate cases
        if (s.equals(t))
            return 0;
        if (s.length() == 0)
            return t.length();
        if (t.length() == 0)
            return s.length();

        // create two work vectors of integer distances
        int[] v0 = new int[t.length() + 1];
        int[] v1 = new int[t.length() + 1];

        // initialize v0 (the previous row of distances)
        // this row is A[0][i]: edit distance for an empty s
        // the distance is just the number of characters to delete from t
        for (int i = 0; i < v0.length; i++)
            v0[i] = i;

        for (int i = 0; i < s.length(); i++)
        {
            // calculate v1 (current row distances) from the previous row v0

            // first element of v1 is A[i+1][0]
            // edit distance is delete (i+1) chars from s to match empty t
            v1[0] = i + 1;

            // use formula to fill in the rest of the row
            for (int j = 0; j < t.length(); j++)
            {
                int cost = (s.charAt(i) == t.charAt(j) ? 0 : 1);
                v1[j + 1] = Math.min(Math.min(v1[j] + 1, v0[j + 1] + 1), v0[j] + cost);
            }

            // copy v1 (current row) to v0 (previous row) for next iteration
            // for (int j = 0; j < v0.length; j++)
            // v0[j] = v1[j];

            v0 = Arrays.copyOfRange(v1, 0, v0.length);
        }

        return v1[t.length()];
    }
}