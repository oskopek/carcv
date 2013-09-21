package org.carcv.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.carcv.beans.*;
import org.carcv.model.Address;
import org.carcv.model.CarData;
import org.carcv.model.Entry;
import org.carcv.model.LicencePlate;
import org.carcv.model.MediaObject;
import org.carcv.model.MediaType;
import org.carcv.model.Speed;
import org.carcv.model.SpeedUnit;
/**
 * Servlet implementation class AddEntryServlet
 */
@WebServlet("/test/AddEntryServlet")
public class AddEntryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
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
	
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddEntryServlet() {
        super();
    }
    
    /**
     * @see HibernateUtilTest#saveAndQueryTest()
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    			response.setContentType("text/plain");
    			PrintWriter out = response.getWriter();
    	
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

    			Entry testEntry = new Entry(carData, preview);
    			
    			// End entity code
    			out.println(testEntry.toString());
    			
    			addressBean.create(address);
    			licencePlateBean.create(licencePlate);
    			mediaObjectBean.create(preview, video);
    			speedBean.create(speed);
    			carDataBean.create(carData);
    			entryBean.create(testEntry);

    			out.println("Saved");
    			out.println(testEntry.toString());

    			long entry_id = testEntry.getId();
    			out.println("Id=" + entry_id);

    			// now retrieve
    			out.println(entryBean.findById(entry_id).toString());

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);	
	}

}
