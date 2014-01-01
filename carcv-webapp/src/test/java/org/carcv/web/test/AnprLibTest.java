/*
 * Copyright 2012-2014 CarCV Development Team
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

package org.carcv.web.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import net.sf.javaanpr.imageanalysis.CarSnapshot;
import net.sf.javaanpr.intelligence.Intelligence;

import org.junit.Test;
import org.xml.sax.SAXException;

/**
 * Test case for checking if the ANPR library (JavaANPR) works as expected.
 */
public class AnprLibTest {

    @Test
    public void anprLibTest() throws IOException, ParserConfigurationException, SAXException {
        Intelligence intel = new Intelligence();
        assertNotNull(intel);

        String spz = intel.recognize(new CarSnapshot("/img/skoda_oct.jpg"));
        assertEquals("2SU358F", spz); // actually 2SU358F
    }
}