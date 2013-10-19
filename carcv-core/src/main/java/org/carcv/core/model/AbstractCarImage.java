/**
 * 
 */
package org.carcv.core.model;

import java.awt.Image;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

/**
 * @author oskopek
 *
 */
@Entity
public abstract class AbstractCarImage extends AbstractModel implements AutoCloseable {    

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * @return the image
     */
    @Column
    @NotNull
    public abstract Image getImage();
}
