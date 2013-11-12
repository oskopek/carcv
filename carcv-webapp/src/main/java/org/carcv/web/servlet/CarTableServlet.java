/*
 * Copyright 2012 CarCV Development Team
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.carcv.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.carcv.core.model.file.FileCarImage;
import org.carcv.core.model.file.FileEntry;
import org.carcv.web.beans.EntryBean;

/**
 * A Servlet that retrieves all FileEntries from the database and formats them into a nice table.
 */
@WebServlet("/servlet/CarTableServlet")
public class CarTableServlet extends HttpServlet {

    private static final long serialVersionUID = 650302178430670688L;

    @EJB
    private EntryBean bean;

    /**
     * @see CarTableServlet
     * @param request the HttpServletRequest
     * @param response the HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException,
        IOException {
        ArrayList<FileEntry> abstractEntries = (ArrayList<FileEntry>) bean.getAll();

        // write page
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset=\"UTF-8\">");
        out.println("<title>CarCV - Car Table</title>");
        out.println("<style type=\"text/css\">");
        out.println("#table {");
        out.println("	text-align: center;");
        out.println("}");
        out.println("</style>");
        out.println("</head>");

        out.println("<body>");
        out.println("<table style=\"border: 1px solid #C0C0C0;\">");
        out.println("<tr>");
        out.println("<th style=\"width: 160px; height: 15px; background-color: #B0C4DE;\">Car preview</th>");
        out.println("<th style=\"width: 10%; height: 15px; background-color: #B0C4DE;\">Date</th>");
        out.println("<th style=\"width: 15%; height: 15px; background-color: #B0C4DE;\">Licence plate</th>");
        out.println("<th style=\"width: 20%; height: 15px; background-color: #B0C4DE;\">Location</th>");
        out.println("<th style=\"width: 15%; height: 15px; background-color: #B0C4DE;\">Video</th>");
        out.println("<th style=\"width: 15%; height: 15px; background-color: #B0C4DE;\">Pictures</th>");
        out.println("<th style=\"width: 15%; height: 15px; background-color: #B0C4DE;\">Report</th>");
        out.println("<th style=\"width: 10%; height: 15px; background-color: #B0C4DE;\">Delete</th>");
        out.println("</tr>");

        DateFormat dateFormat = new SimpleDateFormat("dd. MM. yyyy");
        DateFormat timeFormat = new SimpleDateFormat("HH:mm");

        String date, time;
        String licencePlate;
        String location;
        String videoURL;
        String previewURL;

        for (FileEntry e : abstractEntries) {
            // initialize
            date = dateFormat.format(e.getCarData().getTimestamp());
            time = timeFormat.format(e.getCarData().getTimestamp());
            licencePlate = e.getCarData().getNumberPlate().getOrigin() + ": "
                + e.getCarData().getNumberPlate().getText();
            location = e.getCarData().getAddress().printBR();

            FileCarImage fci = e.getCarImages().get(0);
            previewURL = fci.getFilepath().toString();

            videoURL = "/servlet/GenerateVideoServlet?entry_id=" + e.getId();

            // write
            out.println("<tr>");
            out.println("<td style=\"\"><img");
            out.println("src=\"" + previewURL + "\"");
            out.println("style=\"border: 2px\" width=\"150\" alt=\"Car\"></td>");
            out.println("<td>" + date + "\n" + time + "</td>");
            out.println("<td>" + licencePlate + "</td>");
            out.println("<td>" + location + "</td>");
            out.println("<td><a href=\"" + videoURL + "\" target=\"_top\">View video</a></td>");
            out.println("<td><a href=\"" + previewURL + "\" target=\"_top\">View preview</a></td>");
            out.println("<td><a href=\"" + "/servlet/GenerateReport?entry_id=" + e.getId()
                + "\" target=\"_top\">Generate report</a></td>");
            out.println("<td><a href=\"" + "/servlet/RemoveServlet?entry_id=" + e.getId()
                + "\" target=\"_top\">Delete" + "</a></td>");
            out.println("</tr>");
        }

        out.println("</table>");
        out.println("</body>");
        out.println("</html>");
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     * @see #processRequest(HttpServletRequest, HttpServletResponse)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     * @see #processRequest(HttpServletRequest, HttpServletResponse)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
        IOException {
        processRequest(request, response);
    }
}