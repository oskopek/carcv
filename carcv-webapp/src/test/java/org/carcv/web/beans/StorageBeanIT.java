/*
 * Copyright 2013 CarCV Development Team
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

package org.carcv.web.beans;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;

import javax.ejb.EJB;

import org.apache.commons.io.FileUtils;
import org.carcv.core.io.DirectoryWatcher;
import org.carcv.core.model.file.FileCarImage;
import org.carcv.core.model.file.FileEntry;
import org.carcv.impl.core.model.FileEntryTool;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Test class for {@link StorageBean}
 */
@RunWith(Arquillian.class)
public class StorageBeanIT {

    @EJB
    private StorageBean storageBean;

    @EJB
    private EntryBean entryBean;

    private Path rootDir;

    @Deployment
    public static WebArchive createDeployment() {

        WebArchive testArchive = ShrinkWrap.createFromZipFile(WebArchive.class, new File("target/carcv-webapp.war"));

        testArchive.delete("WEB-INF/classes/META-INF/persistence.xml");
        testArchive.addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml");

        testArchive.delete("WEB-INF/jboss-web.xml");
        testArchive.addAsWebInfResource("WEB-INF/test-jboss-web.xml", "jboss-web.xml");

        testArchive.addAsResource("arquillian.xml");

        // testArchive.as(ZipExporter.class).exportTo(new File("target/carcv-webapp-test.war"));

        return testArchive;
    }

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        // we choose to compare to this path, because the StorageBean should internally chose it against OPENSHIFT_DATA_DIR
        String property = System.getProperty(StorageBean.JbossDataDirProperty);
        rootDir = Paths.get(property, "carcv_data");
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
        DirectoryWatcher.deleteDirectory(storageBean.getInputDirectory().getParent());
    }

    /**
     * Test method for {@link org.carcv.web.beans.StorageBean#getInputDirectory()}.
     */
    @Test
    public void testGetInputDirectory() {
        assertNotNull(storageBean.getInputDirectory());
        assertNotNull(Paths.get(rootDir.toString(), "in"));
        assertEquals(Paths.get(rootDir.toString(), "in"), storageBean.getInputDirectory());
    }

    /**
     * Test method for {@link org.carcv.web.beans.StorageBean#getOutputDirectory()}.
     */
    @Test
    public void testGetOutputDirectory() {
        assertNotNull(storageBean.getOutputDirectory());
        assertNotNull(Paths.get(rootDir.toString(), "out"));
        assertEquals(Paths.get(rootDir.toString(), "out"), storageBean.getOutputDirectory());
    }

    /**
     * Test method for {@link org.carcv.web.beans.StorageBean#createBatchDirectory()}.
     *
     * @throws IOException
     */
    @Test
    public void testCreateBatchDirectory() throws IOException {
        Path p = storageBean.createBatchDirectory();
        assertFalse(DirectoryWatcher.isDirEmpty(storageBean.getInputDirectory()));
        DirectoryStream<Path> stream = Files.newDirectoryStream(storageBean.getInputDirectory());

        for (Path dir : stream) { // there should only be one directory, therefore the assertion should be true
            assertEquals(p, dir);
        }
    }

    /**
     * Test method for {@link org.carcv.web.beans.StorageBean#storeToDirectory(java.io.InputStream, String, Path)}.
     *
     * @throws IOException
     */
    @Test
    public void testStoreToDirectory() throws IOException {
        FileEntryTool tool = new FileEntryTool();
        FileEntry f = tool.generate();

        assertNotNull(f);
        Path original = f.getCarImages().get(0).getFilepath();
        assertNotNull(original);
        assertTrue(Files.exists(original));

        // store
        String fileName = "test" + f.hashCode() + ".jpg";
        storageBean.storeToDirectory(Files.newInputStream(original), fileName, Paths.get("/tmp"));

        Path newPath = Paths.get("/tmp", fileName);
        assertNotNull(newPath);
        assertTrue(Files.exists(newPath));
        assertTrue(FileUtils.contentEquals(newPath.toFile(), original.toFile()));
    }

    /**
     * Test method for {@link org.carcv.web.beans.StorageBean#storeBatchToDatabase(java.util.List)}.
     *
     * @throws IOException
     */
    @Test
    public void testStoreBatchToDatabase() throws IOException {
        Random r = new Random();
        FileEntryTool tool = new FileEntryTool();

        FileEntry[] arr = new FileEntry[r.nextInt(11)];

        for (int i = 0; i < arr.length; i++) {
            FileEntry f = tool.generate();
            assertNotNull(f);
            arr[i] = f;
        }

        entryBean.persist(arr);

        ArrayList<FileEntry> got = (ArrayList<FileEntry>) entryBean.getAll();

        assertNotNull(got);
        assertNotEquals(0, got.size());

        for (int i = 0; i < arr.length; i++) {
            assertTrue(got.contains(arr[i]));

            assertEquals(arr[i], entryBean.findById(arr[i].getId()));
        }

        // clean up
        entryBean.remove(arr);
        for (int i = 0; i < arr.length; i++) {
            for (FileCarImage fi : arr[i].getCarImages()) {
                Files.delete(fi.getFilepath());
            }
        }
    }

    /**
     * Test method for {@link org.carcv.web.beans.StorageBean#getPrefix()}.
     */
    @Test
    public void testGetPrefix() {
        String env = System.getenv(StorageBean.envname_OPENSHIFT_DATA_DIR);
        Path checkedPath;

        if (env == null) { // if test isn't on OpenShift
            String jbossData = System.getProperty("jboss.server.data.dir");
            assertEquals(jbossData, storageBean.getPrefix());

            checkedPath = Paths.get(jbossData);
        } else {
            assertEquals(env, storageBean.getPrefix());
            checkedPath = Paths.get(env);
        }

        assertNotNull(checkedPath);
        assertTrue(Files.exists(checkedPath));
        assertTrue(Files.isDirectory(checkedPath));
        assertTrue(Files.isWritable(checkedPath));
    }
}