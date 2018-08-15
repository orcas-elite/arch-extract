package de.rss.arch_extract.input_output;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import de.rss.arch_extract.resources.ServiceDependency;
import de.rss.arch_extract.resources.trace.Process;
import de.rss.arch_extract.resources.trace.Reference;
import de.rss.arch_extract.resources.trace.Span;
import de.rss.arch_extract.resources.trace.Trace;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class InputParser {

    private Gson gson;
    private static final String DATA_ARRAY = "data";

    public InputParser() {
        gson = new Gson();
    }

    /**
     * Parse the json string to obtain the service names.
     *
     * @param json
     * @return
     */
    public List<String> getServiceNames(String json) {
        List<String> serviceNames = new ArrayList<String>();

        // Read the service names and save them in a list
        try {
            JsonObject root = gson.fromJson(json, JsonObject.class);
            JsonArray services = root.getAsJsonArray(DATA_ARRAY);

            for (JsonElement service : services) {
                String serviceName = service.getAsString();
                serviceNames.add(serviceName);
            }
        } catch (Exception e) {
            System.out.println("Exception while extracting service names from json");
        }

        // Check that the list is not empty and delete the "jaeger-query" service name that is automatically created by
        // jaeger
        if (!serviceNames.isEmpty()) {
            Iterator<String> iter = serviceNames.iterator();

            while (iter.hasNext()) {
                String str = iter.next();
                if (str.equals("jaeger-query")) {
                    iter.remove();
                }
            }
            return serviceNames;
        }

        return null;
    }

    /**
     * Parse the json string to obtain the operation names for the passed service.
     *
     * @param json
     * @param service
     * @return
     */
    public List<String> getOperationNames(String json, String service) {
        List<String> operationNames = new ArrayList<String>();

        // Read the operation names and save them in a list
        try {
            JsonObject root = gson.fromJson(json, JsonObject.class);
            JsonArray operations = root.getAsJsonArray(DATA_ARRAY);

            for (JsonElement operation : operations) {
                String operationName = operation.getAsString();
                operationNames.add(operationName);
            }
        } catch (Exception e) {
            System.out.println("Exception while extracting operation names for service " + service + " from json");
        }

        // Check that list is not empty
        if (!operationNames.isEmpty()) {
            return operationNames;
        }

        return null;
    }

    /**
     * Parse the json string to obtain the service dependencies
     *
     * @param json
     * @return
     */
    public List<ServiceDependency> getServiceDependencies(String json) {
        List<ServiceDependency> serviceDependencies = new ArrayList<ServiceDependency>();

        // Read the dependencies and save them in a list
        try {
            JsonObject root = gson.fromJson(json, JsonObject.class);
            JsonArray dependencies = root.getAsJsonArray(DATA_ARRAY);

            for (JsonElement dependency : dependencies) {
                String parent = dependency.getAsJsonObject().get("parent").getAsString();
                String child = dependency.getAsJsonObject().get("child").getAsString();
                serviceDependencies.add(new ServiceDependency(parent, child));
            }
        } catch (Exception e) {
            System.out.println("Exception while extracting service dependencies from json");
        }

        // Check that list is not empty
        if (!serviceDependencies.isEmpty()) {
            return serviceDependencies;
        }

        return null;
    }

    public List<Trace> getTraces(String json, String serviceName) {
        List<Trace> traces = new ArrayList<Trace>();

        // Read the traces and save them in a list
        try {
            JsonObject root = gson.fromJson(json, JsonObject.class);
            JsonArray data = root.getAsJsonArray(DATA_ARRAY);

            // Iterate over traces
            for (JsonElement traceElement : data) {
                JsonObject trace = traceElement.getAsJsonObject();

                // Set traceID
                Trace newTrace = new Trace(trace.get("traceID").getAsString());

                JsonArray spanArray = trace.getAsJsonArray("spans");

                // Iterate over spans
                for (JsonElement spanElement : spanArray) {
                    JsonObject span = spanElement.getAsJsonObject();

                    // Set traceID, spanID and operationName
                    Span newSpan = new Span(span.get("traceID").getAsString(), span.get("spanID").getAsString());
                    newSpan.setOperationName(span.get("operationName").getAsString());

                    // Get references
                    JsonArray referenceArray = span.getAsJsonArray("references");
                    if (referenceArray.size() > 0) {
                        Reference reference = new Reference();
                        JsonObject referenceObject = referenceArray.get(0).getAsJsonObject();
                        reference.setRefType(referenceObject.get("refType").getAsString());
                        reference.setTraceID(referenceObject.get("traceID").getAsString());
                        reference.setSpanID(referenceObject.get("spanID").getAsString());

                        // Set reference
                        newSpan.setReference(reference);
                    }

                    // Get circuit breaker
                    JsonArray tags = span.getAsJsonArray("tags");
                    for (JsonElement tagElement : tags) {
                        JsonObject tag = tagElement.getAsJsonObject();
                        String key = tag.get("key").getAsString();

                        if (key.equals("pattern.circuitBreaker")) {
                            boolean value = tag.get("value").getAsBoolean();
                            newSpan.setCircuitBreaker(value);
                        } else if (key.equals("pattern.circuitBreaker.fallback")) {
                            boolean value = tag.get("value").getAsBoolean();
                            newSpan.setCircuitBreakerFallback(value);
                        }
                    }

                    // Get processID
                    newSpan.setProcessID(span.get("processID").getAsString());

                    // Add span
                    newTrace.addSpan(newSpan);
                }

                // Create process map
                JsonObject processes = trace.get("processes").getAsJsonObject();
                List<Process> processList = new ArrayList<Process>();

                boolean hasNext = true;
                int i = 1;
                while (hasNext) {
                    String processName = "p" + String.valueOf(i);
                    try {
                        String service = processes.get(processName).getAsJsonObject().get("serviceName").getAsString();
                        String hostName = processes.get(processName).getAsJsonObject().get("tags").getAsJsonArray().get(0).getAsJsonObject().get("value").getAsString();
                        Process process = new Process(processName, service, hostName);
                        processList.add(process);
                    } catch (Exception e) {
                        hasNext = false;
                    }
                    ++i;
                }

                // Set serviceName for each span in this trace
                for (Span span : newTrace.getSpans()) {
                    String sName = null;
                    String hostName = null;

                    for (Process process : processList) {
                        if (process.getProcessId().equals(span.getProcessID())) {
                            sName = process.getServiceName();
                            hostName = process.getHostName();
                            span.setServiceName(sName);
                            span.setHostName(hostName);
                        }
                    }
                }

                // Add trace to traces list
                traces.add(newTrace);
            }
        } catch (Exception e) {
            System.out.println("Exception while extracting traces for service " + serviceName + " from json");
        }

        // Check that list is not empty
        if (!traces.isEmpty()) {
            return traces;
        }

        return null;
    }
}
