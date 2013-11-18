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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

    private ExecutorService pool;

    @Override
    public void init() {
        pool = Executors.newFixedThreadPool(2);
    }

    /**
     * @param request the HttpServletRequest
     * @param response the HttpServletResponse
     * @throws IOException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        /*HttpSession session = request.getSession();

        Object uploadIdObj = session.getAttribute("uploadId");

        if (uploadIdObj != null) {
            Double d = (Double) uploadIdObj;
            if (d >= 100) {
                System.out.println("Another upload is already in progress!");
                return;
            }
        }

        pool.execute(new UploadRunnable(request, session, System.currentTimeMillis() + ""));

        response.sendRedirect("/servlet/UploadProgressServlet");*/
        Path batchDir = storageBean.createBatchDirectory();

        ServletFileUpload servletFileUpload = new ServletFileUpload(new DiskFileItemFactory());
        List<FileItem> items = null;
        try {
            items = servletFileUpload.parseRequest(request);
        } catch (FileUploadException e) {
            System.err.println("Error while parsing request to upload files!");
            e.printStackTrace();
        }
        
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

        processRequest(request, response);
    }

    /**
     * @see #processRequest(HttpServletRequest, HttpServletResponse)
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        processRequest(request, response);
    }

    class UploadRunnable implements Runnable {

        private final HttpServletRequest request;

        private final HttpSession session;

        private final String uploadId;

        public UploadRunnable(HttpServletRequest request, HttpSession session, String uploadId) {
            this.request = request;
            this.session = session;
            this.uploadId = uploadId;
        }

        @Override
        public void run() {
            try {
                session.setAttribute(uploadId, new Double(0d)); // 0%
                Path batchDir = storageBean.createBatchDirectory();

                ServletFileUpload servletFileUpload = new ServletFileUpload(new DiskFileItemFactory());
                List<FileItem> items = servletFileUpload.parseRequest(request);

                double counter = 0;
                final double size = items.size();
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

                    // update progress in percent
                    session.setAttribute(uploadId, new Double(((counter / size) * 100)));
                    counter++;
                }

                Path infoProps = Paths.get(batchDir.toString(), DirectoryLoader.infoFileName);
                if (!Files.exists(infoProps)) { // if the info file wasn't uploaded, generate a random demo one
                    Properties demo = Main.createDemoProperties();
                    Path props = Paths.get(batchDir.toString(), DirectoryLoader.infoFileName);
                    demo.store(Files.newOutputStream(props), "Demo properties");
                }
            } catch (Exception e) {
                System.err.println("Error occured while uploading files.");
                e.printStackTrace();
            }
        }
    }
}