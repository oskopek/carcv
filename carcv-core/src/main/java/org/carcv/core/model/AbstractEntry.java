/**
 * 
 */
package org.carcv.core.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * @author oskopek
 *
 */
public abstract class AbstractEntry extends AbstractModel {
    
    /**
     * 
     */
    private static final long serialVersionUID = 2309289364991527040L;
    /*
    private CarData carData;
    private AbstractCarImage carImage;
   
    public AbstractEntry(CarData carData, AbstractCarImage carImage) {
        this.carData = carData;
        this.carImage = carImage;
    }

    */
    
    public abstract CarData getCarData();
    public abstract AbstractCarImage getCarImage();

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
        if (!(obj instanceof AbstractEntry)) {
            return false;
        }
        AbstractEntry other = (AbstractEntry) obj;

        return new EqualsBuilder()
                .append(getCarImage(), other.getCarImage())
                .append(getCarData(), other.getCarData())
                .isEquals();
        
    }

}
