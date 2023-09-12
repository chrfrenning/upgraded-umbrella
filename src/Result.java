import java.io.Serializable;

public class Result implements Serializable {
    private final int result;
    private final int waitingTime;
    private final int executionTime;
    private final String serverName;

    public Result(int result, int waitingTime, int executionTime, String serverName) {
        this.result = result;
        this.waitingTime = waitingTime;
        this.executionTime = executionTime;
        this.serverName = serverName;
    }

    // Getters and setters for each field
    public int getResult() {
        return result;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public int getExecutionTime() {
        return executionTime;
    }

    public String getServerName() {
        return serverName;
    }
}
