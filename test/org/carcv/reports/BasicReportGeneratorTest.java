/**
 * 
 */
package org.carcv.reports;

import java.util.Date;

import net.sf.jasperreports.engine.JRException;

import org.carcv.model.Address;
import org.carcv.model.CarData;
import org.carcv.model.Entry;
import org.carcv.model.ILocation;
import org.carcv.model.LicencePlate;
import org.carcv.model.MediaObject;
import org.carcv.model.MediaType;
import org.carcv.model.Speed;
import org.carcv.model.SpeedUnit;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author oskopek
 *
 */
public class BasicReportGeneratorTest {
	
	private static Entry testEntry;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		MediaObject preview = new MediaObject("./res/reports/OpenCV_Logo_with_text.png", MediaType.PNG);
		
		Speed speed = new Speed(80d, SpeedUnit.KPH);
		
		Address add = new Address("Myjava", "90701", "Jablonská", "Slovakia", 27, 860);
		ILocation location = add;
		
		LicencePlate licencePlate = new LicencePlate("MY-077AU", "SK");
		
		Date timestamp = new Date(System.currentTimeMillis());
		
		MediaObject video = new MediaObject("test.com/video.h264", MediaType.H264);
		
		CarData carData = new CarData(speed, location, licencePlate, timestamp, video);
		
		testEntry = new Entry(carData, preview);
	}

	/**
	 * Test method for {@link org.carcv.reports.BasicReportGenerator#buildPDFReport(org.carcv.model.Entry, java.lang.String, java.lang.String, java.lang.String)}.
	 * @throws JRException 
	 */
	@Test
	public void testBuildPDFReport() throws JRException {
			BasicReportGenerator.buildPDFReport(testEntry, "./res/reports/speed_report.jasper", "./test_results/report" + System.currentTimeMillis() + ".pdf", "Myjava", "TestReport");
	}

}
