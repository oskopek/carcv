/**
 * 
 */
package org.carcv.core.model;

import static org.junit.Assert.*;

import org.carcv.core.model.Speed;
import org.carcv.core.model.SpeedUnit;
import org.junit.Test;

/**
 * @author oskopek
 *
 */
public class SpeedTest {

    /**
     * Test method for {@link org.carcv.core.model.Speed#equals(java.lang.Object)}.
     */
    @Test
    public void testEqualsObject() {
        Speed s1 = new Speed(80.2, SpeedUnit.KPH);
        assertEquals(s1, s1);
        
        Speed s2 = new Speed(80.2, SpeedUnit.KPH);
        assertEquals(s1, s2);
        
        s1.setId(1);
        s2.setId(2);
        
        assertEquals(s1, s2);
        
        s2 = new Speed(80.2, SpeedUnit.MPH);
        
        assertNotEquals(s1, s2);
        
        s1 = new Speed(80.15, SpeedUnit.KPH);
        s2 = new Speed(80.2, SpeedUnit.KPH);
        
        assertNotEquals(s1, s2);
    }

    /**
     * Test method for {@link org.carcv.core.model.Speed#compareTo(org.carcv.core.model.Speed)}.
     */
    @Test
    public void testCompareTo() {
        Speed s1 = new Speed(80.2, SpeedUnit.KPH);        
        assertEquals(0, s1.compareTo(s1));
        
        Speed s2 = new Speed(80.2, SpeedUnit.KPH);
        assertEquals(0, s1.compareTo(s2));
        assertEquals(0, s2.compareTo(s1));
        
        s1.setId(1);
        s2.setId(2);
        
        assertEquals(0, s1.compareTo(s2));
        assertEquals(0, s2.compareTo(s1));
        
        s2 = new Speed(80.2, SpeedUnit.MPH);
        
        assertEquals(0, s1.compareTo(s2));
        assertEquals(0, s2.compareTo(s1));
        
        s1 = new Speed(80.15, SpeedUnit.KPH);
        s2 = new Speed(80.2, SpeedUnit.KPH);
        
        assertTrue(s1.compareTo(s2) < 0);
        assertTrue(s2.compareTo(s1) > 0);
    }

}
