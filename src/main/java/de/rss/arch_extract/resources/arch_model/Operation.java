package de.rss.arch_extract.resources.arch_model;

import java.util.ArrayList;
import java.util.List;

public class Operation {
    private String name;
    private int demand;
    private CircuitBreaker circuitBreaker;
    private List<Dependency> dependencies;

    public Operation() {
        dependencies = new ArrayList<Dependency>();
    }

    public Operation(String name) {
        dependencies = new ArrayList<Dependency>();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDemand() {
        return demand;
    }

    public void setDemand(int demand) {
        this.demand = demand;
    }

    public CircuitBreaker getCircuitBreaker() {
        return circuitBreaker;
    }

    public void setCircuitBreaker(CircuitBreaker circuitBreaker) {
        this.circuitBreaker = circuitBreaker;
    }

    public List<Dependency> getDependencies() {
        return dependencies;
    }

    public void setDependencies(List<Dependency> dependencies) {
        this.dependencies = dependencies;
    }

    public void addDependency(Dependency dependency) {
        dependencies.add(dependency);
    }

    public boolean containsDependency(Dependency newDependency) {
        for (Dependency dependency : dependencies) {
            if (dependency.getService().equals(newDependency.getService())) {
                if (dependency.getOperation().equals(newDependency.getOperation())) {
                    return true;
                }
            }
        }
        return false;
    }
}
