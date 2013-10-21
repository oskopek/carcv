/**
 * 
 */
package org.carcv.core.input;

import java.io.IOException;
import java.nio.file.Path;

/**
 * @author oskopek
 *
 */
public abstract class VideoDisector {
    
    public abstract boolean disectToFrames(Path video, int frameRate) throws IOException;
    
    public abstract boolean disectToFrames(Path dir, Path video, int frameRate) throws IOException;

}
