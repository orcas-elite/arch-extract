package de.rss.arch_extract.resources.trace;

import java.util.ArrayList;
import java.util.List;

public class Trace {

    private String traceID;
    private List<Span> spans;

    public Trace(String traceID) {
        this.traceID = traceID;
        spans = new ArrayList<Span>();
    }

    public String getTraceID() {
        return traceID;
    }

    public void setTraceID(String traceID) {
        this.traceID = traceID;
    }

    public List<Span> getSpans() {
        return spans;
    }

    public void setSpans(List<Span> spans) {
        this.spans = spans;
    }

    public void addSpan(Span span) {
        this.spans.add(span);
    }
}
