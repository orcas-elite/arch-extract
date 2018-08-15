package de.rss.arch_extract.input_output;

import de.rss.arch_extract.resources.trace.Trace;

import java.util.List;

public interface InputManager {
    public List<String> getServices();

    public List<String> getOperations(String service);

    public List<Trace> getTraces(String serviceName);
}
