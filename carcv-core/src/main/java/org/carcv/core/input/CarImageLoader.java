/**
 * 
 */
package org.carcv.core.input;

import java.io.IOException;
import java.util.List;

import org.carcv.core.model.CarData;
import org.carcv.core.model.AbstractCarImage;
import org.carcv.core.model.AbstractEntry;

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
    public List<? extends AbstractEntry> getBatch() throws IOException {

        getDiscoverer().discover();

        return getBatchNoDiscover();
    }

    /**
     * Doesn't try to discover new elements first
     * 
     * @return
     */
    public abstract List<? extends AbstractEntry> getBatchNoDiscover(); /*{
        
        CarData cd = fetchAllCarData();

        List<AbstractEntry> list = new ArrayList<>();

        AbstractEntry e = null;
        
        while (!getDiscoverer().getQueue().isEmpty()) {
            AbstractCarImage ci = getDiscoverer().getQueue().poll();
            
            e = new AbstractEntry(cd, ci); //TODO: fix cannot instantiate abstract class error
            
            list.add(e);
        }

        return list;
    }*/

    public abstract Discoverer<? extends ImageQueue<? extends AbstractCarImage>> getDiscoverer();
    
    public abstract CarData fetchAllCarData();
}
