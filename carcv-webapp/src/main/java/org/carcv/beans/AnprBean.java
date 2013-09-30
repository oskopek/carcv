/**
 * 
 */
package org.carcv.beans;

import java.io.IOException;
import java.io.InputStream;

import javaanpr.imageanalysis.CarSnapshot;
import javaanpr.intelligence.Intelligence;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;

/**
 * @author oskopek
 *
 */
@Stateless
public class AnprBean {
    private static Intelligence intel;
    
    @PostConstruct
    private void init() {
        try {
            AnprBean.intel = new Intelligence(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
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
