/**
 * 
 */
package org.carcv.core.impl.input;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;
import java.util.TreeSet;

import org.carcv.core.model.file.FileCarImage;
import org.carcv.impl.core.input.FileDiscoverer;
import org.carcv.impl.core.input.FileImageQueue;
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
    
    private FileImageQueue queue;

    private FileAttribute<Set<PosixFilePermission>> permissions;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        queue = new FileImageQueue();
        
        rootPath = Files.createTempDirectory("fileDiscovererTestDir");

        fileDiscoverer = new FileDiscoverer(rootPath, queue);

        TreeSet<PosixFilePermission> permissions = new TreeSet<>();
        PosixFilePermission[] perms = PosixFilePermission.values();

        for (PosixFilePermission p : perms) {
            permissions.add(p);
        }

        this.permissions = PosixFilePermissions.asFileAttribute(permissions);
    }

    /**
     * Deletes the temp directory
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
        //   /*
        deleteDirectory(rootPath);
        assertFalse(Files.exists(rootPath));
        assertFalse(Files.isDirectory(rootPath));
        //   */
    }

    /**
     * Test method for {@link org.carcv.impl.core.input.FileDiscoverer#FileDiscoverer(java.nio.file.Path)}.
     */
    @Test
    public void testFileDiscoverer() {
        FileDiscoverer d = new FileDiscoverer(rootPath, queue);

        assertNotNull(d);
        assertNotNull(d.getBaseDirectory());
        assertEquals(rootPath, d.getBaseDirectory());
    }

    /**
     * Test method for {@link org.carcv.impl.core.input.FileDiscoverer#getResourceAsStream(java.lang.String)}.
     */
    @SuppressWarnings("deprecation")
    @Test
    public void testGetResourceAsStream() {
        InputStream is = fileDiscoverer.getResourceAsStream("/img/skoda_oct.jpg");

        assertNotNull(is);
    }

    /**
     * Test method for {@link org.carcv.core.input.FileDiscoverer#getFileDiscoverer()}.
     */
    /*
    @Test
    public void testGetFileDiscoverer() {
        FileDiscoverer test = null;

        try {
            test = FileDiscoverer.getFileDiscoverer();
        } catch (IllegalStateException e) {
            assertNull(test);
        }

        FileDiscoverer.init(rootPath);

        test = FileDiscoverer.getFileDiscoverer();

        assertNotNull(test.getBaseDirectory());
        assertNotNull(FileDiscoverer.getFileDiscoverer());
    }
    */

    /**
     * Test method for {@link org.carcv.impl.core.input.FileDiscoverer#discover()}.
     * 
     * @throws IOException
     */
    @Test
    public void testFileDiscovery() throws IOException {
        assertNotNull(fileDiscoverer);
        //System.out.println("FileDiscovery dir: " + rootPath); /* {@link org.carcv.core.input.FileDiscovererTest#tearDown()} */

        Path newFile1 = Files.createTempFile(rootPath, "testFileDiscovery", ".carcv.jpg", permissions);
        assertNotNull(newFile1);
        //System.out.println(newFile1); /* {@link org.carcv.core.input.FileDiscovererTest#tearDown()} */

        fileDiscoverer.discover();

        FileCarImage newPath1 = queue.poll();
        assertNotNull(newPath1);
        assertNotNull(newPath1.getFilepath());
        assertNull(newPath1.getImage());

        Path newFile2 = Files.createTempFile(rootPath, "testFileDiscovery", ".carcv.jpg", permissions);
        assertNotNull(newFile2);
        //System.out.println(newFile2);  /* {@link org.carcv.core.input.FileDiscovererTest#tearDown()} */

        fileDiscoverer.discover();

        FileCarImage newPath2 = queue.poll();
        assertNotNull(newPath2);
        assertNotNull(newPath2.getFilepath());
        assertNull(newPath2.getImage());

        assertNotEquals(newPath1, newPath2);
        assertNotEquals(newPath1.getFilepath(), newPath2.getFilepath());
        assertNotEquals(newFile1, newFile2);
    }

    private static void deleteDirectory(Path path) throws IOException {
        Files.walkFileTree(path, new FileVisitor<Path>() {

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                //System.out.println("Deleting directory :" + dir);
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                //System.out.println("Deleting file: " + file);
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                //System.out.println(exc.toString());
                return FileVisitResult.CONTINUE;
            }
        });
    }

}
