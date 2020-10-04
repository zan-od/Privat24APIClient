package zan.api.privat24;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Application configuration holder
 * Loads properties from "application.properties" files
 * @author azlatov
 */
public class ApplicationConfig {
    /**
     * Default (production) environment name
     */
    public static final String ENVIRONMENT_DEFAULT = "default";

    /**
     * Test environment name
     */
    public static final String ENVIRONMENT_TEST = "test";

    private static final String APPLICATION_PROPERTIES = "application.properties";
    private static final String TEST_PROPERTIES = "application-test.properties";

    /**
     * API server URL
     */
    private String serverUrl;

    /**
     * API server port (optional)
     */
    private int serverPort;

    public ApplicationConfig(String environment) throws IOException {
        
        if (ENVIRONMENT_TEST.equalsIgnoreCase(environment)) {
            loadProperties(TEST_PROPERTIES);
        } else {
            loadProperties(APPLICATION_PROPERTIES);
        }
    }

    private void loadProperties(String filename) throws IOException {
        try (InputStream input = ApplicationConfig.class.getClassLoader().getResourceAsStream(filename)) {

            if (input == null) {
                throw new FileNotFoundException("Unable to find " + filename + " resource");
            }

            Properties prop = new Properties();
            prop.load(input);

            serverUrl = prop.getProperty("api.server.url");

            String portNumber = prop.getProperty("api.server.port");
            if (portNumber != null && !portNumber.isEmpty()) {
                serverPort = Integer.parseInt(portNumber);
            }
        }
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public int getServerPort() {
        return serverPort;
    }
}
