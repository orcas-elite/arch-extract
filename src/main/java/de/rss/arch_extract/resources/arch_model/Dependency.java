package de.rss.arch_extract.resources.arch_model;

public class Dependency {
    private String service;
    private String operation;
    private float probability;

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public float getProbability() {
        return probability;
    }

    public void setProbability(float probability) {
        this.probability = probability;
    }
}
