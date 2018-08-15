package resources;

public class Operation {

    private String name;
    private int demand;
    private CircuitBreaker circuitBreaker;
    private Dependency[] dependencies;
    private int dependingOperationCount = 0;

    public Operation(String name) {
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

    public Dependency[] getDependencies() {
        return dependencies;
    }

    public void setDependencies(Dependency[] dependencies) {
        this.dependencies = dependencies;
    }

    public int getDependingOperationCount() {
        return dependingOperationCount;
    }

    public void setDependingOperationCount(int dependingOperationCount) {
        this.dependingOperationCount = dependingOperationCount;
    }

    public void increaseDependingOperationCount() {
        this.dependingOperationCount++;
    }

    public boolean hasCircuitBreaker() {
        if (circuitBreaker != null) {
            return true;
        }
        return false;
    }
}
