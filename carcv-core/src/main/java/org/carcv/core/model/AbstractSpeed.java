/**
 * 
 */
package org.carcv.core.model;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * @author oskopek
 *
 */
@MappedSuperclass
public abstract class AbstractSpeed extends AbstractModel {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    @NotNull
    //@Range(min = 0, message = "Speed is less or equal to 0!")
    public abstract Double getSpeed();
    

    @NotNull  
    public abstract SpeedUnit getUnit();


    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(getSpeed()).append(getUnit()).toHashCode();
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
        if (!(obj instanceof AbstractSpeed)) {
            return false;
        }
        AbstractSpeed other = (AbstractSpeed) obj;
        return new EqualsBuilder()
                .append(this.getSpeed(), other.getSpeed())
                .append(this.getUnit(), other.getUnit())
                .isEquals();
    }

}
