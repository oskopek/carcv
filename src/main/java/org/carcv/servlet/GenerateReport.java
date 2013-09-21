package org.carcv.servlet;

import java.io.File;
import java.io.IOException;
import java.net.URL;

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
    	long entryId = Long.parseLong(request.getParameter("entry_id"));
    	
    	Entry entry = entryBean.findById(entryId);
    	
    	//need this to load the image from disk
    	URL previewImgUrl = getClass().getResource(entry.getPreview().getURL());

		entry.getPreview().setURL(previewImgUrl.getPath());
		
		//generate
		URL testDir = this.getClass().getResource("/");

		File test_results_dir = new File(testDir.getPath() + "/test_results/");
		if (!test_results_dir.exists() || !test_results_dir.isDirectory()) {
			test_results_dir.mkdir();
		}
		System.out.println("results dir: " + test_results_dir.getPath());

		URL templateUrl = this.getClass().getResource(
				"/reports/speed_report.jasper");
		
		File templateFile = new File(templateUrl.getFile());
		
		
		System.out.println("reports template: " + templateUrl.getPath());
		System.out.println("reports template: " + templateUrl.getFile());
		System.out.println("reports template: file: " + templateFile.getAbsolutePath());

		BasicReportGenerator.buildPDFReport(
				entry,
				templateUrl.getFile(),
				testDir + "/test_results/report"
						+ System.currentTimeMillis() + ".pdf", "Myjava",
				"TestReport");
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
