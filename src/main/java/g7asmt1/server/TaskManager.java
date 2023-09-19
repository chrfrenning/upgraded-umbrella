package g7asmt1.server;

import java.rmi.RemoteException;
import java.util.logging.Logger;
import java.util.stream.IntStream;

public class TaskManager {
    private final int[] counter;
    private final int BUSY = 8;
    private final int OVERLOAD = 18;
    public TaskManager(int amountOfServers) {
        this.counter = new int[amountOfServers + 1]; // For simplicity the index 0 will not be used
    }

    @Deprecated(since = "For test purposes only!")
    public TaskManager(int[] counter) {
        this.counter = counter;
    }
    public boolean isBusy(String zone) {
        // TODO handle negatives
        return counter[Integer.parseInt(zone)] >= BUSY;
    }

    public boolean isOverloaded(String zone) {
        // TODO handle negatives
        return counter[Integer.parseInt(zone)] >= OVERLOAD;
    }
    public String lessTasks() throws RemoteException {
        return String.valueOf(IntStream.range(1, counter.length)
                .reduce((i, j) -> counter[i] < counter[j] ? i : j) // returns the index if the last occurrence of the lowest value
                .orElseThrow(() -> new RemoteException("Task counter is not initialized or populated.")));
    }

    public void countTaskForZone(String zone) {
        counter[Integer.parseInt(zone)]++;
    }
}
