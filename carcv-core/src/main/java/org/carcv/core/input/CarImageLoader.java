/**
 * 
 */
package org.carcv.core.input;

import java.io.IOException;
import java.util.List;

import org.carcv.core.model.CarData;
import org.carcv.core.model.CarImage;
import org.carcv.core.model.Entry;

/**
 * Need to override init method, to initialize <code>discoverer</code> and <code>queue</code>
 * variables
 * 
 * @author oskopek
 * 
 */
public abstract class CarImageLoader {

    /**
     * Doesn't try to discover new elements
     * 
     * @return
     * @throws IOException
     */
    public List<? extends Entry> getBatch() throws IOException {

        getDiscoverer().discover();

        return getBatchNoDiscover();
    }

    /**
     * Doesn't try to discover new elements first
     * 
     * @return
     */
    public abstract List<? extends Entry> getBatchNoDiscover(); /*{
        
        CarData cd = fetchAllCarData();

        List<Entry> list = new ArrayList<>();

        Entry e = null;
        
        while (!getDiscoverer().getQueue().isEmpty()) {
            CarImage ci = getDiscoverer().getQueue().poll();
            
            e = new Entry(cd, ci); //TODO: fix cannot instantiate abstract class error
            
            list.add(e);
        }

        return list;
    }*/

    public abstract Discoverer<? extends ImageQueue<? extends CarImage>> getDiscoverer();
    
    public abstract CarData fetchAllCarData();
}
