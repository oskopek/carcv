/**
 * 
 */
package org.carcv.persistence;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Date;

import javax.ejb.EJB;

import org.carcv.beans.AddressBean;
import org.carcv.beans.CarDataBean;
import org.carcv.beans.EntryBean;
import org.carcv.beans.LicencePlateBean;
import org.carcv.beans.MediaObjectBean;
import org.carcv.beans.SpeedBean;
import org.carcv.model.*;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
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

        WebArchive testArchive = ShrinkWrap.createFromZipFile(WebArchive.class,
                new File("target/carcv-webapp.war"));

        testArchive.delete("WEB-INF/classes/META-INF/persistence.xml");
        testArchive.addAsResource("META-INF/test-persistence.xml",
                "META-INF/persistence.xml");

        testArchive.delete("WEB-INF/jboss-web.xml");
        testArchive.addAsWebInfResource("WEB-INF/test-jboss-web.xml",
                "jboss-web.xml");

        testArchive.addAsResource("arquillian.xml");

        // testArchive.as(ZipExporter.class).exportTo(new
        // File("target/carcv-webapp-test.war"));

        return testArchive;
    }

    @EJB
    private AddressBean addressBean;
    @EJB
    private CarDataBean carDataBean;
    @EJB
    private LicencePlateBean licencePlateBean;
    @EJB
    private MediaObjectBean mediaObjectBean;
    @EJB
    private SpeedBean speedBean;
    @EJB
    private EntryBean entryBean;

    @Test
    public void persistenceTest() {
     // Entity code
        MediaObject preview = new MediaObject("reports/OpenCV_Logo_with_text.png", MediaType.PNG);

        Speed speed = new Speed(80d, SpeedUnit.KPH);

        Address address = new Address("Myjava", "90701", "Jablonská",
                "Slovakia", 27, 860);

        LicencePlate licencePlate = new LicencePlate("MY-077AU", "SK");

        Date timestamp = new Date(System.currentTimeMillis());

        MediaObject video = new MediaObject("http://test.com/video.h264",
                MediaType.H264);

        CarData carData = new CarData(speed, address, licencePlate, timestamp,
                video);

        Entry entry = new Entry(carData, preview);
        
        // End entity code
        
        addressBean.create(address);
        licencePlateBean.create(licencePlate);
        mediaObjectBean.create(preview, video);
        speedBean.create(speed);
        carDataBean.create(carData);
        entryBean.create(entry);
        
        assertEquals(preview, mediaObjectBean.getAll().get(0));
        assertEquals(video, mediaObjectBean.getAll().get(1));
        assertEquals(speed, speedBean.getAll().get(0));
        assertEquals(address, addressBean.getAll().get(0));
        assertEquals(licencePlate, licencePlateBean.getAll().get(0));
        
        CarData gotCarData = carDataBean.getAll().get(0);
        
        assertEquals(carData, gotCarData);
        assertEquals(timestamp, gotCarData.getTimestamp());        
        assertEquals(entry, entryBean.getAll().get(0));
    }

}
