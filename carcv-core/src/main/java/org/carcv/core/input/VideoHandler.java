/* 
 * Copyright 2012 CarCV Development Team
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

package org.carcv.core.input;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;

/**
 * It is encouraged to use a script to split up into frames images externally
 */
public abstract class VideoHandler {

    public abstract boolean disectToFrames(Path video, int frameRate) throws IOException;

    public abstract boolean disectToFrames(Path dir, Path video, int frameRate) throws IOException;
    
    public abstract void generateVideo(Path imageDir, Path videoPath, int frameRate) throws IOException;
    
    public abstract Path generateVideo(Path imageDir, int frameRate) throws IOException;
    
    public abstract OutputStream generateVideoAsStream(Path imageDir, int frameRate) throws IOException;

}