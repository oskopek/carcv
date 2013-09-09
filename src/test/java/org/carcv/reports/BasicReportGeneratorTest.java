/**
 * 
 */
package org.carcv.reports;

import java.io.File;
import java.net.URL;
import java.util.Date;

import net.sf.jasperreports.engine.JRException;

import org.carcv.model.Address;
import org.carcv.model.CarData;
import org.carcv.model.Entry;
import org.carcv.model.LicencePlate;
import org.carcv.model.MediaObject;
import org.carcv.model.MediaType;
import org.carcv.model.Speed;
import org.carcv.model.SpeedUnit;
import org.junit.Before;
import org.junit.Test;

/**
 * @author oskopek
 *
 */
public class BasicReportGeneratorTest {

	private static Entry testEntry = null;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		URL previewImgUrl = getClass().getResource("/reports/OpenCV_Logo_with_text.png");
		
		MediaObject preview = new MediaObject(previewImgUrl.getPath(), MediaType.PNG);

		Speed speed = new Speed(80d, SpeedUnit.KPH);

		Address location = new Address("Myjava", "90701", "Jablonská", "Slovakia", 27, 860);

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
		URL testDir = getClass().getResource("/");
		
		File test_results_dir = new File(testDir.getPath() + "/test_results/");
		if (!test_results_dir.exists() || !test_results_dir.isDirectory()) {
			test_results_dir.mkdir();
		}
		System.out.println(test_results_dir.getPath());
		
		URL templateUrl = this.getClass().getResource("/reports/speed_report.jasper");	
		
		
		BasicReportGenerator.buildPDFReport(testEntry, templateUrl.getPath(), testDir.getPath() + "/test_results/report" + System.currentTimeMillis() + ".pdf", "Myjava", "TestReport");
	}

}
