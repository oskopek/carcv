/**
 * 
 */
package org.carcv.core.input;

import java.awt.Image;
import java.io.Serializable;

/**
 * @author oskopek
 *
 */
public interface CarImage extends AutoCloseable, Serializable {    

    /**
     * @return the image
     */
    public Image getImage();

    /**
     * @return the id
     */
    public Number getId(); 
}
