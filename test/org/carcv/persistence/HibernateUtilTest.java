/**
 * 
 */
package org.carcv.persistence;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.carcv.model.Address;
import org.carcv.model.CarData;
import org.carcv.model.Entry;
import org.carcv.model.LicencePlate;
import org.carcv.model.MediaObject;
import org.carcv.model.MediaType;
import org.carcv.model.Speed;
import org.carcv.model.SpeedUnit;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author oskopek
 *
 */
public class HibernateUtilTest {

	SessionFactory testSf;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		
		testSf = HibernateUtil.buildSessionFactory("/resources/hibernate_unittest.cfg.xml");
		
		
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		testSf.close();
	}

	@Test
	public void test() throws InterruptedException {
		Session session = testSf.openSession();
		
		Transaction tx = session.beginTransaction();
		tx.setTimeout(5);
		
		//Entity code
		MediaObject preview = new MediaObject("/resources/reports/OpenCV_Logo_with_text.png", MediaType.PNG);

		Speed speed = new Speed(80d, SpeedUnit.KPH);

		Address location = new Address("Myjava", "90701", "Jablonská", "Slovakia", 27, 860);

		LicencePlate licencePlate = new LicencePlate("MY-077AU", "SK");

		Date timestamp = new Date(System.currentTimeMillis());

		MediaObject video = new MediaObject("test.com/video.h264", MediaType.H264);

		CarData carData = new CarData(speed, location, licencePlate, timestamp, video);

		Entry testEntry = new Entry(carData, preview);		
		//End entity code
		session.saveOrUpdate(preview);
		session.saveOrUpdate(speed);
		session.saveOrUpdate(location);
		session.saveOrUpdate(licencePlate);
		session.saveOrUpdate(video);
		session.saveOrUpdate(carData);
		session.saveOrUpdate(testEntry);
		
		System.out.println("Saved");
		
		tx.commit();
		System.out.println("Commited");
		
		long entry_id = testEntry.getId();
		System.out.println("Id=" + entry_id);
		
		assertTrue(tx.wasCommitted());
		
		
		//now retrieve
		
		Query query = session.createQuery("from Entry where id = :entryid");
		query.setParameter("entryid", entry_id);
		
		@SuppressWarnings("unchecked")
		List<Entry> list = query.list();
		
		Entry entryFromDB = list.get(0);
		
		assertEquals(testEntry, entryFromDB);
		
		session.close();
	}

}
