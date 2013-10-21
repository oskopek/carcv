package org.carcv.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.Date;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.carcv.core.model.Address;
import org.carcv.core.model.CarData;
import org.carcv.core.model.Entry;
import org.carcv.core.model.NumberPlate;
import org.carcv.core.model.Speed;
import org.carcv.core.model.SpeedUnit;
import org.carcv.core.model.file.FileCarImage;
import org.carcv.web.beans.*;
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
	private NumberPlateBean numberPlateBean;
	//@EJB
	//private MediaObjectBean mediaObjectBean;
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
    			Speed speed = new Speed(80d, SpeedUnit.KPH);

    			Address address = new Address("Myjava", "90701", "Jablonská",
    					"Slovakia", 27, 860);

    			NumberPlate licencePlate = new NumberPlate("MY-077AU", "SK");

    			Date timestamp = new Date(System.currentTimeMillis());

    			//MediaObject video = new MediaObject("http://test.com/video.h264", MediaType.H264);

    			CarData carData = new CarData(speed, address, licencePlate, timestamp);

    			Entry testEntry = new Entry(carData, new FileCarImage(Paths.get("/tmp/test/video.h264")));
    			
    			// End entity code
    			out.println(testEntry.toString());
    			
    			addressBean.create(address);
    			numberPlateBean.create(licencePlate);
    			//mediaObjectBean.create(video);
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
