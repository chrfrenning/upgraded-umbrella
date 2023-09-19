package g7asmt1.client;

import java.util.ArrayList;
import java.util.List;

public class MethodStats {
    List<Long> turnaround;
    List<Long> execution;
    List<Long> wait;
    

    public MethodStats() {
        this.turnaround = new ArrayList<Long>();
        this.execution = new ArrayList<Long>();
        this.wait = new ArrayList<Long>();
    }

    public void add(long turnaround, long execution, long wait) {
        this.turnaround.add(turnaround);
        this.execution.add(execution);
        this.wait.add(wait);
    }

    public double avgTurnaround() {
        return turnaround.stream()
            .mapToLong(Long::longValue)
            .average()
            .orElse(0);
    }

    public double avgExecution() {
        return execution.stream()
            .mapToLong(Long::longValue)
            .average()
            .orElse(0);
    }

    public double avgWait() {
        return wait.stream()
            .mapToLong(Long::longValue)
            .average()
            .orElse(0);
    }

    public long size() {
        return turnaround.size();
    }
}
