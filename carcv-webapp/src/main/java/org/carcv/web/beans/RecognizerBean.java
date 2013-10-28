/*
 * Copyright 2012 CarCV Development Team
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
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.ejb.Stateless;

import org.carcv.impl.core.recognize.FileCarRecognizer;

/**
 *
 */
@Stateless
public class RecognizerBean {

    // TODO 3 Somehow load these paths from a configuration or something else
    final private static Path inDir = Paths.get("/home/oskopek/dev/java/carcv_data/in");
    final private static Path outDir = Paths.get("/home/oskopek/dev/java/carcv_data/out");

    private FileCarRecognizer recognizer = new FileCarRecognizer(inDir, outDir);

    public void recognize() throws IOException {
        recognizer.recognize();
    }

}