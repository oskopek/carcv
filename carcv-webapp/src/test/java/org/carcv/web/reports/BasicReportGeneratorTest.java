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

package org.carcv.web.reports;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Date;

import net.sf.jasperreports.engine.JRException;

import org.carcv.core.model.Address;
import org.carcv.core.model.CarData;
import org.carcv.core.model.NumberPlate;
import org.carcv.core.model.Speed;
import org.carcv.core.model.SpeedUnit;
import org.carcv.core.model.file.FileCarImage;
import org.carcv.core.model.file.FileEntry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class BasicReportGeneratorTest {

    final private static Logger LOGGER = LoggerFactory.getLogger(BasicReportGeneratorTest.class);

    private static FileEntry testEntry = null;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        Speed speed = new Speed(80d, SpeedUnit.KPH);

        Address location = new Address("Myjava", "90701", "Jablonská", "Slovakia", 27, 860);

        NumberPlate licencePlate = new NumberPlate("MY-077AU", "SK");

        Date timestamp = new Date(System.currentTimeMillis());

        CarData carData = new CarData(speed, location, licencePlate, timestamp);

        String imageResource = "/reports/OpenCV_Logo_with_text.png";

        Path imagePath = Files.createTempFile("testImage-", ".png");
        Files.copy(getClass().getResourceAsStream(imageResource), imagePath, StandardCopyOption.REPLACE_EXISTING);

        assertTrue(Files.exists(imagePath));
        assertTrue(Files.isRegularFile(imagePath));

        testEntry = new FileEntry(carData, Arrays.asList(new FileCarImage(imagePath)));
        testEntry.setId(0l);

        assertNotNull(testEntry.getCarImages().get(0).getFilepath());
        assertNotNull(testEntry.getId());
    }

    @After
    public void tearDown() throws IOException {
        Files.delete(testEntry.getCarImages().get(0).getFilepath());
    }

    /**
     * Test method for
     * {@link org.carcv.web.reports.BasicReportGenerator#buildPDFReport(org.carcv.core.model.AbstractEntry, java.lang.String, java.lang.String, java.lang.String)}
     * .
     *
     * @throws JRException
     */
    @Test
    public void testBuildPDFReport() throws JRException {
        URL testDir = getClass().getResource("/");

        File test_results_dir = new File(testDir.getPath() + "/test_results/");
        if (!test_results_dir.exists() || !test_results_dir.isDirectory()) {
            assertTrue(test_results_dir.mkdir());
        }
        LOGGER.info("OutDir: {}", test_results_dir.getPath());

        String filename = testDir.getPath() + "/test_results/report" + System.currentTimeMillis() + ".pdf";

        assertNotNull(testEntry.getCarImages().get(0).getFilepath());
        assertTrue(Files.exists(testEntry.getCarImages().get(0).getFilepath()));
        assertTrue(Files.isRegularFile(testEntry.getCarImages().get(0).getFilepath()));

        BasicReportGenerator brg = new BasicReportGenerator(testEntry, "/reports/speed_report.jasper", "Myjava",
            "TestReport", "https://carcv-oskopek.rhcloud.com");

        brg.exportFile(filename);
    }
}