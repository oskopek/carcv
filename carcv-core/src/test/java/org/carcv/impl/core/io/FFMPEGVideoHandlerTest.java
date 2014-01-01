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
package org.carcv.impl.core.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.carcv.core.io.DirectoryWatcher;
import org.carcv.core.model.file.FileEntry;
import org.carcv.impl.core.model.FileEntryTool;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Test for {@link FFMPEGVideoHandler}.
 */
@Ignore(value = "createVideo doesn't work")
public class FFMPEGVideoHandlerTest {

    final private static Logger LOGGER = LoggerFactory.getLogger(FFMPEGVideoHandlerTest.class);

    private Path rootDir;
    private Path videoDir;
    private FileEntryTool tool;
    private FileEntry entry;

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        assumeTrue(isFFMPEGInstalled());
    }

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        rootDir = Files.createTempDirectory("ffmpegVideoHandlerTest-");

        videoDir = Files.createTempDirectory(rootDir, "videoDir-");

        InputStream is1 = getClass().getResourceAsStream("/img/skoda_oct.jpg");
        InputStream is2 = getClass().getResourceAsStream("/img/test_041.jpg");

        Path p1 = Paths.get(rootDir.toString(), "videoImage1-" + System.currentTimeMillis() + ".jpg");
        Path p2 = Paths.get(rootDir.toString(), "videoImage2-" + System.currentTimeMillis() + ".jpg");

        Files.copy(is1, p1);
        Files.copy(is2, p2);

        tool = new FileEntryTool();
        entry = tool.generate(p1, p2);
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
        /*
         * tool.close(); DirectoryWatcher.deleteDirectory(rootDir);
         *
         * DirectoryStream<Path> leftOvers = Files.newDirectoryStream(rootDir.getParent()); for(Path p : leftOvers) { String
         * filename = p.getFileName().toString();
         *
         * if (filename.startsWith("tempVideo") || filename.startsWith("testvideo") || filename.startsWith("video")) {
         * DirectoryWatcher.deleteDirectory(p); } }
         */
    }

    /**
     * Test method for {@link org.carcv.impl.core.io.FFMPEGVideoHandler#splitIntoFrames(java.nio.file.Path, int)}.
     *
     * @throws IOException
     */
    @Test
    public void testSplitIntoFramesPathInt() throws IOException {
        FFMPEGVideoHandler fvh = new FFMPEGVideoHandler();
        FFMPEGVideoHandler.copyCarImagesToDir(entry.getCarImages(), videoDir);
        Path video = fvh.generateVideo(videoDir, FFMPEGVideoHandler.defaultFrameRate);

        assertTrue("Split failed.", fvh.splitIntoFrames(video, FFMPEGVideoHandler.defaultFrameRate));

        Path dir = Paths.get(video.toString() + ".dir");
        DirectoryStream<Path> paths = Files.newDirectoryStream(dir);
        int counter = 0;
        for (@SuppressWarnings("unused")
        Path p : paths) {
            counter++;
        }
        assertEquals(entry.getCarImages().size(), counter);

        Files.delete(video);
        DirectoryWatcher.deleteDirectory(dir);
    }

    /**
     * Test method for
     * {@link org.carcv.impl.core.io.FFMPEGVideoHandler#splitIntoFrames(java.nio.file.Path, int, java.nio.file.Path)}.
     *
     * @throws IOException
     */
    @Test
    public void testSplitIntoFramesPathIntPath() throws IOException {
        FFMPEGVideoHandler fvh = new FFMPEGVideoHandler();
        FFMPEGVideoHandler.copyCarImagesToDir(entry.getCarImages(), videoDir);
        Path video = fvh.generateVideo(videoDir, FFMPEGVideoHandler.defaultFrameRate);

        Path dir = Paths.get(video.toString() + ".custom_dir");
        assertTrue("Split failed.", fvh.splitIntoFrames(video, FFMPEGVideoHandler.defaultFrameRate, dir));

        DirectoryStream<Path> paths = Files.newDirectoryStream(dir);
        int counter = 0;
        for (@SuppressWarnings("unused")
        Path p : paths) {
            counter++;
        }
        assertEquals(entry.getCarImages().size(), counter);

        Files.delete(video);
        DirectoryWatcher.deleteDirectory(dir);
    }

    /**
     * Test method for
     * {@link org.carcv.impl.core.io.FFMPEGVideoHandler#generateVideo(java.nio.file.Path, int, java.nio.file.Path)}.
     *
     * @throws IOException
     */
    @Test
    public void testGenerateVideoPathIntPath() throws IOException {
        FFMPEGVideoHandler.copyCarImagesToDir(entry.getCarImages(), videoDir);
        Path video = new FFMPEGVideoHandler().generateVideo(videoDir, FFMPEGVideoHandler.defaultFrameRate);
        new FFMPEGVideoHandler().generateVideo(videoDir, FFMPEGVideoHandler.defaultFrameRate);
        assertTrue("Generated Video file doesn't exist.", Files.exists(video));

        Files.delete(video);
    }

    /**
     * Test method for {@link org.carcv.impl.core.io.FFMPEGVideoHandler#generateVideo(java.nio.file.Path, int)}.
     *
     * @throws IOException
     */
    @Test
    public void testGenerateVideoPathInt() throws IOException {
        FFMPEGVideoHandler.copyCarImagesToDir(entry.getCarImages(), videoDir);
        Path video = new FFMPEGVideoHandler().generateVideo(videoDir, FFMPEGVideoHandler.defaultFrameRate);
        assertTrue("Generated Video file doesn't exist.", Files.exists(video));

        Files.delete(video);
    }

    /**
     * Test method for {@link org.carcv.impl.core.io.FFMPEGVideoHandler#generateVideoAsStream(java.nio.file.Path, int)}.
     *
     * @throws IOException
     */
    @Test
    public void testGenerateVideoAsStreamPathInt() throws IOException {
        FFMPEGVideoHandler.copyCarImagesToDir(entry.getCarImages(), videoDir);
        OutputStream out = new FFMPEGVideoHandler().generateVideoAsStream(videoDir, FFMPEGVideoHandler.defaultFrameRate);
        assertNotNull("Generated video output stream is null", out);
    }

    /**
     * Test method for {@link org.carcv.impl.core.io.FFMPEGVideoHandler#FFMPEGVideoHandler()}.
     */
    @Test
    public void testFFMPEGVideoHandler() {
        assertNotNull(FFMPEGVideoHandler.defaultFrameRate);
        assertEquals(30, FFMPEGVideoHandler.defaultFrameRate);
        FFMPEGVideoHandler fvh = new FFMPEGVideoHandler();
        assertNotNull(fvh);
        assertEquals(30, FFMPEGVideoHandler.defaultFrameRate);
    }

    /**
     * Test method for {@link org.carcv.impl.core.io.FFMPEGVideoHandler#splitIntoFrames(java.nio.file.Path)}.
     *
     * @throws IOException
     */
    @Test
    public void testSplitIntoFramesPath() throws IOException {
        FFMPEGVideoHandler fvh = new FFMPEGVideoHandler();
        FFMPEGVideoHandler.copyCarImagesToDir(entry.getCarImages(), videoDir);
        Path video = fvh.generateVideo(videoDir, FFMPEGVideoHandler.defaultFrameRate);

        assertTrue("Split failed.", FFMPEGVideoHandler.splitIntoFrames(video));

        Path dir = Paths.get(video.toString() + ".dir");
        DirectoryStream<Path> paths = Files.newDirectoryStream(dir);
        int counter = 0;
        for (@SuppressWarnings("unused")
        Path p : paths) {
            counter++;
        }
        assertEquals(entry.getCarImages().size(), counter);

        Files.delete(video);
        DirectoryWatcher.deleteDirectory(dir);
    }

    /**
     * Test method for {@link org.carcv.impl.core.io.FFMPEGVideoHandler#generateVideoAsStream(java.nio.file.Path)}.
     *
     * @throws IOException
     */
    @Test
    public void testGenerateVideoAsStreamPath() throws IOException {
        FFMPEGVideoHandler.copyCarImagesToDir(entry.getCarImages(), videoDir);
        OutputStream out = FFMPEGVideoHandler.generateVideoAsStream(videoDir);
        assertNotNull("The Generated Video stream is null.", out);
    }

    /**
     * Test method for
     * {@link org.carcv.impl.core.io.FFMPEGVideoHandler#generateVideoAsStream(org.carcv.core.model.file.FileEntry, java.io.OutputStream)}
     * .
     *
     * @throws IOException
     */
    @Test
    public void testGenerateVideoAsStreamFileEntryOutputStream() throws IOException {
        Path outTemp = Files.createTempFile("testvideo", ".h264");
        OutputStream out = Files.newOutputStream(outTemp);
        FFMPEGVideoHandler.generateVideoAsStream(entry, out);
        assertNotNull(out);
        assertTrue(Files.exists(outTemp));
        assertTrue(Files.isRegularFile(outTemp));

        Files.delete(outTemp);
    }

    /**
     * Test method for {@link org.carcv.impl.core.io.FFMPEGVideoHandler#copyCarImagesToDir(java.util.List, java.nio.file.Path)}.
     *
     * @throws IOException
     */
    @Test
    public void testCopyCarImagesToDir() throws IOException {
        FFMPEGVideoHandler.copyCarImagesToDir(entry.getCarImages(), videoDir);
        DirectoryStream<Path> dirStream = Files.newDirectoryStream(videoDir);
        ArrayList<Path> paths = new ArrayList<>();
        for (Path p : dirStream) {
            paths.add(p);
        }
        dirStream.close();
        assertEquals(2, paths.size());

        Collections.sort(paths, new Comparator<Path>() {
            @Override
            public int compare(Path o1, Path o2) {
                return new CompareToBuilder().append(o1.getFileName().toString(), o2.getFileName().toString()).toComparison();
            }
        });

        for (int i = 0; i < paths.size(); i++) {
            assertTrue(paths.get(i).getFileName().toString().startsWith(i + ""));
        }
    }

    /**
     * Test method for {@link org.carcv.impl.core.io.FFMPEGVideoHandler#getSuffix(java.nio.file.Path)}.
     */
    @Test
    public void testGetSuffix() {
        Path image = entry.getCarImages().get(0).getFilepath();
        assertTrue(image.toString().endsWith("jpg"));

        String suffix = FFMPEGVideoHandler.getSuffix(image);
        assertEquals("jpg", suffix);
    }

    /**
     * Test method for
     * {@link org.carcv.impl.core.io.FFMPEGVideoHandler#generateVideoAsStream(java.nio.file.Path, int, java.io.OutputStream)}.
     *
     * @throws IOException
     */
    @Test
    public void testGenerateVideoAsStreamPathIntOutputStream() throws IOException {
        Path outTemp = Files.createTempFile("testvideo", ".h264");
        OutputStream out = Files.newOutputStream(outTemp);

        FFMPEGVideoHandler.copyCarImagesToDir(entry.getCarImages(), videoDir);
        new FFMPEGVideoHandler().generateVideoAsStream(videoDir, FFMPEGVideoHandler.defaultFrameRate, out);
        assertNotNull(out);
        assertTrue(Files.exists(outTemp));
        assertTrue(Files.isRegularFile(outTemp));

        Files.delete(outTemp);
    }

    /**
     * Test method for {@link org.carcv.impl.core.io.FFMPEGVideoHandler#createVideo(java.nio.file.Path, int)}.
     *
     * @throws IOException
     */
    @Test
    public void testCreateVideo() throws IOException {
        FFMPEGVideoHandler.copyCarImagesToDir(entry.getCarImages(), videoDir);
        Path video = new FFMPEGVideoHandler().createVideo(videoDir, FFMPEGVideoHandler.defaultFrameRate);
        assertTrue("Generated video file doesn't exist.", Files.exists(video));

        Files.delete(video);
    }

    @Test
    public void testCreateVideoInternal() throws IOException, InterruptedException {
        FFMPEGVideoHandler.copyCarImagesToDir(entry.getCarImages(), videoDir);

        DirectoryStream<Path> testpaths = Files.newDirectoryStream(videoDir);
        int counterTest = 0;
        for (Path p : testpaths) {
            assertTrue(Files.exists(p));
            counterTest++;
        }
        assertEquals(2, counterTest);

        DirectoryStream<Path> paths = Files.newDirectoryStream(videoDir);
        String imageSuffix = ".invalid";
        for (Path p : paths) {
            imageSuffix = FFMPEGVideoHandler.getSuffix(p);
            if (imageSuffix != null)
                break;
        }

        Path output = Paths.get("/tmp", "video-"
            + new Random().nextInt() + "-"
            + System.currentTimeMillis()
            + "." + "mjpeg");

        String command = "ffmpeg -y -f image2 -pattern_type glob -i \"" +
            videoDir.toAbsolutePath().toString() + File.separator + "*." + imageSuffix +
            "\" -r " + 2 + " " + output.toAbsolutePath().toString();

        LOGGER.info("Executing: " + command);
        Process p = Runtime.getRuntime().exec(command);
        LOGGER.debug(getErrorMessage(p.getErrorStream()));

        try {
            LOGGER.debug("Return value: {}", p.waitFor());
        } catch (InterruptedException e) {
            throw e;
        }

        assertTrue(Files.exists(output));
        LOGGER.debug("Path of created video: {}", output);
    }

    /**
     * Tests if FFMPEG is installed using "ffmpeg -version"
     *
     * @return true if ffmpeg is installed and works
     * @throws InterruptedException if the runtime is interrupted while waiting for the return value
     */
    private static boolean isFFMPEGInstalled() throws InterruptedException {
        Process ffmpegVersionTest;
        try {
            ffmpegVersionTest = Runtime.getRuntime().exec("ffmpeg -version");
        } catch (IOException e) {
            return false; // if ffmpeg isn't installed
        }
        int retVal = ffmpegVersionTest.waitFor();

        return retVal == 0 ? true : false; // if the return value is 0, ffmpeg returned without problem
    }

    public String getErrorMessage(InputStream error) throws IOException {
        BufferedReader stdError = new BufferedReader(new
            InputStreamReader(error));
        String s = "";
        String lastLine = s;
        while ((s = stdError.readLine()) != null) {
            lastLine = s;
        }
        return lastLine;
    }
}