/**
 * 
 */
package org.carcv.core.detect;

import org.carcv.core.model.AbstractCarImage;

/**
 * @author oskopek
 *
 */
public interface Detector {

    public String detect(final AbstractCarImage image);
}
