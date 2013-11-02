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
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.carcv.core.model.Address;
import org.carcv.core.model.CarData;
import org.carcv.core.model.NumberPlate;
import org.carcv.core.model.Speed;
import org.carcv.core.model.SpeedUnit;
import org.carcv.core.model.file.FileCarImage;
import org.carcv.core.model.file.FileEntry;
import org.carcv.web.beans.*;

/**
 * Servlet implementation class AddEntryServlet
 */
@WebServlet("/test/AddEntryServlet")
public class AddEntryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

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
     * @throws ServletException
     * @throws IOException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException,
        IOException {
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();

        // Entity code
        Speed speed = new Speed(80d, SpeedUnit.KPH);

        Address address = new Address("Myjava", "90701", "Jablonská", "Slovakia", 27, 860);

        NumberPlate licencePlate = new NumberPlate("MY-077AU", "SK");

        Date timestamp = new Date(System.currentTimeMillis());

        // MediaObject video = new MediaObject("http://test.com/video.h264", MediaType.H264);

        CarData carData = new CarData(speed, address, licencePlate, timestamp);

        FileEntry testEntry = new FileEntry(carData, Arrays.asList(new FileCarImage(Paths.get("/tmp/test/image.jpg"))));

        // End entity code
        out.println(testEntry.toString());

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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
        IOException {
        processRequest(request, response);
    }

}