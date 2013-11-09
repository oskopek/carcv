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

package org.carcv.core.io;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;

/**
 * An abstraction of an object that handles splitting a video into frames of a video and rebuilding it from the frames later.
 */
public abstract class VideoHandler {

    /**
     * Splits video into frames with a frame rate.
     * 
     * @param video Path of the input video file
     * @param frameRate number of frames per second
     * @return true if the splitting finished successfully
     * @throws IOException if an error during loading video or saving images occurred
     */
    public abstract boolean splitIntoFrames(Path video, int frameRate) throws IOException;

    /**
     * Splits video into frames with a frame rate.
     * 
     * @param video Path of the input video file
     * @param frameRate number of frames per second
     * @param dir directory where output files will be saved
     * @return true if the splitting finished successfully
     * @throws IOException if an error during loading video or saving images occurred
     */
    public abstract boolean splitIntoFrames(Path video, int frameRate, Path imageDir) throws IOException;

    /**
     * Creates a video from all images in a directory.
     * 
     * @param imageDir directory from which to load images
     * @param video Path of the output video file
     * @param frameRate number of frames per second
     * @throws IOException if an error during loading images or saving of the video
     */
    public abstract void generateVideo(Path imageDir, int frameRate, Path video) throws IOException;

    /**
     * Creates a video from all images in a directory.
     * 
     * @param imageDir directory from which to load images
     * @param frameRate number of frames per second
     * @throws IOException if an error during loading images or saving of the video
     */
    public abstract Path generateVideo(Path imageDir, int frameRate) throws IOException;

    /**
     * Creates a video from all images in a directory.
     * 
     * @param imageDir directory from which to load images
     * @param frameRate number of frames per second
     * @throws IOException if an error during loading images or creating the video
     */
    public abstract OutputStream generateVideoAsStream(Path imageDir, int frameRate) throws IOException;
}