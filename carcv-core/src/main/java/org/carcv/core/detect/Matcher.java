/**
 * 
 */
package org.carcv.core.detect;

import org.carcv.core.model.CarImage;

/**
 * @author oskopek
 *
 */
public interface Matcher {
    public boolean match(CarImage leftImage, CarImage rightImage);
}
