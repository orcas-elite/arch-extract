package resources;

public class CircuitBreaker {

    private int rollingWindow;
    private int requestVolumeThreshold;
    private float errorThresholdPercentage;
    private int sleepWindow;
    private int timeout;

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

    public float getErrorThresholdPercentage() {
        return errorThresholdPercentage;
    }

    public void setErrorThresholdPercentage(float errorThresholdPercentage) {
        this.errorThresholdPercentage = errorThresholdPercentage;
    }

    public int getSleepWindow() {
        return sleepWindow;
    }

    public void setSleepWindow(int sleepWindow) {
        this.sleepWindow = sleepWindow;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }
}
