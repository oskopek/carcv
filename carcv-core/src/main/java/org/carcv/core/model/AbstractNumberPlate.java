/**
 * 
 */
package org.carcv.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.carcv.impl.core.model.NumberPlate;

/**
 * @author oskopek
 *
 */
@Entity
//@Table(name = "numberplate")
public abstract class AbstractNumberPlate extends AbstractModel {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    @Column
    @NotNull
    public abstract String getText();
    
    @Column
    @NotNull
    public abstract String getOrigin();
    


    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(getText()).append(getOrigin()).toHashCode();
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
        if (!(obj instanceof NumberPlate)) {
            return false;
        }
        NumberPlate other = (NumberPlate) obj;
        return new EqualsBuilder()
                .append(getText(), other.getText())
                .append(getOrigin(), other.getOrigin())
                .isEquals();
    }
}
