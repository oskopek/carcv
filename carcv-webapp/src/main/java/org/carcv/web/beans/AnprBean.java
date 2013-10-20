/**
 * 
 */
package org.carcv.web.beans;

import java.io.IOException;
import java.io.InputStream;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import net.sf.javaanpr.imageanalysis.CarSnapshot;
import net.sf.javaanpr.intelligence.Intelligence;

/**
 * @author oskopek
 *
 */
@Stateless
public class AnprBean {
	
    private static Intelligence intel;
    
    @PostConstruct
    public void init() {
    	try {
            intel = new Intelligence();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }
    
    public String recognize(InputStream is) throws IOException, Exception {
        String lp = "";
        lp = intel.recognize(new CarSnapshot(is));
        return lp;
    }
    

}
