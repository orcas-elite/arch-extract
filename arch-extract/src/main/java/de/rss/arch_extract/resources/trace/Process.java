package de.rss.arch_extract.resources.trace;

public class Process {

    private String processId;
    private String serviceName;
    private String hostName;

    public Process(String processId, String serviceName, String hostName) {
        this.processId = processId;
        this.serviceName = serviceName;
        this.hostName = hostName;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
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
}
