/**
 * 
 */
package org.carcv.persistence;

import static org.junit.Assert.*;

import java.io.File;

import javax.inject.Inject;

import org.carcv.beans.SpeedBean;
import org.carcv.model.Speed;
import org.carcv.model.SpeedUnit;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.exporter.ZipExporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author oskopek
 *
 */
@RunWith(Arquillian.class)
public class PersistenceIT {
    
      

    @Deployment
    public static WebArchive createDeployment() {
        
        WebArchive testArchive = ShrinkWrap
                .createFromZipFile(WebArchive.class, new File("target/carcv-webapp.war"));
        
        testArchive.delete("WEB-INF/classes/META-INF/persistence.xml");        
        testArchive.addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml");
        
        testArchive.addAsResource("arquillian.xml");  
        
        testArchive.as(ZipExporter.class).exportTo(new File("target/carcv-webapp-test.war"));
        
        return testArchive;
    }
    
    @Inject
    private SpeedBean speedBean;
    
    @Test
    public void speedBeanPersistenceTest() {
        Speed s1 = new Speed(80.2, SpeedUnit.KPH);
        
        
        speedBean.create(s1);
        Speed s2 = speedBean.findById(1);
        
        assertEquals(s1, s2);
        
        
    }

}
