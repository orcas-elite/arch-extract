package de.rss.arch_extract.input_output;

import de.rss.arch_extract.Extractor;
import de.rss.arch_extract.resources.trace.Trace;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class NetworkManager implements InputManager {

    private static final String SERVICES_URL = "http://localhost:16686/api/services";
    private static final String TRACES_BASE_URL = "http://localhost:16686/api/traces?service=";
    private static final String DEPENDENCIES_BASE_URL = "http://localhost:16686/api/dependencies";
    private static final String OPERATIONS_QUERY = "operations";
    private static final String ENDTS_QUERY = "endTs=";
    private static final String LOOKBACK_QUERY = "lookback=";

    /**
     * Execute a HTTP GET request to the passed URL and return the body of the response.
     *
     * @param url
     * @return
     * @throws IOException
     */
    private String executeGetRequest(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();

        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            System.out.println("GET request to " + url + " was not successful");
            return null;
        }
    }

    /**
     * Get the service names from jaeger.
     *
     * @return
     */
    public List<String> getServices() {
        String json = "";
        InputParser inputParser = new InputParser();

        // Get the json string, that contains the service names, from jaeger
        try {
            json = executeGetRequest(SERVICES_URL);
        } catch (IOException e) {
            System.out.println("Request to get service names failed");
        }

        if (Extractor.createBackup) {
            FileManager fileMan = new FileManager();
            fileMan.writeServiceFile(json);
        }

        // Parse the json string to obtain the service names and return them in a list.
        return inputParser.getServiceNames(json);
    }

    /**
     * Get the operation names for the passed service from jaeger.
     *
     * @param service
     * @return
     */
    public List<String> getOperations(String service) {
        String json = "";
        InputParser inputParser = new InputParser();

        // Get the json string that contains the service names from jaeger
        try {
            String urlString = SERVICES_URL + "/" + service + "/" + OPERATIONS_QUERY;
            json = executeGetRequest(urlString);
        } catch (IOException e) {
            System.out.println("Request to get operation names for service " + service + " failed");
        }

        if (Extractor.createBackup) {
            FileManager fileMan = new FileManager();
            fileMan.writeOperationsFile(service, json);
        }

        return inputParser.getOperationNames(json, service);
    }

    /**
     * Get the json string from jaeger that contains the dependencies between the services.
     *
     * @return
     */
    public String getServiceDependencies() {
        String json = "";

        String time = String.valueOf(new Date().getTime());
        String lookback = "604800000"; // four weeks, milliseconds

        // Get the json string that contains the service dependencies from jaeger
        try {
            String urlString = DEPENDENCIES_BASE_URL + "?" + ENDTS_QUERY + time + "&" + LOOKBACK_QUERY + lookback;
            json = executeGetRequest(urlString);
        } catch (IOException e) {
            System.out.println("Request to get service dependencies failed");
        }

        // Return null if the string is empty
        if (!json.equals("")) {
            return json;
        }

        return null;
    }

    /**
     * Get the json string from jaeger that contains the traces for the passed service.
     *
     * @param serviceName
     * @return
     */
    public List<Trace> getTraces(String serviceName) {
        String json = "";
        InputParser inputParser = new InputParser();

        // Get the json string, that contains the traces, from jaeger
        try {
            String urlString = TRACES_BASE_URL + serviceName;
            json = executeGetRequest(urlString);
        } catch (IOException e) {
            System.out.println("Request to get traces for service " + serviceName + " failed");
        }

        if (Extractor.createBackup) {
            FileManager fileMan = new FileManager();
            fileMan.writeTraceFile(serviceName, json);
        }

        return inputParser.getTraces(json, serviceName);
    }

}
