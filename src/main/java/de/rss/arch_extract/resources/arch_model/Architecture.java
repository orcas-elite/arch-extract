package de.rss.arch_extract.resources.arch_model;

import java.util.ArrayList;
import java.util.List;

public class Architecture {

    private List<Microservice> microservices;

    public Architecture() {
        microservices = new ArrayList<Microservice>();
    }

    public Architecture(List<Microservice> services) {
        this.microservices = services;
    }

    public List<Microservice> getMicroservices() {
        return microservices;
    }

    public void setMicroservices(List<Microservice> microservices) {
        this.microservices = microservices;
    }

    public void addMicroservice(Microservice microservice) {
        microservices.add(microservice);
    }
}
