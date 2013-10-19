package org.carcv.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.carcv.core.model.MediaObject;
import org.carcv.core.model.MediaType;
import org.carcv.web.beans.MediaObjectBean;

/**
 * Servlet implementation class TestServlet
 */
@WebServlet("/test/TestServlet")
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	 @EJB
	 private MediaObjectBean bean;
	 
	   protected void processRequest(HttpServletRequest request, HttpServletResponse response)
	                  throws ServletException, IOException {
	       PrintWriter out = response.getWriter();
	       
	       MediaObject m1 = new MediaObject("http://test.com/test.h264", MediaType.H264);
	       MediaObject m2 = new MediaObject("http://test.com/test.jpeg", MediaType.JPEG);
	       
	       bean.create(m1, m2);
	       out.println("Created and persisted " + m1.toString() + ", and " + m2.toString());
	 
	       /* mo = bean.findById(0);
	       out.println("Query returned: " + mo.toString());
	       */
	       
	       MediaObject mo = bean.findById(m1.getId());
	       out.println("Query returned: " + mo.toString());
	       
	       mo = bean.findById(m2.getId());
	       out.println("Query returned: " + mo.toString());
	       
	       List<MediaObject> moList = bean.getAll();
	       out.println();
	       out.println("List:\n " + moList.toString());
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
