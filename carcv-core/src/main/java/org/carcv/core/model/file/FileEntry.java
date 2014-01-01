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

package org.carcv.core.model.file;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.carcv.core.model.AbstractEntry;
import org.carcv.core.model.CarData;

/**
 * An implementation of AbstractEntry using CarData and FileCarImages.
 */
@Entity
public class FileEntry extends AbstractEntry {

    private static final long serialVersionUID = -8030471101247536237L;

    private List<FileCarImage> carImages;
    private CarData carData;

    /**
     * A private default constructor used for persistence.
     */
    @SuppressWarnings("unused")
    private FileEntry() {
        // intentionally empty
    }

    /**
     * A constructor for FileEntry specifying CarData and a list of FileCarImages.
     *
     * @param carData instance of CarData corresponding to the given car
     * @param carImages a list of images of the car
     */
    public FileEntry(CarData carData, List<FileCarImage> carImages) {
        this.carData = carData;
        this.carImages = carImages;
    }

    /**
     * @return CarData of this FileEntry
     */
    @Override
    @NotNull
    @Embedded
    public CarData getCarData() {
        return carData;
    }

    /**
     * @return a list of FileCarImages of this FileEntry
     */
    @Override
    @NotNull
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = FileCarImage.class, orphanRemoval = true)
    public List<FileCarImage> getCarImages() {
        return carImages;
    }

    /**
     * @param carImages the list of FileCarImages to set
     */
    public void setCarImages(List<FileCarImage> carImages) {
        this.carImages = carImages;
    }

    /**
     * @param carData the CarData to set
     */
    public void setCarData(CarData carData) {
        this.carData = carData;
    }

    @Override
    public String toString() {
        return "FileEntry[id=" + getId() + ", carImages.size()=" + carImages.size() + ", carData=" + carData.toString()
            + "]";
    }

    @Override
    public int hashCode() {
        HashCodeBuilder hcb = new HashCodeBuilder();

        hcb.append(getCarData()).append(getCarImages().size());

        for (FileCarImage fci : getCarImages()) {
            hcb.append(fci);
        }

        return hcb.toHashCode();
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof FileEntry)) {
            return false;
        }
        FileEntry other = (FileEntry) obj;

        if (getCarImages().size() != other.getCarImages().size()) {
            return false;
        }

        EqualsBuilder e = new EqualsBuilder().append(getCarData(), other.getCarData());

        for (int i = 0; i < getCarImages().size(); i++) {
            e.append(getCarImages().get(i), other.getCarImages().get(i));
        }

        return e.isEquals();

    }

}