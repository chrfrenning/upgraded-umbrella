package g7asmt1.server;

import java.rmi.RemoteException;
import java.util.stream.IntStream;

public class TaskManager {
    private int[] counter = new int[0]; // waiting lists
    private static final int BUSY = 8;
    private static final int OVERLOAD = 18;
    public TaskManager(int amountOfServers) {
        this.counter = new int[amountOfServers + 1]; // For simplicity the index 0 will not be used
    }

    @Deprecated(since = "For test purposes only!")
    public TaskManager(int[] counter) {
        this.counter = counter;
    }
    public boolean isBusy(int zone) {
        // TODO handle negatives
        return counter[zone] >= BUSY;
    }

    public boolean isOverloaded(int zone) {
        // TODO handle negatives
        return counter[zone] >= OVERLOAD;
    }
    public int lessTasks() throws RemoteException {
        return IntStream.range(1, counter.length)
                .reduce((i, j) -> counter[i] < counter[j] ? i : j) // returns the index if the last occurrence of the lowest value
                .orElseThrow(() -> new RemoteException("Task counter is not initialized or populated."));
    }

    public void incrementCounterForZone(int zone) {
        // TODO: No limits?
        counter[zone]++;
    }
}
