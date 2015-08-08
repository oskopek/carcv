package org.carcv.web.test;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;

import java.io.File;

public abstract class AbstractIT {

    public static WebArchive createGenericDeployment() {
        WebArchive testArchive = ShrinkWrap.createFromZipFile(WebArchive.class, new File("target/carcv-webapp.war"));
        testArchive.delete("WEB-INF/classes/META-INF/persistence.xml");
        testArchive.addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml");
        testArchive.delete("WEB-INF/jboss-web.xml");
        testArchive.addAsWebInfResource("WEB-INF/test-jboss-web.xml", "jboss-web.xml");
        testArchive.addAsResource("arquillian.xml");
        return testArchive;
    }

}
