/**
 * 
 */
package org.carcv.core.model;

import java.util.Date;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * @author oskopek
 *
 */
@MappedSuperclass
public abstract class AbstractCarData extends AbstractModel {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * NOTE: Add @OneToOne annotation
     * @return
     */
    @NotNull
    public abstract AbstractSpeed getSpeed();

    /**
     * NOTE: Add @OneToOne annotation
     * @return
     */
    @NotNull
    public abstract AbstractAddress getAddress();

    /**
     * NOTE: Add @OneToOne annotation
     * @return
     */
    @NotNull
    public abstract AbstractNumberPlate getNumberPlate();

    @NotNull
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
