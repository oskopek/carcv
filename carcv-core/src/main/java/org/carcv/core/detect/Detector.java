/**
 * 
 */
package org.carcv.core.detect;

import java.util.List;

import org.carcv.core.model.AbstractCarImage;

/**
 * @author oskopek
 * 
 */
public interface Detector {

    public String detect(final List<? extends AbstractCarImage> images);
}
