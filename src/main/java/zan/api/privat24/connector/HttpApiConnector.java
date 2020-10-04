package zan.api.privat24.connector;

import zan.api.privat24.ApplicationConfig;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;

/**
 * HTTP API connector, performs HTTP requests
 * @author azlatov
 */
public class HttpApiConnector {

    private final ApplicationConfig applicationConfig;

    public HttpApiConnector(ApplicationConfig applicationConfig, String login, String password) throws IOException {
        this(applicationConfig);
    }

    public HttpApiConnector(ApplicationConfig applicationConfig) throws IOException {
        this.applicationConfig = applicationConfig;
        checkApplicationConfig();
    }

    private void checkApplicationConfig() {
        if (applicationConfig.getServerUrl() == null || applicationConfig.getServerUrl().isEmpty()) {
            throw new IllegalArgumentException("Property [serverUrl] should be specified");
        }
    }

    /**
     * GET request
     * @param resource resource string
     * @return response
     * @throws IOException
     */
    public HttpApiResponse get(String resource) throws IOException {
        return doRequest("GET", resource, null);
    }

    /**
     * POST request
     * @param resource resource string
     * @param body body string
     * @return response
     * @throws IOException
     */
    public HttpApiResponse post(String resource, String body) throws IOException {
        return doRequest("POST", resource, body);
    }

    private HttpApiResponse doRequest(String method, String resource, String body) throws IOException {
        StringBuilder targetURLBuilder = new StringBuilder();
        targetURLBuilder.append(applicationConfig.getServerUrl());
        if (applicationConfig.getServerPort() > 0) {
            targetURLBuilder.append(":").append(applicationConfig.getServerPort());
        }
        targetURLBuilder.append(resource);
        String targetURL = targetURLBuilder.toString();

        boolean isHTTPS = applicationConfig.getServerUrl().startsWith("https://");
        HttpURLConnection connection = null;

        try {
            //Create connection
            URL url = new URL(targetURL);

            if (isHTTPS) {
                connection = (HttpsURLConnection) url.openConnection();
            } else {
                connection = (HttpURLConnection) url.openConnection();
            }

            connection.setRequestMethod(method);

            connection.setUseCaches(false);
            connection.setDoOutput(body != null);

            if (body != null) {
                connection.setRequestProperty("Content-Length",
                        Integer.toString(body.getBytes().length));

                try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
                    wr.writeBytes(body);
                }
            }

            //Get Response
            int responseCode = connection.getResponseCode();

            String response = null;
            try (InputStream is = getInputStream(connection);
                 BufferedReader rd = new BufferedReader(new InputStreamReader(is))) {
                response = rd.lines().collect(Collectors.joining("\n"));
            }

            return new HttpApiResponse(responseCode, response);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private InputStream getInputStream(HttpURLConnection connection) throws IOException {
        if (connection.getResponseCode() > 399) {
            return connection.getErrorStream();
        } else {
            return connection.getInputStream();
        }
    }
}
