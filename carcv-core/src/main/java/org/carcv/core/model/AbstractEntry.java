/**
 * 
 */
package org.carcv.core.model;

import java.util.List;

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
    public abstract List<? extends AbstractCarImage> getCarImages();

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCarData())
            .append(getCarImages().size())
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
                .append(getCarImages().size(), other.getCarImages().size())
                .append(getCarData(), other.getCarData())
                .isEquals();
        
    }

}
