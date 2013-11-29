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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.carcv.core.model.file.FileCarImage;
import org.carcv.core.model.file.FileEntry;
import org.carcv.web.beans.EntryBean;
import org.carcv.web.reports.WebReportTableMember;

/**
 * A Servlet that retrieves all FileEntries from the database and formats them into a nice table.
 */
@WebServlet("/servlet/CarTable")
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
        final ArrayList<FileEntry> requestEntries = (ArrayList<FileEntry>) bean.getAll();

        @SuppressWarnings("unchecked")
        ArrayList<FileEntry> abstractEntries = (ArrayList<FileEntry>) requestEntries.clone();

        DateFormat dateFormat = new SimpleDateFormat("dd. MM. yyyy");
        DateFormat timeFormat = new SimpleDateFormat("HH:mm");

        String date, time, entryId, licensePlate, location, previewURL;
        ArrayList<WebReportTableMember> wrtmList = new ArrayList<>();

        for (FileEntry e : abstractEntries) {
            // initialize
            date = dateFormat.format(e.getCarData().getTimestamp());
            time = timeFormat.format(e.getCarData().getTimestamp());

            entryId = e.getId().toString();

            licensePlate = e.getCarData().getNumberPlate().getOrigin() + ": "
                + e.getCarData().getNumberPlate().getText();

            location = e.getCarData().getAddress().printBR();

            FileCarImage fci = e.getCarImages().get(0);
            previewURL = "/servlet/DisplayImage?path=" + fci.getFilepath().toString();

            WebReportTableMember wrtm = new WebReportTableMember(previewURL, entryId, time, date, location, licensePlate);

            wrtmList.add(wrtm);
        }

        request.setAttribute("wrtmList", wrtmList);
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/app/cartable.jsp");
        rd.forward(request, response);
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