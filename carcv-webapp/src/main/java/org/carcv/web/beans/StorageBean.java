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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.carcv.core.model.file.FileEntry;

/**
 *
 */
@Stateless
public class StorageBean {

    @EJB
    private EntryBean entryBean;
    
    public static final String envVariable = "OPENSHIFT_DATA_DIR";

    private final String prefix = System.getenv(envVariable);

    private final Path inDir = Paths.get(prefix, "carcv_data", "in");

    private final Path outDir = Paths.get("prefix", "carcv_data", "out");

    private void assertDirCreated(Path p) {
        if (!Files.exists(p) || !Files.isDirectory(p)) {
            try {
                Files.createDirectories(p);
            } catch (IOException e) {
                System.err.println("Error while creating directories: " + p.toString());
                e.printStackTrace();
            }
        }
    }

    public Path getInputDirectory() {
        assertDirCreated(inDir);
        return inDir;
    }

    public Path getOutputDirectory() {
        assertDirCreated(outDir);
        return outDir;
    }

    public Path createBatchDirectory() throws IOException {
        assertDirCreated(inDir);
        assertDirCreated(outDir);
        return Files.createDirectory(Paths.get(inDir.toString(), "batch-" + System.currentTimeMillis()));
    }

    public void storeImageToDirectory(InputStream is, String fileName, Path dir) throws IOException {
        assertDirCreated(dir);
        Path file = Files.createFile(Paths.get(dir.toString(), fileName));

        saveToFile(is, Files.newOutputStream(file));
    }

    public void storeBatchToDatabase(List<FileEntry> list) {
        entryBean.create((FileEntry[]) list.toArray());
    }

    private void saveToFile(InputStream from, OutputStream outputStream) {
        try {

            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = from.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (from != null) {
                try {
                    from.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    // outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}