/**
 * 
 */
package org.carcv.beans;

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
    
    public String getLicencePlate(String imgPath) {
        String lp = "";
        try {
            lp = intel.recognize(new CarSnapshot(imgPath));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            lp = "N/A";
        }
        return lp;
    }
    

}
