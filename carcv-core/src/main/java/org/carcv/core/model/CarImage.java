/**
 * 
 */
package org.carcv.core.model;

import java.awt.Image;

/**
 * @author oskopek
 *
 */
public abstract class CarImage extends AbstractModel implements AutoCloseable {    

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * @return the image
     */
    public abstract Image getImage();
}
