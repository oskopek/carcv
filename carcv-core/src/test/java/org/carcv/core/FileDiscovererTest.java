/**
 * 
 */
package org.carcv.core;

import static org.junit.Assert.*;

import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author oskopek
 *
 */
public class FileDiscovererTest {
    
    Path rootPath; 
    
    private FileDiscoverer fileDiscoverer;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        rootPath = Files.createTempDirectory("fileDiscovererTestDir");        
        
        fileDiscoverer = new FileDiscoverer(rootPath);
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }

    /**
     * Test method for {@link org.carcv.core.FileDiscoverer#FileDiscoverer(java.nio.file.Path)}.
     */
    @Test
    public void testFileDiscoverer() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link org.carcv.core.FileDiscoverer#getResourceAsStream(java.lang.String)}.
     */
    @Test
    public void testGetResourceAsStream() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link org.carcv.core.FileDiscoverer#getFileDiscoverer()}.
     */
    @Test
    public void testGetFileDiscoverer() {
        FileDiscoverer test = null;
        
        try {
        test = FileDiscoverer.getFileDiscoverer();
        } catch (IllegalStateException e) {
            assertNull(test);
        }
        
        FileDiscoverer.init(rootPath);
        assertNotNull(FileDiscoverer.getBaseDirectory());
        assertNotNull(FileDiscoverer.getFileDiscoverer());
    }

    /**
     * Test method for {@link org.carcv.core.FileDiscoverer#discover()}.
     */
    @Test
    public void testDiscover() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link org.carcv.core.FileDiscoverer#getNew()}.
     */
    @Test
    public void testGetNew() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link org.carcv.core.FileDiscoverer#visitFile(java.nio.file.Path, java.nio.file.attribute.BasicFileAttributes)}.
     */
    @Test
    public void testVisitFilePathBasicFileAttributes() {
        fail("Not yet implemented");
    }

}
