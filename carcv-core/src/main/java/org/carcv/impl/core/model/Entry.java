/**
 * 
 */
package org.carcv.impl.core.model;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import org.carcv.core.model.AbstractEntry;

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
    @OneToOne
    public CarData getCarData() {
        return carData;
    }

    /* (non-Javadoc)
     * @see org.carcv.core.model.AbstractEntry#getVideo()
     */
    @Override
    @OneToOne
    public MediaObject getVideo() {
        return video;
    }

}
