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

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.ejb.EJB;

import org.carcv.core.io.DirectoryWatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Test class for {@link StorageBean}
 */
public class StorageBeanTest { // TODO 1 Fill the StorageBeanTest

    @EJB
    private StorageBean storageBean;

    private Path rootDir;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        Path p = Files.createTempDirectory("storageBeanDataDir");
        Process setenv = Runtime.getRuntime().exec("export " + StorageBean.envVariable + "=" + p.toAbsolutePath().toString()); // TODO
                                                                                                                               // 1
                                                                                                                               // How
                                                                                                                               // to
                                                                                                                               // solve
                                                                                                                               // this?
        try {
            setenv.waitFor();
        } catch (InterruptedException e) {
            System.err.println("Failed to set env. variable " + StorageBean.envVariable + "=" + p.toAbsolutePath().toString());
            e.printStackTrace();
        }
        rootDir = p;
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
     * Test method for
     * {@link org.carcv.web.beans.StorageBean#storeImageToDirectory(java.io.InputStream, java.lang.String, java.nio.file.Path)}.
     */
    @Test
    @Ignore
    public void testStoreImageToDirectory() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link org.carcv.web.beans.StorageBean#storeBatchToDatabase(java.util.List)}.
     */
    @Test
    @Ignore
    public void testStoreBatchToDatabase() {
        fail("Not yet implemented");
    }

}
