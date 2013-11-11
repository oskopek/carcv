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

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.carcv.core.model.file.FileEntry;
import org.carcv.impl.core.io.FFMPEG_VideoHandler;
import org.carcv.web.beans.EntryBean;

/**
 * A Servlet that generates a video from a list of images using
 * {@link FFMPEG_VideoHandler#generateVideoAsStream(FileEntry, java.io.OutputStream)}.
 */
@WebServlet("/servlet/GenerateVideoServlet")
public class GenerateVideoServlet extends HttpServlet {

    private static final long serialVersionUID = 6662253349108285886L;

    @EJB
    private EntryBean entryBean;

    /**
     *
     * @param request the HttpServletRequest
     * @param response the HttpServletResponse
     * @throws IOException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("video/h264");
        String filename = "video-" + System.currentTimeMillis() + ".h264";

        // If you want it to be downloadable, enable next line:
        response.setHeader("Content-Disposition", "attachment; filename=" + filename);

        // generate
        long entryId = Long.parseLong(request.getParameter("entry_id"));
        FileEntry entry = entryBean.findById(entryId);

        FFMPEG_VideoHandler.generateVideoAsStream(entry, response.getOutputStream());
    }

    /**
     * @see #processRequest(HttpServletRequest, HttpServletResponse)
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        processRequest(request, response);
    }

    /**
     * @see #processRequest(HttpServletRequest, HttpServletResponse)
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        processRequest(request, response);
    }
}