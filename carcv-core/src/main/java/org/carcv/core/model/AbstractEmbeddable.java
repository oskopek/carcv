/**
 * 
 */
package org.carcv.core.model;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

/**
 * @author oskopek
 *
 */
@MappedSuperclass
public abstract class AbstractEmbeddable implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = 7435193250078288118L;

    @Override
    public abstract boolean equals(Object o);
    
    @Override
    public abstract int hashCode();

}
