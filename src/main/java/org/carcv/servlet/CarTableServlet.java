package org.carcv.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.carcv.model.Entry;
import org.carcv.persistence.HibernateUtil;
import org.hibernate.Session;

/**
 * Servlet implementation class CarTableServlet
 */
public class CarTableServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CarTableServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		Session session = HibernateUtil.getSessionFactory().openSession();

		@SuppressWarnings("unchecked")
		ArrayList<Entry> entries = (ArrayList<Entry>) session.createQuery(
				"from Entry").list();

		// write page
		PrintWriter out = response.getWriter();

		out.write("<!DOCTYPE html>");
		out.write("<html>");
		out.write("<head>");
		out.write("<meta charset=\"UTF-8\">");
		out.write("<title>Car database</title>");
		out.write("<style type=\"text/css\">");
		out.write("#table {");
		out.write("	text-align: center;");
		out.write("}");
		out.write("</style>");
		out.write("</head>");

		out.write("<body>");
		out.write("<table style=\"border: 1px solid #C0C0C0;\">");
		out.write("<tr>");
		out.write("<th style=\"width: 160px; height: 15px; background-color: #B0C4DE;\">Car preview</th>");
		out.write("<th style=\"width: 10%; height: 15px; background-color: #B0C4DE;\">Date</th>");
		out.write("<th style=\"width: 15%; height: 15px; background-color: #B0C4DE;\">Licence plate</th>");
		out.write("<th style=\"width: 20%; height: 15px; background-color: #B0C4DE;\">Location</th>");
		out.write("<th style=\"width: 20%; height: 15px; background-color: #B0C4DE;\">Video</th>");
		out.write("<th style=\"width: 20%; height: 15px; background-color: #B0C4DE;\">Pictures</th>");
		out.write("<th style=\"width: 15%; height: 15px; background-color: #B0C4DE;\">Report</th>");
		out.write("</tr>");

		DateFormat dateFormat = new SimpleDateFormat("dd. MM. yyyy");
		DateFormat timeFormat = new SimpleDateFormat("HH:mm");

		String date, time;
		String licencePlate;
		String location;
		String videoURL;
		String previewURL;

		for (Entry e : entries) {
			// initialize
			date = dateFormat.format(e.getData().getTimestamp());
			time = timeFormat.format(e.getData().getTimestamp());
			licencePlate = e.getData().getLicencePlate().getOrigin() + " - "
					+ e.getData().getLicencePlate().getOrigin();
			location = e.getData().getLocation().toString();
			videoURL = e.getData().getVideo().getURL();
			previewURL = e.getPreview().getURL();

			// write
			out.write("<tr>");
			out.write("<td style=\"\"><img");
			out.write("src=\"" + previewURL + "\"");
			out.write("style=\"border: 2px\" width=\"150\" alt=\"Car\"></td>");
			out.write("<td>" + date + "\n" + time + "</td>");
			out.write("<td>" + licencePlate + "</td>");
			out.write("<td>" + location + "</td>");
			out.write("<td><a href=\"" + videoURL + "\" target=\"_top\">View video</a></td>");
			out.write("<td><a href=\"" + previewURL
					+ "\" target=\"_top\">View pictures</a></td>");
			out.write("<td><a href=\"" + "/servlet/GenerateReport"
					+ "\" target=\"_top\">Generate report</a></td>");
			out.write("</tr>");
		}

		out.write("</table>");
		out.write("</body>");
		out.write("</html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
