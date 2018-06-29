package de.rss.arch_extract;

import de.rss.arch_extract.input_output.FileManager;
import de.rss.arch_extract.input_output.InputManager;
import de.rss.arch_extract.input_output.NetworkManager;
import de.rss.arch_extract.input_output.OutputParser;
import de.rss.arch_extract.resources.arch_model.*;
import de.rss.arch_extract.resources.trace.Reference;
import de.rss.arch_extract.resources.trace.Span;
import de.rss.arch_extract.resources.trace.Trace;

import java.util.ArrayList;
import java.util.List;

public class Extractor {

    private List<Microservice> services;
    public static boolean createBackup = false;
    public static boolean useBackup = false;

    public Extractor() {
        services = new ArrayList<Microservice>();
    }

    public static class Parent {
        Microservice service;
        Operation operation;
        Span span;

        public Parent(Microservice service, Operation operation, Span span) {
            this.service = service;
            this.operation = operation;
            this.span = span;
        }

        public Microservice getService() {
            return service;
        }

        public void setService(Microservice service) {
            this.service = service;
        }

        public Operation getOperation() {
            return operation;
        }

        public void setOperation(Operation operation) {
            this.operation = operation;
        }

        public Span getSpan() {
            return span;
        }

        public void setSpan(Span span) {
            this.span = span;
        }
    }

    public void getMicroservices() {
        InputManager man = createInputManager();
        List<String> serviceNames = man.getServices();
        if (serviceNames != null && !(serviceNames.isEmpty())) {
            for (String name : serviceNames) {
                Microservice service = new Microservice(name);
                service.setInstances(1);
                service.setCapacity(1000);
                services.add(service);
            }
        }
    }

    public void getOperations(Microservice service) {
        if (service.getName() == null) {
            return;
        }
        InputManager man = createInputManager();
        List<String> operationNames = man.getOperations(service.getName());
        for (String name : operationNames) {
            if (!name.equals("GET") && !name.equals("errorHtml")) {
                Operation op = new Operation(name);
                op.setDemand(100);
                service.addOperation(op);
            }
        }
    }

    public void getDependencies(Microservice service) {
        if (service == null) {
            System.out.println("Can't get dependencies because service is null");
            return;
        }
        if (service.getName() == null) {
            System.out.println("Can't get dependencies because service doesn't have a name");
            return;
        }

        InputManager man = createInputManager();
        List<Trace> traces = man.getTraces(service.getName());

        if (traces == null || traces.isEmpty()) {
            System.out.println("Can't get dependencies for service " + service.getName() + " because no traces were retrieved for this service");
            return;
        }

        for (Trace trace : traces) {
            if ((trace == null) || (trace.getSpans() == null)) {
                System.out.println("Can't get dependency for service " + service.getName() + " because trace is null or doesn't contain spans");
                continue;
            }

            List<Span> spans = trace.getSpans();

            if ((spans == null) || spans.isEmpty()) {
                System.out.println("Can't get dependency for service " + service.getName() + " because trace " + trace.getTraceID() + " doesn't contain spans");
                continue;
            }

            for (Span span : spans) {
                if ((span == null) || (span.getServiceName() == null)) {
                    System.out.print("Can't get a dependencies for service " + service.getName() + " because a span is null");
                    continue;
                }

                if (span.getOperationName().equals("GET") || span.getOperationName().equals("errorHtml")) {
                    continue;
                }

                // Check for circuit breaker
                if (span.isCircuitBreaker()) {
                    String operationName = span.getOperationName();
                    Microservice microservice = findService(span.getServiceName());
                    Operation operation = findOperation(operationName, microservice);
                    operation.setCircuitBreaker(new CircuitBreaker());
                }

                boolean spanMatchesService = span.getServiceName().equals(service.getName());
                boolean spanHasReference = (span.getReference() != null);

                if (spanMatchesService && spanHasReference) {
                    Dependency dependency = new Dependency();
                    dependency.setService(span.getServiceName());
                    dependency.setOperation(span.getOperationName());
                    dependency.setProbability(1.0f);

                    Parent parent = getParent(span, traces);

                    if (parent != null) {
                        Microservice parentService = parent.getService();
                        Operation parentOperation = parent.getOperation();

                        if (!parentOperation.containsDependency(dependency)) {
                            parentOperation.addDependency(dependency);
                        }
                    }
                }
            }
        }
    }

