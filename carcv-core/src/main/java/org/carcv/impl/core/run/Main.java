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
package org.carcv.impl.core.run;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Properties;

import org.carcv.impl.core.recognize.FileCarRecognizer;

/**
 * Provides a demo command line implementation of CarCV Core functionality.
 */
public class Main {

    final private static String envHOME = System.getenv("HOME");

    private static Path inDir = Paths.get(envHOME + "/dev/java/carcv_data/in");
    private static Path outDir = Paths.get(envHOME + "/dev/java/carcv_data/out");

    private FileCarRecognizer recognizer;

    /**
     * A wrapper method that runs {@link FileCarRecognizer#recognize()} on the private FileCarRecognizer instance
     *
     * @throws IOException
     * @deprecated Unused, unneeded, to be removed ASAP
     */
    public void recognize() throws IOException {
        recognizer.recognize();
    }

    /**
     * Main method for demo command line implementation. The preferred way to invoke it is to run <code>mvn exec:java</code>
     * <p>
     * Possible arguments:
     * <ul>
     * <li>--out=OUTPUT_DIR
     * <li>--in=INPUT_DIR
     * </ul>
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        final String dashes = "-------------------------------------------------------------------------------";

        System.out.println();
        System.out.println(dashes);
        System.out.println("\tRunning CarCV Core demo command-line interface");
        System.out.println(dashes);
        System.out.println();

        for (int i = 0; i < args.length; i++) {
            if (args[i].startsWith("--out=")) {
                String out = args[i].split("--out=")[0];

                System.out.println("Parsed output directory:\t" + out);

                outDir = Paths.get(out);
            }
            if (args[i].startsWith("--in=")) {
                String in = args[i].split("--in=")[0];

                System.out.println("Parsed input directory:\t" + in);

                inDir = Paths.get(in);
            }
        }

        System.out.println("Assuming input directory is at:\t\t" + inDir.toString());
        System.out.println("Assuming output directory is at:\t" + outDir.toString());

        System.out.print("\nCreating demo info.properties... ");
        Properties props = createDemoProperties();
        DirectoryStream<Path> batchDirs = Files.newDirectoryStream(inDir);
        for (Path p : batchDirs) {
            Path infoFile = Paths.get(p.toString(), "info.properties");

            props.store(new FileOutputStream(infoFile.toFile()), "Demo info.properties");
        }
        System.out.println("done.");

        Main m = new Main();
        m.run();
    }

    /**
     * Actually goes through the whole recognition process a {@link FileCarRecognizer} normally would.
     *
     * @throws IOException
     */
    private void run() throws IOException {
        System.out.println("\nCreating FileCarRecognizer instance");
        recognizer = new FileCarRecognizer(inDir, outDir);

        System.out.print("\nRecognizing... ");
        recognizer.recognize();
        System.out.println("done.");

        System.out.println("\nFor results see the output directory:\t" + outDir.toString());
        System.out.println();
    }

    /**
     * Creates a sample Properties object to be used as <code>info.properties</code> in a input batch folder
     *
     * @return an example of a <code>info.properties</code> Properties object
     */
    private static Properties createDemoProperties() {
        Properties properties = new Properties();

        properties.setProperty("address-lat", Double.toString(48.5));
        properties.setProperty("address-long", Double.toString(17.8));
        properties.setProperty("address-city", "Bratislava");
        properties.setProperty("address-postalCode", "93221");
        properties.setProperty("address-street", "Hrušková");
        properties.setProperty("address-country", "Slovakia");
        properties.setProperty("address-streetNo", "32");
        properties.setProperty("address-refNo", "1010");
        properties.setProperty("timestamp", String.valueOf(new Date(System.currentTimeMillis()).getTime()));

        return properties;
    }

}
