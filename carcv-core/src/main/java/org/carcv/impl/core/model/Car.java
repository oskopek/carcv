/**
 * 
 */
package org.carcv.impl.core.model;

import javax.persistence.Entity;

import org.carcv.core.model.AbstractCar;
import org.carcv.core.model.AbstractCarData;
import org.carcv.core.model.AbstractCarImage;
import org.carcv.impl.core.model.file.FileCarImage;

/**
 * @author oskopek
 *
 */
@Entity
public class Car extends AbstractCar {
    
    /**
     * 
     */
    private static final long serialVersionUID = 2506409391922336854L;

    private CarData carData;
    
    private FileCarImage carImage;
    
    public Car(CarData carData, FileCarImage carImage) {
        this.carData = carData;
        this.carImage = carImage;
    }

    /* (non-Javadoc)
     * @see org.carcv.core.model.AbstractCar#getCarData()
     */
    @Override
    public AbstractCarData getCarData() {
        return carData;
    }

    /* (non-Javadoc)
     * @see org.carcv.core.model.AbstractCar#getCarImage()
     */
    @Override
    public AbstractCarImage getCarImage() {
        return carImage;
    }

}
