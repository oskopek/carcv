/**
 * 
 */
package org.carcv.core.output;

import java.util.List;

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
    public boolean save(final List<? extends AbstractEntry> batch);
}
