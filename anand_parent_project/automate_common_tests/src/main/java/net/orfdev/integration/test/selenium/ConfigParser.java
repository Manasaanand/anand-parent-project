package net.orfdev.integration.test.selenium;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class ConfigParser {
    public static String browser;
    public static String domain;
    public static String env;
    public static Properties ENV = new Properties();
    public static Properties OR = new Properties();
    public Properties CONFIG = null;
    private static final Logger log = LogManager.getLogger(browser, null);
    static String projectName;

    public static void readConfig(String projectname) throws FileNotFoundException {

        projectName = projectname;
        loadProperty("ENV.properties", ENV);
        if (ENV.containsKey("ENV")) {
            // log.debug(ENV.getProperty("ENV"));
            switch (ENV.getProperty("ENV").toLowerCase()) {
                case "staging_healthcheck":
                    loadProperty("Staging.properties", OR);
                    env = ENV.getProperty("ENV");
                    browser = ENV.getProperty("BROWSER");
                    domain = OR.getProperty(projectname);
                    log.debug("Staging");
                    return;
                case "production_healthcheck":
                    loadProperty("Production.properties", OR);
                    // log.debug("Production");
                    env = ENV.getProperty("ENV");
                    browser = ENV.getProperty("BROWSER");
                    domain = OR.getProperty(projectname);
                    return;
                case "glf_stag":
                    loadProperty("GLF_STAGING.properties", OR);
                    env = ENV.getProperty("ENV");
                    browser = ENV.getProperty("BROWSER");
                    domain = OR.getProperty(projectname);
                    return;

            }
        }

        throw new FileNotFoundException();

    }

    private static void loadProperty(String propertyFileName, Properties properties) {
        log.debug(String.format("Load properties file %s.", propertyFileName));
        InputStream inputStream = ConfigParser.class.getResourceAsStream(propertyFileName);
        try {
            properties.load(inputStream);

        } catch (IOException e) {
            log.debug("Cannot load properties file.");
            e.printStackTrace();
        }
    }

    public static String getDomain(String projectname) {
        if (domain == null) {
            try {
                readConfig(projectname);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return domain;
    }

    public static String getBrowser() {
        if (browser == null) {
            try {
                readConfig(projectName);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return browser;
    }

}
