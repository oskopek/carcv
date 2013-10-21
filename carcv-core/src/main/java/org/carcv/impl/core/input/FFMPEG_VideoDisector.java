/**
 * 
 */
package org.carcv.impl.core.input;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.carcv.core.input.VideoDisector;

/**
 * @author oskopek
 *
 */
public class FFMPEG_VideoDisector extends VideoDisector {
    
    final public static int defaultFrameRate = 30;  
    
    public static boolean disectToFrames(Path video) {
        FFMPEG_VideoDisector fvd = new FFMPEG_VideoDisector();
        try {
            return fvd.disectToFrames(video, defaultFrameRate);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }        
    }
    
    @Override
    public boolean disectToFrames(Path video, int frameRate) throws IOException {
        
        Path dir = Paths.get(video.getParent().toString(), video.getFileName()+".dir");
        Files.createDirectory(dir);
        
        return disectToFrames(dir, video, frameRate);
    }

    @Override
    public boolean disectToFrames(Path dir, Path video, int frameRate) throws IOException {
        
        String filenamePrefix = video.getFileName().toString();
        
        Path images = Paths.get(dir.toString(), filenamePrefix + "-%09d.png");
        
        String command = "ffmpeg -i " + video.toString() + " -r " + frameRate 
                + " " +  images.toString();
        
        System.out.println("Executing: " + command);
        
        Process p = Runtime.getRuntime().exec(command);
        try {
            p.waitFor();
        } catch (InterruptedException e) {
            return false;
        }
        
        
        return true;
    }
    
}
