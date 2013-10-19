/**
 * 
 */
package org.carcv.impl.core.model;

import javax.persistence.Entity;

import org.carcv.core.model.AbstractCarData;
import org.carcv.core.model.AbstractEntry;
import org.carcv.core.model.AbstractMediaObject;

/**
 * @author oskopek
 *
 */
@Entity
public class Entry extends AbstractEntry {
    
    /**
     * 
     */
    private static final long serialVersionUID = 2309289364991527040L;
    
    
    private CarData carData;
    private MediaObject video;

    /**
     * 
     */
    public Entry(CarData carData, MediaObject video) {
        this.carData = carData;
        this.video = video;
    }

    /* (non-Javadoc)
     * @see org.carcv.core.model.AbstractEntry#getCarData()
     */
    @Override
    public AbstractCarData getCarData() {
        return carData;
    }

    /* (non-Javadoc)
     * @see org.carcv.core.model.AbstractEntry#getVideo()
     */
    @Override
    public AbstractMediaObject getVideo() {
        return video;
    }

}
