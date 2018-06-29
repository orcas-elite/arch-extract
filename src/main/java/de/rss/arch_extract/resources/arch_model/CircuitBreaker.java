package de.rss.arch_extract.resources.arch_model;

public class CircuitBreaker {

    private static final int DEFAULT_ROLLING_WINDOW = 10;
    private static final int DEFAULT_REQUEST_VOLUME_THRESHOLD = 20;
    private static final double DEFAULT_ERROR_THRESHOLD_PERCENTAGE = 0.5;
    private static final int DEFAULT_TIMEOUT = 1;
    private static final int DEFAULT_SLEEP_WINODW = 5;

    private int rollingWindow;
    private int requestVolumeThreshold;
    private double errorThresholdPercentage;
    private int timeout;
    private int sleepWindow;

    public CircuitBreaker() {
        rollingWindow = DEFAULT_ROLLING_WINDOW;
        requestVolumeThreshold = DEFAULT_REQUEST_VOLUME_THRESHOLD;
        errorThresholdPercentage = DEFAULT_ERROR_THRESHOLD_PERCENTAGE;
        timeout = DEFAULT_TIMEOUT;
        sleepWindow = DEFAULT_SLEEP_WINODW;
    }

    public int getRollingWindow() {
        return rollingWindow;
    }

    public void setRollingWindow(int rollingWindow) {
        this.rollingWindow = rollingWindow;
    }

    public int getRequestVolumeThreshold() {
        return requestVolumeThreshold;
    }

    public void setRequestVolumeThreshold(int requestVolumeThreshold) {
        this.requestVolumeThreshold = requestVolumeThreshold;
    }

    public double getErrorThresholdPercentage() {
        return errorThresholdPercentage;
    }

    public void setErrorThresholdPercentage(double errorThresholdPercentage) {
        this.errorThresholdPercentage = errorThresholdPercentage;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getSleepWindow() {
        return sleepWindow;
    }

    public void setSleepWindow(int sleepWindow) {
        this.sleepWindow = sleepWindow;
    }
}
