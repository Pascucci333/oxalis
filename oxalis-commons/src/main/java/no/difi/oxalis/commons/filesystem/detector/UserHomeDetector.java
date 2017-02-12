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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * @author erlend
 */
public class UserHomeDetector implements HomeDetector {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserHomeDetector.class);

    @Override
    public File detect() {
        String userHome = System.getProperty("user.home");
        File userHomeDir = new File(userHome);
        if (!userHomeDir.isDirectory()) {
            throw new IllegalStateException(userHome + " is not a directory");
        }
        File result;
        String relative_home = "/.oxalis";
        result = new File(userHomeDir, relative_home);
        if (result.exists()) {
            LOGGER.info("Using OXALIS_HOME relative to user.home " + relative_home + " as " + result);
            return result;
        }

        return null;
    }
}
