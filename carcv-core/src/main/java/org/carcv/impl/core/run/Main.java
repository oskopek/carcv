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

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.carcv.impl.core.recognize.FileCarRecognizer;

/**
 *
 */
public class Main {
    
    private static Path inDir = Paths.get("/home/oskopek/dev/java/carcv_data/in");
    private static Path outDir = Paths.get("/home/oskopek/dev/java/carcv_data/out");

    private FileCarRecognizer recognizer;

    public void recognize() throws IOException {
        recognizer.recognize();
    }

    /**
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        for(int i = 0; i < args.length; i++) {
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
        Main m = new Main();
        m.run();
    }
    
    public void run() throws IOException {
        System.out.println("Creating FileCarRecognizer instance");
        recognizer = new FileCarRecognizer(inDir, outDir);
        
        System.out.print("Recognizing... ");
        recognizer.recognize();
        
        System.out.println("Done.");
        System.out.println("\nSee the results at: " + outDir.toString());
    }

}
