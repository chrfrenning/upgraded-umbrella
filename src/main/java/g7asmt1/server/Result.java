package g7asmt1.server;

import java.io.Serializable;

public class Result implements Serializable {
    private final String serviceName;
    private final long result;
    private final long waitingTime;
    private final long executionTime;
    private final int zone;

    public Result(String serviceName, long result, long waitingTime, long executionTime, int zone) {
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
