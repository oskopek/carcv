/**
 * 
 */
package org.carcv.core.input;

import java.util.Queue;

/**
 * Discoverer of new input files - images/videos 
 * @author oskopek
 *
 */
public interface Discoverer<T extends Queue> {
    
    /**
     * Returns the new or all input files in a Queue
     * @return
     */
    public T getQueue();

}
