package de.rss.arch_extract.resources.trace;

public class Span {

    private String traceID;
    private String spanID;
    private String operationName;
    private Reference reference;
    private String serviceName;
    private String hostName;
    private String processID;
    private boolean circuitBreaker;
    private boolean circuitBreakerFallback;

    public Span(String traceID, String spanID) {
        this.traceID = traceID;
        this.spanID = spanID;
        this.circuitBreaker = false;
    }

    public String getTraceID() {
        return traceID;
    }

    public void setTraceID(String traceID) {
        this.traceID = traceID;
    }

    public String getSpanID() {
        return spanID;
    }

    public void setSpanID(String spanID) {
        this.spanID = spanID;
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public Reference getReference() {
        return reference;
    }

    public void setReference(Reference reference) {
        this.reference = reference;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getProcessID() {
        return processID;
    }

    public void setProcessID(String processID) {
        this.processID = processID;
    }

    public boolean isCircuitBreaker() {
        return circuitBreaker;
    }

    public void setCircuitBreaker(boolean circuitBreaker) {
        this.circuitBreaker = circuitBreaker;
    }

    public boolean isCircuitBreakerFallback() {
        return circuitBreakerFallback;
    }

    public void setCircuitBreakerFallback(boolean circuitBreakerFallback) {
        this.circuitBreakerFallback = circuitBreakerFallback;
    }
}
