/**
 * 
 */
package org.carcv.core.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * @author oskopek
 *
 */
@Entity
//@Table(name = "cardata")
public abstract class AbstractCarData extends AbstractModel {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @ManyToOne
    @NotNull
    //@Column(name="speed")
    public abstract AbstractSpeed getSpeed();

    @ManyToOne
    @NotNull
    //@Column(name="location")
    public abstract AbstractAddress getAddress();

    @ManyToOne
    @NotNull
    //@Column(name="licenceplate")
    public abstract AbstractNumberPlate getNumberPlate();

    @NotNull
    //@Column(name = "timestamp")
    //@Type(type="TIMESTAMP")
    public abstract Date getTimestamp();

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(getSpeed()).append(getAddress())
                .append(getNumberPlate()).append(getTimestamp())
                .toHashCode();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof AbstractCarData)) {
            return false;
        }
        AbstractCarData other = (AbstractCarData) obj;

        return new EqualsBuilder()
                .append(getSpeed(), other.getSpeed())
                .append(getAddress(), other.getAddress())
                .append(getNumberPlate(), other.getNumberPlate())
                .append(getTimestamp(), other.getTimestamp())
                .isEquals();
    }
    
}
