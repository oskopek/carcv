/**
 * 
 */
package org.carcv.core.output;

import java.util.ArrayList;

import org.carcv.core.model.Entry;

/**
 * @author oskopek
 *
 */
public interface SaveBatch {
    
    public boolean save(ArrayList<Entry> list);
}
