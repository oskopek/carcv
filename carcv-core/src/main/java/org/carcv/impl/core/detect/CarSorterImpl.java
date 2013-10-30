package org.carcv.impl.core.detect;

import org.carcv.core.detect.CarSorter;
import org.carcv.core.model.file.FileCarImage;
import org.carcv.core.model.file.FileEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class CarSorterImpl extends CarSorter {  // TODO 1 Test CarSorterImpl

    private static CarSorterImpl instance = new CarSorterImpl();

    private CarSorterImpl() {

    }

    public static CarSorterImpl getInstance() {
        return instance;
    }

    @Override
    public List<FileEntry> sortIntoCars(FileEntry batchDir)  {
        List<FileEntry> list = new ArrayList<>();

        List<FileCarImage> images = batchDir.getCarImages();


        for(int i = 1; i < images.size(); i++) {
            boolean equals = this.carsEquals(images.get(i-1), images.get(i));

            if(equals) { // if images are of same car, add the image to the last known car
                list.get(list.size()-1).getCarImages().add(images.get(i));
            } else { // else create a new entry with the image
                list.add(new FileEntry(batchDir.getCarData(), Arrays.asList(images.get(i))));
            }

        }

        return list;
    }

    @Override
    public boolean carsEquals(FileCarImage one, FileCarImage two) {
        return numberPlateProbabilityEquals(one, two);
    }

    private boolean numberPlateProbabilityEquals(FileCarImage one, FileCarImage two) {
        NumberPlateDetectorImpl npd = new NumberPlateDetectorImpl();

        ArrayList<FileCarImage> oneList = new ArrayList<>();
        ArrayList<FileCarImage> twoList = new ArrayList<>();

        oneList.add(one);
        twoList.add(two);

        String onePlate = npd.detectPlateText(oneList);
        String twoPlate = npd.detectPlateText(twoList);

        int dist = levenshteinDistance(onePlate, twoPlate);

        return dist<3;
    }

    private static int levenshteinDistance(String s, String t)
    {
        // degenerate cases
        if (s.equals(t)) return 0;
        if (s.length() == 0) return t.length();
        if (t.length() == 0) return s.length();

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
            //   edit distance is delete (i+1) chars from s to match empty t
            v1[0] = i + 1;

            // use formula to fill in the rest of the row
            for (int j = 0; j < t.length(); j++)
            {
                int cost = (s.charAt(i) == t.charAt(j) ? 0 : 1);
                v1[j + 1] = Math.min(Math.min(v1[j] + 1, v0[j + 1] + 1), v0[j] + cost);
            }

            // copy v1 (current row) to v0 (previous row) for next iteration
            //for (int j = 0; j < v0.length; j++)
            //    v0[j] = v1[j];

            v0 = Arrays.copyOfRange(v1, 0, v0.length);
        }

        return v1[t.length()];
    }
}
