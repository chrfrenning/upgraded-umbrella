import java.io.Serializable;

public class Result implements Serializable {
    private final int result;
    private final int waitingTime;
    private final int executionTime;
    private final int zone;

    public Result(int result, int waitingTime, int executionTime, int zone) {
        this.result = result;
        this.waitingTime = waitingTime;
        this.executionTime = executionTime;
        this.zone = zone;
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

    public int getZone() {
        return zone;
    }
}
