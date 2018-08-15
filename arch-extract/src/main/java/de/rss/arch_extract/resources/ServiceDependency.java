package de.rss.arch_extract.resources;

public class ServiceDependency {

    private String parent;
    private String child;

    public ServiceDependency(String parent, String child) {
        this.parent = parent;
        this.child = child;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getChild() {
        return child;
    }

    public void setChild(String child) {
        this.child = child;
    }
}
