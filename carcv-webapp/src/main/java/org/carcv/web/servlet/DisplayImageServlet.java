/*
 * Copyright 2013 CarCV Development Team
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
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.carcv.core.io.DirectoryLoader;

/**
 *
 */
@WebServlet("/servlet/DisplayImage")
public class DisplayImageServlet extends HttpServlet {

    private static final long serialVersionUID = 3756019811253496208L;

    /**
     * Remember to update the {@link DirectoryLoader#knownImageFileSuffixes} relative to this!
     *
     * @throws ServletException
     * @throws IOException
     * @see DirectoryLoader#knownImageFileSuffixes
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException,
        IOException {
        String path = request.getParameter("path");

        if (path == null) {
            response.sendError(400, "No valid \"path\" attribute supplied in request.");
            return;
        }

        String lowerCasePath = path.toLowerCase();
        if (lowerCasePath.endsWith(".jpg") || lowerCasePath.endsWith(".jpeg")) {
            response.setContentType("image/jpeg");
        } else if (lowerCasePath.endsWith(".png")) {
            response.setContentType("image/png");
        } else if (lowerCasePath.endsWith(".gif")) {
            response.setContentType("image/gif");
        } else if (lowerCasePath.endsWith(".bmp")) {
            response.setContentType("image/x-ms-bmp");
        } else {
            response.setContentType("application/octet-stream");
        }

        Path imagePath = Paths.get(path);
        if (!Files.exists(imagePath) || !Files.isRegularFile(imagePath)) {
            response.sendError(500, "Image at path " + path + " doesn't exist.");
            return;
        }

        OutputStream out = response.getOutputStream();
        Files.copy(imagePath, out);
        out.flush();
        return;
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     * @see #processRequest(HttpServletRequest, HttpServletResponse)
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     * @see #processRequest(HttpServletRequest, HttpServletResponse)
     * @throws ServletException
     * @throws IOException
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
        IOException {
        processRequest(request, response);
    }
}