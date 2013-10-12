/**
 * 
 */
package org.carcv.beans;

import java.io.IOException;
import java.io.InputStream;

import javax.ejb.Stateless;

import net.sf.javaanpr.imageanalysis.CarSnapshot;
import net.sf.javaanpr.intelligence.Intelligence;

/**
 * @author oskopek
 *
 */
@Stateless
public class AnprBean {
	
    private static Intelligence intel = new Intelligence();
    
    public String recognize(InputStream is) {
        String lp = "";
            try {
                lp = intel.recognize(new CarSnapshot(is));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        return lp;
    }
    

}
