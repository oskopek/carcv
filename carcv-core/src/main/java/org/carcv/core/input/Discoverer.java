/**
 * 
 */
package org.carcv.core.input;

import java.io.IOException;
import java.util.Queue;

/**
 * Discoverer of new input files - images/videos
 * 
 * @author oskopek
 * 
 */
public interface Discoverer<T extends Queue<? extends Object>> { //Queue<hack>

    /**
     * Returns the new or all input files in a Queue
     * 
     * @return
     */
    public T getQueue();

    /**
     * Optional method to invoke -> discover new input files
     */
    public void discover() throws IOException;

}
