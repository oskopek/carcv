/*
 * Copyright 2013-2014 CarCV Development Team
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

import org.carcv.core.model.file.FileEntry;
import org.carcv.web.beans.EntryBean;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * A Servlet that removes FileEntries from the database.
 */
@WebServlet("/admin/servlet/RemoveEntry")
public class RemoveEntryServlet extends HttpServlet {

    private static final long serialVersionUID = 6813706341486318124L;

    @EJB
    private EntryBean entryBean;

    /**
     * Removes the FileEntry with id in the <code>entry_id</code> request parameter from the database.
     *
     * @throws IOException
     * @see EntryBean#remove(FileEntry...)
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws
            IOException {
        String[] entryIdArray = request.getParameter("entry_id").split(",");
        for (String entryId : entryIdArray) {
            entryBean.remove(Long.parseLong(entryId));
        }
        response.sendRedirect("/app/index.jsp"); // TODO 3 redirect should be more intelligent
    }

    /**
     * @throws ServletException
     * @throws IOException
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     * @see #processRequest(HttpServletRequest, HttpServletResponse)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * @throws ServletException
     * @throws IOException
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     * @see #processRequest(HttpServletRequest, HttpServletResponse)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        processRequest(request, response);
    }
}