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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;

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
import org.carcv.core.io.DirectoryLoader;
import org.carcv.impl.core.run.Main;
import org.carcv.web.beans.StorageBean;

/**
 * A Servlet that uploads images to a new batch input directory.
 */
@WebServlet("/servlet/UploadServlet")
public class UploadServlet extends HttpServlet {

    private static final long serialVersionUID = 8953603165824574044L;

    @EJB
    private StorageBean storageBean;

    /**
     * @param request the HttpServletRequest
     * @param response the HttpServletResponse
     * @throws IOException
     * @throws FileUploadException 
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, FileUploadException {
        Path batchDir = storageBean.createBatchDirectory();

        ServletFileUpload servletFileUpload = new ServletFileUpload(new DiskFileItemFactory());
        List<FileItem> items = servletFileUpload.parseRequest(request);
        
        for (FileItem item : items) {
            if (item.isFormField()) {
                // Process regular form field (input type="text|radio|checkbox|etc", select, etc).

                // String fieldName = item.getFieldName();
                // String fieldValue = item.getString();
            } else {
                // Process form file field (input type="file").
                // String fieldName = item.getFieldName();
                String fileName = FilenameUtils.getName(item.getName());
                InputStream fileContent = item.getInputStream();

                storageBean.storeToDirectory(fileContent, fileName, batchDir);
            }
        }

        Path infoProps = Paths.get(batchDir.toString(), DirectoryLoader.infoFileName);
        if (!Files.exists(infoProps)) { // if the info file wasn't uploaded, generate a random demo one
            Properties demo = Main.createDemoProperties();
            Path props = Paths.get(batchDir.toString(), DirectoryLoader.infoFileName);
            demo.store(Files.newOutputStream(props), "Demo properties");
        }
    }

    /**
     * @see #processRequest(HttpServletRequest, HttpServletResponse)
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
     * @see #processRequest(HttpServletRequest, HttpServletResponse)
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