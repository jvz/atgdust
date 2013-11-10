/*
 * Copyright 2013 Matt Sicker and Contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 *
 */
package atg.test.configuration;

import atg.nucleus.Nucleus;
import atg.nucleus.logging.PrintStreamLogger;
import atg.service.lockmanager.ClientLockManager;
import atg.test.util.FileUtil;
import atg.xml.tools.apache.ApacheXMLToolsFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * <i>This class is a merger of atg.test.util.DBUtils and
 * atg.adapter.gsa.GSATestUtils. The result will hopefully be a class that just
 * has the bare minimums needed for testing against an existing and/or in-memory
 * database.</i>
 * <p>
 * This class will created all properties files needed for non repository based
 * tests.
 * </p>
 *
 * @author robert
 */
public final class BasicConfiguration {

    protected String isDebug = Boolean.FALSE.toString();

    protected final Map<String, String> settings = new HashMap<String, String>();

    private static Logger log = LogManager.getLogger();

    public void setDebug(final boolean isDebug) {
        this.isDebug = Boolean.toString(isDebug);
    }

    public BasicConfiguration() {
        super();
    }

    /**
     * @param root
     *
     * @throws IOException
     */
    public void createPropertiesByConfigurationLocation(final File root)
            throws IOException {

        this.createClientLockManager(root);
        this.createGlobal(root);
        this.createInitialServices(root);
        this.createScreenLog(root);
        this.createXMLToolsFactory(root);

        log.info("Created basic configuration fileset");

    }

    /**
     * @param root
     *
     * @throws IOException
     */
    private void createClientLockManager(final File root)
            throws IOException {
        this.settings.clear();
        settings.put("lockServerAddress", "localhost");
        settings.put("lockServerPort", "9010");
        settings.put("useLockServer", "false");
        FileUtil.createPropertyFile(
                "ClientLockManager", new File(
                root.getAbsolutePath() + "/atg/dynamo/service"
        ), ClientLockManager.class, settings
        );
    }

    /**
     * @param root
     *
     * @throws IOException
     */
    private void createGlobal(final File root)
            throws IOException {
        this.settings.clear();
        settings.put("logListeners", "/atg/dynamo/service/logging/ScreenLog");
        settings.put("loggingDebug", isDebug);
        FileUtil.createPropertyFile(
                "GLOBAL", new File(root.getAbsolutePath() + "/"), null, settings
        );

    }

    /**
     * Creates initial services properties like Initial, AppServerConfig, Nucleus,
     * etc, etc.
     *
     * @param root
     *
     * @throws IOException
     */
    private void createInitialServices(final File root)
            throws IOException {
        this.settings.clear();
        settings.put("initialServiceName", "/Initial");
        FileUtil.createPropertyFile("Nucleus", root, Nucleus.class, settings);
    }

    /**
     * @param root
     *
     * @throws IOException
     */
    private void createScreenLog(final File root)
            throws IOException {

        this.settings.clear();
        settings.put("cropStackTrace", "false");
        settings.put("loggingEnabled", isDebug);
        FileUtil.createPropertyFile(
                "ScreenLog", new File(
                root.getAbsolutePath() + "/atg/dynamo/service/logging"
        ), PrintStreamLogger.class, settings
        );
    }

    /**
     * @param root
     *
     * @throws IOException
     */
    private void createXMLToolsFactory(final File root)
            throws IOException {
        FileUtil.createPropertyFile(
                "XMLToolsFactory", new File(
                root.getAbsolutePath() + "/atg/dynamo/service/xml"
        ), ApacheXMLToolsFactory.class, new HashMap<String, String>()
        );
    }

}
