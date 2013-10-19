/**
 * 
 */
package org.carcv.core.input;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.carcv.core.model.AbstractCarImage;

/**
 * Need to override init method, to initialize <code>discoverer</code> and <code>queue</code>
 * variables
 * 
 * @author oskopek
 * 
 */
public abstract class CarImageLoader {

    private Discoverer<ImageQueue<AbstractCarImage>> discoverer;

    /**
     * Doesn't try to discover new elements
     * 
     * @return
     * @throws IOException
     */
    public List<? extends AbstractCarImage> getBatch() throws IOException {

        discoverer.discover();

        List<AbstractCarImage> list = new ArrayList<>();

        while (!discoverer.getQueue().isEmpty()) {
            list.add(discoverer.getQueue().poll());
        }

        return list;
    }

    /**
     * Doesn't try to discover new elements first
     * 
     * @return
     */
    public List<? extends AbstractCarImage> getBatchNoDiscover() {

        List<AbstractCarImage> list = new ArrayList<>();

        while (!discoverer.getQueue().isEmpty()) {
            list.add(discoverer.getQueue().poll());
        }

        return list;
    }
}
