/**
 * 
 */
package org.carcv.core.model;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * @author oskopek
 *
 */
@Entity
public class Entry extends AbstractModel {
    
    /**
     * 
     */
    private static final long serialVersionUID = 2309289364991527040L;
    
    private CarData carData;
    private CarImage carImage;
    

    @SuppressWarnings("unused")
    private Entry() {
        //intentionally empty
    }

    /**
     * 
     */
    public Entry(CarData carData, CarImage carImage) {
        this.carData = carData;
        this.carImage = carImage;
    }

    /* (non-Javadoc)
     * @see org.carcv.core.model.AbstractEntry#getCarData()
     */
    @NotNull
    @OneToOne
    public CarData getCarData() {
        return carData;
    }

    /* (non-Javadoc)
     * @see org.carcv.core.model.AbstractEntry#getVideo()
     */
    @NotNull
    @OneToOne
    public CarImage getCarImage() {
        return carImage;
    }
    
    /**
     * @param carData the carData to set
     */
    public void setCarData(CarData carData) {
        this.carData = carData;
    }

    /**
     * @param video the video to set
     */
    public void setCarImage(CarImage carImage) {
        this.carImage = carImage;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCarData())
            .append(getCarImage())
            .toHashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Entry)) {
            return false;
        }
        Entry other = (Entry) obj;

        return new EqualsBuilder()
                .append(getCarImage(), other.getCarImage())
                .append(getCarData(), other.getCarData())
                .isEquals();
        
    }

}
