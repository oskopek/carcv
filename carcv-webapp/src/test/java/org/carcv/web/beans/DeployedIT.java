/**
 * 
 */
package org.carcv.web.beans;
import static org.junit.Assert.*;

import java.io.File;

import javax.ejb.EJB;

import org.carcv.web.beans.SpeedBean;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Test if the app deploys and EJB CDI Injection works.
 * @author oskopek
 *
 */
@RunWith(Arquillian.class)
public class DeployedIT {
    
      

    @Deployment
    public static WebArchive createDeployment() {
        
        WebArchive testArchive = ShrinkWrap
                .createFromZipFile(WebArchive.class, new File("target/carcv-webapp.war"));
        
        testArchive.delete("WEB-INF/classes/META-INF/persistence.xml");        
        testArchive.addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml");
        
        testArchive.delete("WEB-INF/jboss-web.xml");
        testArchive.addAsWebInfResource("WEB-INF/test-jboss-web.xml", "jboss-web.xml");
        
        testArchive.addAsResource("arquillian.xml");  
        
        //testArchive.as(ZipExporter.class).exportTo(new File("target/carcv-webapp-test.war"));
        
        return testArchive;
    }
    
    @EJB
    private SpeedBean speedBean;
    
    @Test
    public void beanInjectionTest() {
        assertNotNull("Failed to inject EJB speedBean", speedBean);
    }

}
