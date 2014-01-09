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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.carcv.core.model.file.FileEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Handles storage (loading and saving) of images and Entries. Also provides easy database access for persistence.
 */
@Stateless
public class StorageBean {

    final private static Logger LOGGER = LoggerFactory.getLogger(StorageBean.class);

    @EJB
    private EntryBean entryBean;

    protected static final String envname_OPENSHIFT_DATA_DIR = "OPENSHIFT_DATA_DIR";
    protected static final String JbossDataDirProperty = "jboss.server.data.dir";
    private String prefix;
    private Path inDir;
    private Path outDir;

    @PostConstruct
    private void init() {
        String env = System.getenv(envname_OPENSHIFT_DATA_DIR);
        // if openshift data dir exists use it, else use jboss data dir
        prefix = env == null ? System.getProperty(JbossDataDirProperty) : env;

        inDir = Paths.get(prefix, "carcv_data", "in");
        outDir = Paths.get(prefix, "carcv_data", "out");

        // create directories
        getInputDirectory();
        getOutputDirectory();
    }

    /**
     * Returns the location of the data directory.
     * <p>
     * Note: The actual data resides in the <code>getPrefix()+"/carcv_data"</code> directory.
     *
     * @return the prefix
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * Checks if the directory exists, if not, creates it. Prints an error if an error during creating occurred, doesn't throw
     * any Exception.
     *
     * @param p the Path to check
     */
    private void assertDirCreated(Path p) throws IOException {
        if (!Files.exists(p) || !Files.isDirectory(p)) {
            Files.createDirectories(p);
        }
    }

    /**
     * Makes sure the input directory exists and returns it's path.
     *
     * @see #assertDirCreated(Path)
     * @return the input directory Path
     */
    public Path getInputDirectory() {
        try {
            assertDirCreated(inDir);
        } catch (IOException e) {
            LOGGER.error("Error during input directory creation: " + e.getMessage());
            e.printStackTrace();
        }
        return inDir;
    }

    /**
     * Makes sure the output directory exists and returns it's path.
     *
     * @see #assertDirCreated(Path)
     * @return the output directory Path
     */
    public Path getOutputDirectory() {
        try {
            assertDirCreated(outDir);
        } catch (IOException e) {
            LOGGER.error("Error during input directory creation: " + e.getMessage());
            e.printStackTrace();
        }
        return outDir;
    }

    /**
     * Creates a new batch directory for storing input images. Also assures that the input and output directories exist.
     *
     * @return Path of the new batch directory
     * @throws IOException if an error during the creation occurrs
     */
    public Path createBatchDirectory() throws IOException {
        assertDirCreated(inDir);
        assertDirCreated(outDir);
        return Files.createDirectory(Paths.get(inDir.toString(), "batch-" + System.currentTimeMillis()));
    }

    /**
     * Reads InputStream and stores it to the <code>dir</code> with the name <code>fileName</code>.
     *
     * @param is the InputStream from which to read
     * @param fileName the file name of the saved file
     * @param dir the directory to which to save the file
     * @see #saveToFile(InputStream, OutputStream)
     * @throws IOException if an error occurs
     */
    public void storeToDirectory(InputStream is, String fileName, Path dir) throws IOException {
        assertDirCreated(dir);
        Path file = Files.createFile(Paths.get(dir.toString(), fileName));

        saveToFile(is, Files.newOutputStream(file));
    }

    /**
     * Persists all members of the list to the database.
     *
     * @see EntryBean#persist(FileEntry...)
     * @param list the list of FileEntries to store
     */
    public void storeBatchToDatabase(final List<FileEntry> list) {
        FileEntry[] array = new FileEntry[list.size()];
        entryBean.persist(list.toArray(array));
    }

    /**
     * Reads from the InputStream and writes to the OutputStream. Used for writing streams to files.
     *
     * @param from the InputStream to read from
     * @param to the OutputStream to write to
     * @throws IOException if an error during read/write occurs
     */
    private void saveToFile(InputStream from, OutputStream to) throws IOException {
        // try
        int read;
        byte[] bytes = new byte[1024];

        while ((read = from.read(bytes)) != -1) {
            to.write(bytes, 0, read);
        }

        // finally

        if (from != null) {
            from.close();
        }
        if (to != null) {
            // outputStream.flush();
            to.close();
        }
    }
}