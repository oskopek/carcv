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
import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.carcv.web.beans.StorageBean;

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
    private StorageBean storageBean;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException,
        IOException, FileUploadException {
        // response.sendRedirect("/app/working.jsp"); // TODO 3 Do Something similar to this

        Path batchDir = storageBean.createBatchDirectory();

        List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
        for (FileItem item : items) {
            if (item.isFormField()) {
                // Process regular form field (input type="text|radio|checkbox|etc", select, etc).

                // String fieldName = item.getFieldName();
                // String fieldValue = item.getString();

                continue;

            } else {
                // Process form file field (input type="file").
                // String fieldName = item.getFieldName();
                String fileName = FilenameUtils.getName(item.getName());
                InputStream fileContent = item.getInputStream();

                storageBean.storeImageToDirectory(fileContent, fileName, batchDir);
            }
        }

        // recognizerBean.recognize(); // TODO 2 should we? probably no
        response.sendRedirect("/app/upload_complete.jsp"); // redirect to completed

    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (FileUploadException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (FileUploadException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}