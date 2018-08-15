package resources;

public class Microservice {

    private String name;
    private int capacity;
    private int instances;
    private Pattern[] spatterns;
    private Operation[] operations;

    public Microservice(String name) {
        this.name = name;
        this.spatterns = new Pattern[]{};
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getInstances() {
        return instances;
    }

    public void setInstances(int instances) {
        this.instances = instances;
    }

    public Pattern[] getSpatterns() {
        return spatterns;
    }

    public void setSpatterns(Pattern[] spatterns) {
        this.spatterns = spatterns;
    }

    public Operation[] getOperations() {
        return operations;
    }

    public void setOperations(Operation[] operations) {
        this.operations = operations;
    }
}
