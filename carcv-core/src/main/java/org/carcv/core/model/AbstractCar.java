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
public abstract class AbstractCar extends AbstractModel {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    public abstract AbstractCarData getCarData();
    
    public abstract AbstractCarImage getCarImage();

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof AbstractCar)) {
            return false;
        }
        AbstractCar other = (AbstractCar) obj;

        return new EqualsBuilder()
                .append(getCarData(), other.getCarData())
                .append(getCarImage(), other.getCarImage())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(getCarData())
                .append(getCarImage())
                .toHashCode();
    }

}
