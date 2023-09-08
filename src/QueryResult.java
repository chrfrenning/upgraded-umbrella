import java.io.Serializable;

public class QueryResult implements Serializable {
    private int result;
    private int turnaroundTime;
    private int waitingTime;
    private int executionTime;
    private String serverName;

    public QueryResult(int result, int turnaroundTime, int waitingTime, int executionTime, String serverName) {
        this.result = result;
        this.turnaroundTime = turnaroundTime;
        this.waitingTime = waitingTime;
        this.executionTime = executionTime;
        this.serverName = serverName;
    }

    // Getters and setters for each field
    public int getResult() {
        return result;
    }

    public int getTurnaroundTime() {
        return turnaroundTime;
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
