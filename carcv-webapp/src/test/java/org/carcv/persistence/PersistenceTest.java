/**
 * 
 */
package org.carcv.persistence;

import static org.junit.Assert.*;

import javax.inject.Inject;

import org.carcv.beans.SpeedBean;
import org.carcv.model.Speed;
import org.carcv.model.SpeedUnit;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author oskopek
 *
 */
@RunWith(Arquillian.class)
public class PersistenceTest {

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
            .addClass(SpeedBean.class)
            .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
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
