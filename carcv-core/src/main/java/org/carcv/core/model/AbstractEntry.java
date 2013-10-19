/**
 * 
 */
package org.carcv.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * @author oskopek
 *
 */
//@Entity
public abstract class AbstractEntry extends AbstractModel {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Column
    @OneToOne
    @NotNull
    public abstract AbstractCarData getCarData();
    
    @OneToOne
    @NotNull
    //@Column(name="video")
    public abstract AbstractMediaObject getVideo();
    
    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCarData())
            .append(getVideo())
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
                .append(getVideo(), other.getVideo())
                .append(getCarData(), other.getCarData())
                .isEquals();
        
    }
}