    private Parent getParent(Span span, List<Trace> traces) {

        Reference ref = span.getReference();
        Trace refTrace = null;
        Span refSpan = null;

        if ((ref.getTraceID() != null) && (ref.getSpanID() != null)) {
            refTrace = findTrace(ref.getTraceID(), traces);
            if (refTrace != null) {
                refSpan = findSpan(ref.getTraceID(), ref.getSpanID(), refTrace.getSpans());

                if (refSpan != null) {
                    String parentServiceName = refSpan.getServiceName();
                    String parentOperationName = refSpan.getOperationName();

                    if (parentOperationName.equals("GET")) {
                        return getParent(refSpan, traces);
                    }

                    Microservice parentService = findService(parentServiceName);
                    Operation parentOperation = findOperation(parentOperationName, parentService);

                    if ((parentService != null) && (parentOperation != null)) {
                        return new Parent(parentService, parentOperation, refSpan);
                    }
                }
            }
        }

        return null;
    }

    private Operation findOperation(String operationName, Microservice service) {
        if (service != null && operationName != null) {
            List<Operation> operations = service.getOperations();
            for (Operation operation : operations) {
                if (operation.getName().equals(operationName)) {
                    return operation;
                }
            }
        }
        return null;
    }

    private Microservice findService(String serviceName) {
        if (serviceName != null) {
            for (Microservice ms : services) {
                if (ms.getName().equals(serviceName)) {
                    return ms;
                }
            }
        }
        return null;
    }

    private Trace findTrace(String traceID, List<Trace> traces) {
        for (Trace trace : traces) {
            if (trace.getTraceID().equals(traceID)) {
                return trace;
            }
        }
        return null;
    }

    private Span findSpan(String traceID, String spanID, List<Span> spans) {
        for (Span span : spans) {
            if ((span.getTraceID().equals(traceID)) && (span.getSpanID().equals(spanID))) {
                return span;
            }
        }
        return null;
    }

    public List<Microservice> getServices() {
        return services;
    }

    public void setServices(List<Microservice> services) {
        this.services = services;
    }

    private void readArgs(String[] args) {
        if (args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                if (args[i].equals("-b")) {
                    createBackup = true;
                } else if (args[i].equals("-l")) {
                    useBackup = true;
                }
            }
            if (useBackup) {
                createBackup = false;
            }
        }
    }

    private InputManager createInputManager() {
        if (useBackup) {
            return new FileManager();
        } else {
            return new NetworkManager();
        }
    }

    public static void main(String[] args) {
        Extractor extractor = new Extractor();

        // Read args to determine if a backup should be made or if the data should be read from local files
        extractor.readArgs(args);

        System.out.println("Create Backup: " + Extractor.createBackup);
        System.out.println("Use local files: " + Extractor.useBackup);
        System.out.println();
        System.out.println("Getting services ...");

        // Get Microservices
        extractor.getMicroservices();

        // Get operations for every service
        for (Microservice ms : extractor.getServices()) {
            System.out.println("Getting operations for service " + ms.getName() + " ...");
            extractor.getOperations(ms);
        }

        // Operation dependencies for every service
        for (Microservice ms : extractor.getServices()) {
            System.out.println("Getting operation dependencies for service " + ms.getName() + " ...");
            extractor.getDependencies(ms);
        }

        // Create architecture model and write it in a file
        if (!(extractor.getServices().isEmpty())) {
            Architecture arch = new Architecture(extractor.getServices());
            OutputParser outputParser = new OutputParser();
            outputParser.writeArchitectureModel(arch);
        }

        System.out.println("Finished creating architecture_model.json file");
    }

}
