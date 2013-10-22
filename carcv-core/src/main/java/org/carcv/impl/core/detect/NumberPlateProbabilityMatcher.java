/**
 * 
 */
package org.carcv.impl.core.detect;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import org.carcv.core.detect.Matcher;
import org.carcv.core.model.AbstractCarImage;

/**
 * @author oskopek
 *
 */
public class NumberPlateProbabilityMatcher implements Matcher { //TODO: test and implement NumberPlateProbabilityMatcher


    /* (non-Javadoc)
     * @see org.carcv.core.detect.Matcher#match(org.carcv.core.input.CarImage, org.carcv.core.input.CarImage)
     */
    @Override
    public boolean match(AbstractCarImage leftImage, AbstractCarImage rightImage) {
        Random r = new Random();
        return r.nextBoolean(); //TODO implement
    }
    
    public static String getAverageNumberPlate(final List<String> numberPlates) {
        HashMap<String, Integer> map = new HashMap<>();
        
        for(String s : numberPlates) {
            Integer count = map.get(s);
            map.put(s, count != null ? count+1 : 0);
        }
        
        String popular = Collections.max(map.entrySet(),
                new Comparator<Entry<String, Integer>>() {

                @Override
                public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
                    return o1.getValue().compareTo(o2.getValue());
                }
            }).getKey();
        
        
        return popular;
    }

}
