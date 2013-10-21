/**
 * 
 */
package org.carcv.web.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.nio.file.Paths;
import java.util.Date;

import javax.ejb.EJB;

import org.carcv.core.model.Address;
import org.carcv.core.model.CarData;
import org.carcv.core.model.NumberPlate;
import org.carcv.core.model.Speed;
import org.carcv.core.model.SpeedUnit;
import org.carcv.core.model.file.FileCarImage;
import org.carcv.core.model.file.FileEntry;
import org.carcv.web.beans.AddressBean;
import org.carcv.web.beans.CarDataBean;
import org.carcv.web.beans.CarImageBean;
import org.carcv.web.beans.EntryBean;
import org.carcv.web.beans.NumberPlateBean;
import org.carcv.web.beans.SpeedBean;
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
    private NumberPlateBean numberPlateBean;
    @EJB
    private CarImageBean carImageBean;
    @EJB
    private SpeedBean speedBean;
    @EJB
    private EntryBean entryBean;

    @Test
    public void persistenceTest() {
     // Entity code

        Speed speed = new Speed(80d, SpeedUnit.KPH);

        Address address = new Address("Myjava", "90701", "Jablonská",
                "Slovakia", 27, 860);

        NumberPlate licencePlate = new NumberPlate("MY-077AU", "SK");

        Date timestamp = new Date(System.currentTimeMillis());

        FileCarImage carImage = new FileCarImage(Paths.get("/tmp/test/video.h264"));

        CarData carData = new CarData(speed, address, licencePlate, timestamp);

        FileEntry abstractEntry = new FileEntry(carData, carImage);
        
        // End entity code
        
        assertNotNull(addressBean);
        assertNotNull(numberPlateBean);
        assertNotNull(carImageBean);
        assertNotNull(speedBean);
        assertNotNull(carDataBean);
        assertNotNull(entryBean);
        
        addressBean.create(address);
        numberPlateBean.create(licencePlate);
        carImageBean.create(carImage);
        speedBean.create(speed);
        carDataBean.create(carData);
        entryBean.create(abstractEntry);
        
        assertEquals(carImage, carImageBean.getAll().get(0));
        assertEquals(speed, speedBean.getAll().get(0));
        assertEquals(address, addressBean.getAll().get(0));
        assertEquals(licencePlate, numberPlateBean.getAll().get(0));
        
        CarData gotCarData = carDataBean.getAll().get(0);
        
        assertEquals(carData, gotCarData);
        assertEquals(timestamp, gotCarData.getTimestamp());        
        assertEquals(abstractEntry, entryBean.getAll().get(0));
    }

}
