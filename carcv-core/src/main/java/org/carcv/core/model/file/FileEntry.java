/**
 * 
 */
package org.carcv.core.model.file;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import org.carcv.core.model.CarData;
import org.carcv.core.model.AbstractCarImage;
import org.carcv.core.model.AbstractEntry;

/**
 * @author oskopek
 *
 */
@Entity
public class FileEntry extends AbstractEntry {
    
    /**
     * 
     */
    private static final long serialVersionUID = -8030471101247536237L;
    
    
    private FileCarImage carImage;
    private CarData carData;

    /**
     * @param carData
     * @param abstractCarImage
     */
    public FileEntry(CarData carData, AbstractCarImage abstractCarImage) {
        this.carData = carData;
        this.carImage = (FileCarImage) abstractCarImage;
    }
    
    /**
     * @param carData
     * @param fileCarImage
     */
    public FileEntry(CarData carData, FileCarImage fileCarImage) {
        this.carData = carData;
        this.carImage = fileCarImage;
    }

    @Override
    @OneToOne
    @NotNull
    public CarData getCarData() {
        return carData;
    }

    @Override
    @OneToOne
    public FileCarImage getCarImage() {
        return carImage;
    }

    /**
     * @param carImage the carImage to set
     */
    public void setCarImage(FileCarImage carImage) {
        this.carImage = carImage;
    }

    /**
     * @param carData the carData to set
     */
    public void setCarData(CarData carData) {
        this.carData = carData;
    }

}
