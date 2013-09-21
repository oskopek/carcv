package org.carcv.servlet;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;

import org.carcv.beans.EntryBean;
import org.carcv.model.Entry;
import org.carcv.reports.BasicReportGenerator;

/**
 * Servlet implementation class GenerateReport
 */
@WebServlet("/servlet/GenerateReport")
public class GenerateReport extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@EJB
	private EntryBean entryBean;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GenerateReport() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, JRException {
    	response.setContentType("application/pdf");
		String filename = "report"
				+ System.currentTimeMillis() + ".pdf";
    	
		//If you want it to be downloadable, enable next line:
    	//response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
    	
    	//generate
    	long entryId = Long.parseLong(request.getParameter("entry_id"));
    	
    	Entry entry = entryBean.findById(entryId);
		
    	BasicReportGenerator brg = new BasicReportGenerator(entry, "/reports/speed_report.jasper", "Myjava", "TestReport");
    	
		brg.exportStream(filename, response.getOutputStream());
		
		
		
	}
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			processRequest(request, response);
		} catch (JRException e) {
			// TODO This shouldn't be here: security reasons
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			processRequest(request, response);
		} catch (JRException e) {
			// TODO This shouldn't be here: security reasons
			e.printStackTrace();
		}
	}

}
