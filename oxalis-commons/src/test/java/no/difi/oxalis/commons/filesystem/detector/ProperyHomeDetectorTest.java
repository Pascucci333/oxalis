/*
 * Copyright 2010-2017 Norwegian Agency for Public Management and eGovernment (Difi)
 *
 * Licensed under the EUPL, Version 1.1 or – as soon they
 * will be approved by the European Commission - subsequent
 * versions of the EUPL (the "Licence");
 *
 * You may not use this work except in compliance with the Licence.
 *
 * You may obtain a copy of the Licence at:
 *
 * https://joinup.ec.europa.eu/community/eupl/og_page/eupl
 *
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the Licence is
 * distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * See the Licence for the specific language governing
 * permissions and limitations under the Licence.
 */

package no.difi.oxalis.commons.filesystem.detector;

import no.difi.oxalis.api.filesystem.HomeDetector;
import org.testng.annotations.Test;

import java.io.File;

import static no.difi.oxalis.commons.filesystem.detector.PropertyHomeDetector.OXALIS_HOME_VAR_NAME;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

/**
 * @author erlend
 */
public class ProperyHomeDetectorTest {

    private HomeDetector homeDetector = new PropertyHomeDetector();

    @Test
    public void testFromJavaSystemProperty() {
        String path = new File("/some/system/path2").getAbsolutePath();
        String backup = System.getProperty(PropertyHomeDetector.OXALIS_HOME_VAR_NAME);

        try {
            System.setProperty(OXALIS_HOME_VAR_NAME, "");
            File oxalis_home = homeDetector.detect();
            assertNull(oxalis_home);

            System.setProperty(OXALIS_HOME_VAR_NAME, path);
            oxalis_home = homeDetector.detect();
            assertEquals(oxalis_home.getAbsolutePath(), path);
        } finally {
            if (backup == null) backup = ""; // prevent null pointer exception
            System.setProperty(OXALIS_HOME_VAR_NAME, backup);
        }
    }
}
