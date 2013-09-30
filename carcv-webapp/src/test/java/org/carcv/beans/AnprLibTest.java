/**
 * 
 */
package org.carcv.beans;

import static org.junit.Assert.*;
import javaanpr.imageanalysis.CarSnapshot;
import javaanpr.intelligence.Intelligence;

import org.junit.Test;

/**
 * @author oskopek
 *
 */
public class AnprLibTest {

    @Test
    public void anprLibTest() throws Exception {
        Intelligence intel = new Intelligence(false);
        assertNotNull(intel);
        
        String spz = intel.recognize(new CarSnapshot(ClassLoader.getSystemClassLoader().getResource("img/skoda_oct.jpg").getFile()));
        assertEquals("2SU358F", spz); //actually 2SU358F
    }
}
