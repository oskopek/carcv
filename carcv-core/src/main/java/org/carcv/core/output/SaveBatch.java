/**
 * 
 */
package org.carcv.core.output;

import java.util.ArrayList;

import org.carcv.core.model.AbstractEntry;

/**
 * @author oskopek
 * 
 */
public interface SaveBatch {

    /**
     * 
     * @param batch
     * @return false if save was unsuccessful, true if successful
     */
    public boolean save(final ArrayList<? extends AbstractEntry> batch);
}
