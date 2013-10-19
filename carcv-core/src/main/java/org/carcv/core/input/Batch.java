/**
 * 
 */
package org.carcv.core.input;

import java.util.HashMap;

/**
 * @author oskopek
 *
 */
public interface Batch {
    
    public HashMap<? extends CarInfo, ? extends CarImage> getBatch(); 
}
