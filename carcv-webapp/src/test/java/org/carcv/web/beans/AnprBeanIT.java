/**
 * 
 */
package org.carcv.web.beans;
import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.ejb.EJB;

import org.carcv.web.beans.AnprBean;
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
public class AnprBeanIT {
    
      

    @Deployment
    public static WebArchive createDeployment() {
        
        WebArchive testArchive = ShrinkWrap
                .createFromZipFile(WebArchive.class, new File("target/carcv-webapp.war"));
        
        testArchive.delete("WEB-INF/classes/META-INF/persistence.xml");        
        testArchive.addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml");
        
        testArchive.delete("WEB-INF/jboss-web.xml");
        testArchive.addAsWebInfResource("WEB-INF/test-jboss-web.xml", "jboss-web.xml");
        
        testArchive.addAsResource("arquillian.xml"); 
        
        testArchive.addAsResource("img/skoda_oct.jpg");
        
        //testArchive.as(ZipExporter.class).exportTo(new File("target/carcv-webapp-test.war"));
        
        return testArchive;
    }
    
    @EJB
    private AnprBean anprBean;
    
    @Test
    public void licencePlateNumberRecognitionTest() throws IOException, Exception {
        assertNotNull(anprBean);

	Path filepath = Paths.get(getClass().getResource("/img/skoda_oct.jpg").toURI());
	
	assertNotNull("Resource doesn't exist: " + filepath);

        String licencePlate = anprBean.recognize(filepath);
        assertEquals("2SU358F", licencePlate); //actually should be 2SU3588
    }

}
