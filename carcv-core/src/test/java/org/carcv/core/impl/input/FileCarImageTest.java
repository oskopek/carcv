/**
 * 
 */
package org.carcv.core.impl.input;

import static org.junit.Assert.*;

import java.awt.Rectangle;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.carcv.core.model.file.FileCarImage;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author oskopek
 *
 */
public class FileCarImageTest {
    
    private Path filepath;
    
    private FileCarImage file;

    /**
     * @throws URISyntaxException 
     */
    @Before
    public void setUp() throws URISyntaxException {
        filepath = Paths.get(getClass().getResource("/img/skoda_oct.jpg").toURI());
        
        file = new FileCarImage(filepath);
        assertNotNull(file);
        assertNull(file.getImage());
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() {        
        filepath = null;
        file.close();
    }

    /**
     * Test method for {@link org.carcv.core.model.file.FileCarImage#ImageFile(java.nio.file.Path)}.
     * @throws IOException 
     */
    @Test
    public void testImageFile() throws IOException {
        
        Path tmpFile = null;
        try {
            tmpFile = Files.createTempFile("imageFile", ".carcv.jpg");
        } catch (IOException e) {
            System.err.println("Failed to create temp file");
            e.printStackTrace();
        }
        assertNotNull(tmpFile);
        
        FileCarImage iFile = new FileCarImage(tmpFile);        
        assertNotNull(iFile);
        
        try {
            iFile.loadImage();
        } catch (final Exception e) {
            //System.err.println(e.getMessage() + " - this is expected");
        }
        
        assertNotNull(iFile);
        assertNull(iFile.getImage());
        assertTrue(Files.deleteIfExists(tmpFile));
    }

    /**
     * Test method for {@link org.carcv.core.model.file.FileCarImage#loadImage()}.
     * @throws IOException 
     */
    @Test
    public void testLoadImage() throws IOException {
        file.loadImage();
        assertNotNull(file);
        assertNotNull(file.getImage());
    }

    /**
     * Test method for {@link org.carcv.core.model.file.FileCarImage#loadFragment(java.awt.Rectangle)}.
     * @throws IOException 
     */
    @Test
    @Ignore(value="Fragment loading not supported yet")
    public void testLoadFragment() throws IOException {        
        Rectangle rect = new Rectangle(10, 10, 10, 10);
        assertNotNull(rect);
        
        file.loadFragment(rect);
        assertNotNull(file);
        assertNotNull(file.getImage());
    }

    /**
     * Test method for {@link org.carcv.core.model.file.FileCarImage#close()}.
     * @throws IOException 
     */
    @Test
    public void testCloseNonLoaded() throws IOException {
        Path tmpFile = null;
        try {
            tmpFile = Files.createTempFile("imageFile", ".carcv.jpg");
        } catch (IOException e) {
            System.err.println("Failed to create temp file");
            e.printStackTrace();
        }
        assertNotNull(tmpFile);
        
        FileCarImage iFile = new FileCarImage(tmpFile);        
        assertNotNull(iFile);
        
        iFile.close();
        assertNotNull(iFile);
        assertNotNull(iFile.getPersistablePath());
        assertNull(iFile.getImage());
        
        assertTrue(Files.deleteIfExists(tmpFile));
    }
    
    /**
     * Test method for {@link org.carcv.core.model.file.FileCarImage#close()}.
     * @throws IOException 
     */
    @Test
    public void testCloseLoadedNonExisting() throws IOException {
        Path tmpFile = null;
        try {
            tmpFile = Files.createTempFile("imageFile", ".carcv.jpg");
        } catch (IOException e) {
            System.err.println("Failed to create temp file");
            e.printStackTrace();
        }
        assertNotNull(tmpFile);
        
        FileCarImage iFile = new FileCarImage(tmpFile);        
        assertNotNull(iFile);
        
        try {
            iFile.loadImage();
        } catch (final Exception e) {
            //System.err.println(e.getMessage() + " - this is expected");
            
        }
        assertNotNull(iFile);
        assertNull(iFile.getImage());
        
        iFile.close();
        assertNotNull(iFile);
        assertNotNull(iFile.getPersistablePath());
        assertNull(iFile.getImage());
        
        assertTrue(Files.deleteIfExists(tmpFile));
    }
    
    /**
     * Test method for {@link org.carcv.core.model.file.FileCarImage#close()}.
     * @throws IOException
     */
    @Test
    public void testCloseLoadedExisting() throws IOException {        
        FileCarImage iFile = new FileCarImage(filepath);        
        assertNotNull(iFile);
        
        iFile.loadImage();
        assertNotNull(iFile);
        assertNotNull(iFile.getImage());
        
        //BufferedImage loaded = iFile.getBufImage();
        
        iFile.close();
        assertNotNull(iFile);
        assertNotNull(iFile.getPersistablePath());
        
        //BufferedImage closed = iFile.getBufImage();
        
        //TODO: how to test if it is really flushed?
    }

}
