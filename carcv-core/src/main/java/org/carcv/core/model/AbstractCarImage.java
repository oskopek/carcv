/**
 * 
 */
package org.carcv.core.model;

import java.awt.Image;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

/**
 * @author oskopek
 *
 */
@MappedSuperclass
public abstract class AbstractCarImage extends AbstractModel implements AutoCloseable {    

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * @return the image
     */
    @NotNull
    public abstract Image getImage();
}
