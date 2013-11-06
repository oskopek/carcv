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

package org.carcv.impl.core.input;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.carcv.core.input.VideoHandler;
import org.carcv.core.model.file.FileEntry;

/**
 * An implementation of a VideoHandler using FFMPEG using {@link Runtime#exec(String)}.
 * <p>
 * The details about using it are here: <a
 * href="https://trac.ffmpeg.org/wiki/Create%20a%20video%20slideshow%20from%20images">FFMPEG Wiki</a>
 * <p>
 * <strong>TODO 1 Revise implementation for new IO directory structure, test and update javadoc</strong>
 *
 */
public class FFMPEG_VideoHandler extends VideoHandler {

    /**
     * A constant referencing the default frame rate of all videos.
     */
    final public static int defaultFrameRate = 30;

    final private static String image_suffix = ".png";

    final private static String video_suffix = ".h264";

    /**
     * Turns the <code>video</code> into a
     *
     * @param video Path of the video file
     * @return true if the method was successful
     */
    public static boolean disectToFrames(Path video) {
        FFMPEG_VideoHandler fvd = new FFMPEG_VideoHandler();
        try {
            return fvd.disectToFrames(video, defaultFrameRate);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static OutputStream generateVideoAsStream(Path imageDir) {
        FFMPEG_VideoHandler fvd = new FFMPEG_VideoHandler();
        try {
            return fvd.generateVideoAsStream(imageDir, defaultFrameRate);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void generateVideoAsStream(final FileEntry entry, final OutputStream outStream) throws IOException {
        FFMPEG_VideoHandler fvd = new FFMPEG_VideoHandler();
        Path dir = entry.getCarImages().get(0).getPath().getParent();

        fvd.generateVideoAsStream(dir, defaultFrameRate, outStream);
    }

    @Override
    public boolean disectToFrames(Path video, int frameRate) throws IOException {

        Path dir = Paths.get(video.getParent().toString(), video.getFileName() + ".dir");
        Files.createDirectory(dir);

        return disectToFrames(dir, video, frameRate);
    }

    @Override
    public boolean disectToFrames(Path dir, Path video, int frameRate) throws IOException {

        String filenamePrefix = video.getFileName().toString();

        Path images = Paths.get(dir.toString(), filenamePrefix + "-%09d" + image_suffix);

        String command = "ffmpeg -i " + video.toString() + " -r " + frameRate + " " + images.toString();

        System.out.println("Executing: " + command);

        Process p = Runtime.getRuntime().exec(command);
        try {
            p.waitFor();
        } catch (InterruptedException e) {
            return false;
        }

        return true;
    }

    @Override
    public Path generateVideo(Path imageDir, int frameRate) throws IOException {
        return createVideo(imageDir, frameRate);
    }

    public void generateVideoAsStream(Path imageDir, int frameRate, final OutputStream outStream) throws IOException {
        Path tmp = createVideo(imageDir, frameRate);
        Files.copy(tmp, outStream);

    }

    @Override
    public OutputStream generateVideoAsStream(Path imageDir, int frameRate) throws IOException {
        Path tmp = createVideo(imageDir, frameRate);
        return new FileOutputStream(tmp.toFile());
    }

    @Override
    public void generateVideo(Path imageDir, Path videoPath, int frameRate) throws IOException {
        Path tmp = createVideo(imageDir, frameRate);
        Files.move(tmp, videoPath, StandardCopyOption.ATOMIC_MOVE);
    }

    private Path createVideo(Path imageDir, int frameRate) throws IOException {
        Path output = Files.createTempFile("video", video_suffix);

        String command = "ffmpeg -r " + frameRate + " -pattern_type glob -i '" +
            imageDir.toAbsolutePath().toString() + File.separator + "*" + image_suffix +
            "' -c:v libx264 -pix_fmt yuv420p " + output.toAbsolutePath().toString();

        System.out.println("Executing: " + command);

        Process p = Runtime.getRuntime().exec(command);
        try {
            p.waitFor();
        } catch (InterruptedException e) {
            return null;
        }

        return output;
    }

}