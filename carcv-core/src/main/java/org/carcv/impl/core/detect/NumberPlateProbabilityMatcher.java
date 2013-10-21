/**
 * 
 */
package org.carcv.impl.core.detect;

import java.util.Random;

import org.carcv.core.detect.Matcher;
import org.carcv.core.model.CarImage;

/**
 * @author oskopek
 *
 */
public class NumberPlateProbabilityMatcher implements Matcher {

    /**
     * 
     */
    public NumberPlateProbabilityMatcher() {
        // TODO Auto-generated constructor stub
    }

    /* (non-Javadoc)
     * @see org.carcv.core.detect.Matcher#match(org.carcv.core.input.CarImage, org.carcv.core.input.CarImage)
     */
    @Override
    public boolean match(CarImage leftImage, CarImage rightImage) {
        Random r = new Random();
        return r.nextBoolean();
    }

}
