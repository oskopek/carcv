/**
 * 
 */
package org.carcv.core.detect;

import org.carcv.core.model.AbstractCarImage;

/**
 * @author oskopek
 * 
 */
public interface Matcher {
    public boolean match(AbstractCarImage leftImage, AbstractCarImage rightImage);
}
