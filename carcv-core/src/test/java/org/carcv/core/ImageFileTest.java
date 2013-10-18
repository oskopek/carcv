/**
 * 
 */
package org.carcv.core;

import static org.junit.Assert.*;

import java.awt.Rectangle;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author oskopek
 *
 */
public class ImageFileTest {
    
    private Path filepath;
    
    private ImageFile file;

    /**
     * @throws URISyntaxException 
     */
    @Before
    public void setUp() throws URISyntaxException {
        filepath = Paths.get(getClass().getResource("/img/skoda_oct.jpg").toURI());
        
        file = new ImageFile(filepath);
        assertNotNull(file);
        assertNull(file.getBufImage());
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
     * Test method for {@link org.carcv.core.ImageFile#ImageFile(java.nio.file.Path)}.
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
        
        ImageFile iFile = new ImageFile(tmpFile);        
        assertNotNull(iFile);
        
        try {
            iFile.loadImage();
        } catch (final Exception e) {
            //System.err.println(e.getMessage() + " - this is expected");
        }
        
        assertNotNull(iFile);
        assertNull(iFile.getBufImage());
        assertTrue(Files.deleteIfExists(tmpFile));
    }

    /**
     * Test method for {@link org.carcv.core.ImageFile#loadImage()}.
     * @throws IOException 
     */
    @Test
    public void testLoadImage() throws IOException {
        file.loadImage();
        assertNotNull(file);
        assertNotNull(file.getBufImage());
    }

    /**
     * Test method for {@link org.carcv.core.ImageFile#loadFragment(java.awt.Rectangle)}.
     * @throws IOException 
     */
    @Test
    @Ignore(value="Fragment loading not supported yet")
    public void testLoadFragment() throws IOException {        
        Rectangle rect = new Rectangle(10, 10, 10, 10);
        assertNotNull(rect);
        
        file.loadFragment(rect);
        assertNotNull(file);
        assertNotNull(file.getBufImage());
    }

    /**
     * Test method for {@link org.carcv.core.ImageFile#close()}.
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
        
        ImageFile iFile = new ImageFile(tmpFile);        
        assertNotNull(iFile);
        
        iFile.close();
        assertNotNull(iFile);
        assertNotNull(iFile.getFilepath());
        assertNull(iFile.getBufImage());
        
        assertTrue(Files.deleteIfExists(tmpFile));
    }
    
    /**
     * Test method for {@link org.carcv.core.ImageFile#close()}.
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
        
        ImageFile iFile = new ImageFile(tmpFile);        
        assertNotNull(iFile);
        
        try {
            iFile.loadImage();
        } catch (final Exception e) {
            //System.err.println(e.getMessage() + " - this is expected");
            
        }
        assertNotNull(iFile);
        assertNull(iFile.getBufImage());
        
        iFile.close();
        assertNotNull(iFile);
        assertNotNull(iFile.getFilepath());
        assertNull(iFile.getBufImage());
        
        assertTrue(Files.deleteIfExists(tmpFile));
    }
    
    /**
     * Test method for {@link org.carcv.core.ImageFile#close()}.
     * @throws IOException
     */
    @Test
    public void testCloseLoadedExisting() throws IOException {        
        ImageFile iFile = new ImageFile(filepath);        
        assertNotNull(iFile);
        
        iFile.loadImage();
        assertNotNull(iFile);
        assertNotNull(iFile.getBufImage());
        
        //BufferedImage loaded = iFile.getBufImage();
        
        iFile.close();
        assertNotNull(iFile);
        assertNotNull(iFile.getFilepath());
        
        //BufferedImage closed = iFile.getBufImage();
        
        //TODO: how to test it is really flushed?
    }

}
