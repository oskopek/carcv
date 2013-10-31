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

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.carcv.web.beans.RecognizerBean;

/**
 *
 */
@WebServlet("/servlet/UploadServlet")
public class UploadServlet extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = 8953603165824574044L;

    @EJB
    private RecognizerBean recognizerBean;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException,
        IOException {
        response.sendRedirect("/app/working.jsp"); // TODO 2 Will this work?

        // TODO 1 Upload all files from ?directory=

        recognizerBean.recognize(); // should we? probably yes
        response.sendRedirect(request.getHeader("referer")); // redirect back where you came from

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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
}