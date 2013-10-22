/**
 * 
 */
package org.carcv.impl.core.recognize;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author oskopek
 * 
 */
public class FileCarRecognizerTest {

    private Path inDir;

    private Path outDir;

    private FileCarRecognizer recognizer;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        inDir = Files.createTempDirectory("inDir");
        outDir = Files.createTempDirectory("outDir");

        recognizer = new FileCarRecognizer(inDir, outDir);
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }

    /**
     * Test method for {@link org.carcv.impl.core.recognize.FileCarRecognizer#recognize()}.
     * 
     * @throws IOException
     */
    @Test
    public void testRecognize() throws IOException {
        assertNotNull(recognizer);
        assertTrue(isDirEmpty(inDir));
        assertTrue(isDirEmpty(outDir));

        recognizer.recognize();

        assertTrue(isDirEmpty(inDir));
        assertTrue(isDirEmpty(outDir));

        //TODO: finish

    }

    private boolean isDirEmpty(Path dir) throws IOException {
        DirectoryStream<Path> ds = Files.newDirectoryStream(dir);

        int counter = 0;
        for (Path p : ds) {
            if (Files.exists(p)) {
                counter++;
            } else {
                throw new IllegalStateException("Path was loaded from dir but the file doesn't exist!");
            }
        }

        if (counter == 0) {
            return true;
        }

        return false;
    }

}
