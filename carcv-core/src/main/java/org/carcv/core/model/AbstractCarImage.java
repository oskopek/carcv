/**
 * 
 */
package org.carcv.core.model;

import java.awt.image.BufferedImage;

/**
 * @author oskopek
 * 
 */
public abstract class AbstractCarImage extends AbstractModel implements AutoCloseable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * @return the image
     */
    public abstract BufferedImage getImage();
}
