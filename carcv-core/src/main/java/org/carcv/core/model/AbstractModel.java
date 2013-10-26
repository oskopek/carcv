/**
 * 
 */
package org.carcv.core.model;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.CompareToBuilder;

/**
 * @author oskopek
 * 
 */
@MappedSuperclass
public abstract class AbstractModel implements Serializable, Comparable<AbstractModel> {

    /**
     * 
     */
    private static final long serialVersionUID = -5140579589148423614L;

    private Long id;

    @Id
    @GeneratedValue
    @NotNull
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public abstract boolean equals(Object o);

    @Override
    public abstract int hashCode();

    @Override
    public int compareTo(AbstractModel o) {
        return new CompareToBuilder().append(getClass().getName(), o.getClass().getName()).append(id, o.id)
                .toComparison();
    }

}
