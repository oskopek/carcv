/**
 * 
 */
package org.carcv.core.input;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;

/**
 * It is encouraged to use a script to split up into frames images externally
 * @author oskopek
 */
public abstract class VideoHandler {

    public abstract boolean disectToFrames(Path video, int frameRate) throws IOException;

    public abstract boolean disectToFrames(Path dir, Path video, int frameRate) throws IOException;
    
    public abstract void generateVideo(Path imageDir, Path videoPath, int frameRate) throws IOException;
    
    public abstract Path generateVideo(Path imageDir, int frameRate) throws IOException;
    
    public abstract OutputStream generateVideoAsStream(Path imageDir, int frameRate) throws IOException;

}
