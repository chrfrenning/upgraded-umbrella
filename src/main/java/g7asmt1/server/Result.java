package g7asmt1.server;

import java.io.Serializable;

public class Result implements Serializable {
    public String serviceName;
    public long result;
    public long waitingTime;
    public long executionTime;
    public int zone;
    public boolean serverCacheHit;
    public boolean clientCacheHit;
    public boolean serverCacheEnabled;

    public Result(String serviceName, long result, long waitingTime, long executionTime, int zone) {
        this.serviceName = serviceName;
        this.result = result;
        this.waitingTime = waitingTime;
        this.executionTime = executionTime;
        this.zone = zone;
        this.serverCacheHit = false;
        this.clientCacheHit = false;
        this.serverCacheEnabled = false;
    }

    public Result(String serviceName, long result, long waitingTime, long executionTime, int zone, boolean serverCacheEnabled, boolean serverCacheHit, boolean clientCacheHit) {
        this.serviceName = serviceName;
        this.result = result;
        this.waitingTime = waitingTime;
        this.executionTime = executionTime;
        this.zone = zone;
        this.serverCacheHit = serverCacheHit;
        this.clientCacheHit = clientCacheHit;
        this.serverCacheEnabled = serverCacheEnabled;
    }

    @Override
    public String toString() {
        return "server.Result{" + serviceName +
                ", result=" + result +
                ", waitingTime=" + waitingTime +
                ", executionTime=" + executionTime +
                ", zone=" + zone +
                ", serverCacheHit=" + serverCacheHit +
                ", clientCacheHit=" + clientCacheHit +
                '}';
    }
}
