package org.carcv.impl.core.detect;

import org.carcv.core.detect.CarSorter;
import org.carcv.core.model.file.FileCarImage;
import org.carcv.core.model.file.FileEntry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    public boolean carsEquals(FileCarImage one, FileCarImage two) {   // TODO 1 Implement: sort them into multiple FileEntries
        return numberPlateProbabilityEquals(one, two);
    }

    private boolean numberPlateProbabilityEquals(FileCarImage one, FileCarImage two) {
        return true;
    }
}
