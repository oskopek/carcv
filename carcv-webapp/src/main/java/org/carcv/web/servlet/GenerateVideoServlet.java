/**
 * 
 */
package org.carcv.web.servlet;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.carcv.core.model.file.FileEntry;
import org.carcv.impl.core.input.FFMPEG_VideoHandler;
import org.carcv.web.beans.EntryBean;

/**
 * @author oskopek
 * 
 */
@WebServlet("/servlet/GenerateVideoServlet")
public class GenerateVideoServlet extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = 6662253349108285886L;

    @EJB
    private EntryBean entryBean;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("video/h264");
        String filename = "video-" + System.currentTimeMillis() + ".h264";

        //If you want it to be downloadable, enable next line:
        response.setHeader("Content-Disposition", "attachment; filename=" + filename);

        //generate
        long entryId = Long.parseLong(request.getParameter("entry_id"));

        FileEntry entry = entryBean.findById(entryId);

        FFMPEG_VideoHandler.generateVideoAsStream(entry, response.getOutputStream());

    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        processRequest(request, response);
    }

    /**
     * @throws IOException 
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException  {
        processRequest(request, response);
    }
    
}
