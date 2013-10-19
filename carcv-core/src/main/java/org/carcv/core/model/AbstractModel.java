/**
 * 
 */
package org.carcv.core.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.CompareToBuilder;

/**
 * @author oskopek
 *
 */
@Entity
public abstract class AbstractModel implements Serializable, Comparable<AbstractModel> {
    
    private Long id;

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue
    @NotNull
    public Long getId() {
        return id;
    }
    
    @Override
    public abstract boolean equals(Object o);
    
    @Override
    public abstract int hashCode();

    @Override
    public int compareTo(AbstractModel o) {
        return new CompareToBuilder().append(getClass().getName(), o.getClass().getName())
                .append(id, o.id)
                .toComparison();
    }
    
    

}
