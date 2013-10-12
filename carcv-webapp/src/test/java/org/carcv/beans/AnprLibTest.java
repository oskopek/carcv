/**
 * 
 */
package org.carcv.beans;

import static org.junit.Assert.*;

import java.io.IOException;

import net.sf.javaanpr.imageanalysis.CarSnapshot;
import net.sf.javaanpr.intelligence.Intelligence;

import org.junit.Test;

/**
 * @author oskopek
 *
 */
public class AnprLibTest {

    @Test
    public void anprLibTest() throws IOException, Exception {
        Intelligence intel = new Intelligence();
        assertNotNull(intel);
        
        String spz = intel.recognize(new CarSnapshot("img/skoda_oct.jpg"));
        assertEquals("2SU358F", spz); //actually 2SU358F
    }
}
