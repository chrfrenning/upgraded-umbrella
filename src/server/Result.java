package server;

import java.io.Serializable;

public class Result implements Serializable {
    private final String serviceName;
    private final int result;
    private final int waitingTime;
    private final int executionTime;
    private final int zone;

    public Result(String serviceName, int result, int waitingTime, int executionTime, int zone) {
        this.serviceName = serviceName;
        this.result = result;
        this.waitingTime = waitingTime;
        this.executionTime = executionTime;
        this.zone = zone;
    }

    @Override
    public String toString() {
        return "server.Result{" + serviceName +
                ", result=" + result +
                ", waitingTime=" + waitingTime +
                ", executionTime=" + executionTime +
                ", zone=" + zone +
                '}';
    }
}
