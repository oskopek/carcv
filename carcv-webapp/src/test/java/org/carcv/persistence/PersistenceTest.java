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
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.DependencyResolvers;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependencyResolver;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author oskopek
 *
 */
@RunWith(Arquillian.class)
public class PersistenceTest {
    
    private static MavenDependencyResolver resolver = DependencyResolvers.use(MavenDependencyResolver.class).loadMetadataFromPom("pom.xml");  

    @Deployment
    public static WebArchive createDeployment() {
        WebArchive testWAR = ShrinkWrap.create(WebArchive.class, "test.war")
                .addClasses(Speed.class, SpeedBean.class, SpeedUnit.class)
                .addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, ArchivePaths.create("beans.xml"))
                .addAsLibraries(resolver.artifact("org.hibernate:hibernate-core").resolveAsFiles())
                .addAsLibraries(resolver.artifact("org.hsqldb:hsqldb").resolveAsFiles());
        
        System.out.println(testWAR.toString(true));
        return testWAR;
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
