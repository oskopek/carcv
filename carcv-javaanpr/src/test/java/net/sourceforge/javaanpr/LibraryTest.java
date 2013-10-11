/**
 * 
 */
package net.sourceforge.javaanpr;

import static org.junit.Assert.*;
import org.junit.Test;

import javaanpr.imageanalysis.CarSnapshot;
import javaanpr.intelligence.Intelligence;

/**
 * @author oskopek
 *
 */
public class LibraryTest {
    
    @Test
    public void intelligenceTest() throws Exception {
        Intelligence intel = new Intelligence(false);
        assertNotNull(intel);
        
        String spz = intel.recognize(new CarSnapshot("snapshots/test_006.jpg"));
        assertEquals("RK099AN", spz);
        //System.out.println(intel.lastProcessDuration());
    }

}
