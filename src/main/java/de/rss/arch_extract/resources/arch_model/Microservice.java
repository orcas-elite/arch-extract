package de.rss.arch_extract.resources.arch_model;

import java.util.ArrayList;
import java.util.List;

public class Microservice {
    private String name;
    private int instances;
    private String[] patterns = new String[0];
    private int capacity;
    private List<Operation> operations;

    private transient List<String> hosts;

    public Microservice() {
        operations = new ArrayList<Operation>();
        hosts = new ArrayList<String>();
    }

    public Microservice(String name) {
        this.name = name;
        operations = new ArrayList<Operation>();
        hosts = new ArrayList<String>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getInstances() {
        return instances;
    }

    public void setInstances(int instances) {
        this.instances = instances;
    }

    public String[] getPatterns() {
        return patterns;
    }

    public void setPatterns(String[] patterns) {
        this.patterns = patterns;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public void setOperations(List<Operation> operations) {
        this.operations = operations;
    }

    public void addOperation(Operation operation) {
        operations.add(operation);
    }

    public List<String> getHosts() {
        return hosts;
    }

    public void setHosts(List<String> hosts) {
        this.hosts = hosts;
    }

    public void addHost(String host) {
        this.hosts.add(host);
    }

    public boolean containsHost(String host) {
        if (hosts.contains(host)) {
            return true;
        }
        return false;
    }
}
