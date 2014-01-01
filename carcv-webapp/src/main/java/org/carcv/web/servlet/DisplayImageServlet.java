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

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.carcv.core.io.DirectoryLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
@WebServlet("/servlet/DisplayImage")
public class DisplayImageServlet extends HttpServlet {

    final private static Logger LOGGER = LoggerFactory.getLogger(DisplayImageServlet.class);

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

        String width = request.getParameter("width");
        String height = request.getParameter("height");

        Path imagePath = imagePath(path, height, width);

        if (imagePath == null) {
            response.sendError(500, "Image at path " + path + " doesn't exist.");
            return;
        }

        OutputStream out = response.getOutputStream();
        Files.copy(imagePath, out);
        out.flush();
    }

    /**
     * Return the path of the image, or resize and return the path of the resized one.
     *
     * @param path the request attribute of the path to load the image from
     * @param height the request attribute of the wanted height of load the image
     * @param width the request attribute of the wanted width of the image
     * @return the path of the image according to the parameters, or null if the image at path doesn't exist
     * @throws IOException
     */
    protected static Path imagePath(String path, String height, String width) throws IOException {
        Path imagePath = Paths.get(path);
        LOGGER.debug("Loading image at: {}", imagePath.toString());
        if (!Files.exists(imagePath) || !Files.isRegularFile(imagePath)) {
            return null;
        }

        Path returnPath;

        if (height == null && width == null) {
            LOGGER.debug("Returning path straight away.");
            returnPath = imagePath;
        } else if (height != null) {
            BufferedImage bi = ImageIO.read(imagePath.toFile());
            int heightInt = Integer.parseInt(height);
            float rate = (float) heightInt / (float) bi.getHeight();
            int widthInt = Math.round(bi.getWidth() * rate);
            LOGGER.debug("I know the height {} and calculated the width {}.", heightInt, widthInt);
            returnPath = imagePathResize(imagePath, heightInt, widthInt);
        } else if (width != null) {
            BufferedImage bi = ImageIO.read(imagePath.toFile());
            int widthInt = Integer.parseInt(width);
            float rate = (float) widthInt / (float) bi.getWidth();
            int heightInt = Math.round(bi.getHeight() * rate);
            LOGGER.debug("I know the width {} and calculated the height {}.", widthInt, heightInt);
            returnPath = imagePathResize(imagePath, heightInt, widthInt);
        } else {
            LOGGER.debug("I know the width {} and the height {}.", Integer.parseInt(width), Integer.parseInt(height));
            returnPath = imagePathResize(imagePath, Integer.parseInt(height), Integer.parseInt(width));
        }

        LOGGER.debug("Returning: {}", returnPath.toString());
        return returnPath;
    }

    /**
     * Resizes an image and returns the path of the resized image.
     *
     * @param imagePath path of the original image
     * @param height height of the image to resize it to
     * @param width width of the image to resize it to
     * @return the path of the resized image
     * @throws IOException if an error during load/write of the image occurs
     */
    private static Path imagePathResize(Path imagePath, int height, int width) throws IOException {
        String filename = imagePath.getFileName().toString();
        int dot = filename.indexOf('.');

        LOGGER.debug("Filename: {} and dot index: {}", filename, dot);
        LOGGER.debug("Name: {}, Suffix: {}", filename.substring(0, dot), filename.subSequence(dot + 1, filename.length()));
        Path resizedImagePath = Paths.get(imagePath.getParent().toString(),
            filename.substring(0, dot) + "_" + width + "x" + height + filename.substring(dot + 1, filename.length()));
        LOGGER.debug("ResizedPath: {}", resizedImagePath.toString());

        if (Files.exists(resizedImagePath)) { // if file already exists, return it's path
            LOGGER.debug("The ResizedPath already exists.");
            return resizedImagePath;
        }

        BufferedImage originalImage = ImageIO.read(imagePath.toFile());
        int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();

        BufferedImage resizedImage = new BufferedImage(width, height, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, width, height, null);
        g.dispose();

        ImageIO.write(resizedImage, filename.substring(dot + 1, filename.length()), resizedImagePath.toFile());

        return resizedImagePath;
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