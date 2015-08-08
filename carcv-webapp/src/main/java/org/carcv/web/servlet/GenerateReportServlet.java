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

import net.sf.jasperreports.engine.JRException;
import org.carcv.core.model.file.FileEntry;
import org.carcv.web.beans.EntryBean;
import org.carcv.web.reports.BasicReportGenerator;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.TimeZone;

/**
 * A Servlet that generates a report with {@link BasicReportGenerator} using the <code>entry_id</code> parameter.
 */
@WebServlet("/servlet/GenerateReport")
public class GenerateReportServlet extends HttpServlet {

    private static final long serialVersionUID = 107823642246194053L;

    @EJB
    private EntryBean entryBean;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws JRException, IOException {
        response.setContentType("application/pdf");
        String filename = "report" + System.currentTimeMillis() + ".pdf";

        // If you want it to be downloadable, enable next line:
        // response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");

        // generate
        long entryId = Long.parseLong(request.getParameter("entry_id"));
        FileEntry fileEntry = entryBean.findById(entryId);

        // Timezone
        String timeZoneStr = request.getParameter("timezone");
        TimeZone tz = TimeZone.getTimeZone(timeZoneStr);

        // host URL
        String hostURL = request.getScheme() + "://" + request.getServerName();

        BasicReportGenerator brg =
                new BasicReportGenerator(fileEntry, "/reports/speed_report.jasper", "Myjava", "TestReport", hostURL,
                        tz);
        brg.exportStream(filename, response.getOutputStream());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (JRException e) {
            response.sendError(500, e.getMessage());
            e.printStackTrace(); // debug
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (JRException e) {
            response.sendError(500, e.getMessage());
            e.printStackTrace(); // debug
        }
    }
}
