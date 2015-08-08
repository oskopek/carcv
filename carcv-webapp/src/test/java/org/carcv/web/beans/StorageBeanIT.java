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

package org.carcv.web.beans;

import org.apache.commons.io.FileUtils;
import org.carcv.core.io.DirectoryWatcher;
import org.carcv.core.model.file.FileCarImage;
import org.carcv.core.model.file.FileEntry;
import org.carcv.impl.core.model.FileEntryTool;
import org.carcv.web.test.AbstractIT;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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
        WebArchive testArchive = AbstractIT.createGenericDeployment();
        testArchive.addAsResource("img/skoda_oct.jpg");
        testArchive.addAsResource("img/test_041.jpg");
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
     * @throws Exception
     */
    @Test
    public void testStoreToDirectory() throws Exception {
        FileEntryTool tool = new FileEntryTool();

        InputStream is1 = getClass().getResourceAsStream("/img/skoda_oct.jpg");
        InputStream is2 = getClass().getResourceAsStream("/img/test_041.jpg");

        Path p1 = Paths.get("/tmp", "storageImage1-" + System.currentTimeMillis() + ".jpg");
        Path p2 = Paths.get("/tmp", "storageImage2-" + System.currentTimeMillis() + ".jpg");

        assertNotNull(is1);
        assertNotNull(is2);
        assertNotNull(p1);
        assertNotNull(p2);
        Files.copy(is1, p1);
        Files.copy(is2, p2);

        FileEntry f = tool.generate(p1, p2);
        assertFileEntry(f);

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

        Files.delete(p1);
        Files.delete(p2);
        tool.close();
    }

    /**
     * Test method for {@link org.carcv.web.beans.StorageBean#storeBatchToDatabase(java.util.List)}.
     *
     * @throws Exception
     */
    @Test
    public void testStoreBatchToDatabase() throws Exception {
        Random r = new Random();
        FileEntryTool tool = new FileEntryTool();

        // prepare

        FileEntry[] arr = new FileEntry[r.nextInt(11)];
        List<FileEntry> list = new ArrayList<>(arr.length);
        for (int i = 0; i < arr.length; i++) {
            FileEntry f = tool.generate();
            assertNotNull(f);
            arr[i] = f;
            list.add(i, f);
        }

        Arrays.sort(arr);
        Collections.sort(list);

        // store and get
        storageBean.storeBatchToDatabase(list);

        ArrayList<FileEntry> got = (ArrayList<FileEntry>) entryBean.getAll();

        assertNotNull(got);
        assertEquals(list.size(), got.size());

        for (int i = 0; i < list.size(); i++) {
            assertTrue(got.contains(list.get(i)));
            assertEquals(got.get(i), entryBean.findById(got.get(i).getId()));
        }

        // clean up
        entryBean.remove(list.toArray(arr));
        for (FileEntry entry : arr) {
            for (FileCarImage fi : entry.getCarImages()) {
                Files.delete(fi.getFilepath());
            }
        }
        tool.close();
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

    private static void assertFileEntry(FileEntry e) {
        // FileEntry
        assertNotNull(e);
        assertNotNull(e.getCarData());
        assertNotNull(e.getCarImages());
        assertNotEquals(0, e.getCarImages().size());

        // CarData
        assertNotNull(e.getCarData().getAddress());
        assertNotNull(e.getCarData().getNumberPlate());
        assertNotNull(e.getCarData().getSpeed());

        // FileCarImage
        for (FileCarImage f : e.getCarImages()) {
            assertNotNull(f);
            assertNotNull(f.getFilepath());
            assertTrue(Files.exists(f.getFilepath()));
        }
    }
}