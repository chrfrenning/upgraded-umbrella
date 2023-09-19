package g7asmt1.server;

import java.rmi.RemoteException;
import java.util.stream.IntStream;

public class TaskManager {
    private int[] waitingLists = new int[0]; // waiting lists
    private static final int BUSY = 8;
    private static final int OVERLOAD = 18;
    public TaskManager(int amountOfServers) {
        this.waitingLists = new int[amountOfServers + 1]; // For simplicity the index 0 will not be used
    }

    @Deprecated(since = "For test purposes only!")
    public TaskManager(int[] waitingLists) {
        this.waitingLists = waitingLists;
    }
    public boolean isBusy(int zone) {
        // TODO handle negatives
        return waitingLists[zone] >= BUSY;
    }

    public boolean isOverloaded(int zone) {
        // TODO handle negatives
        return waitingLists[zone] >= OVERLOAD;
    }
    public int lessTasks() throws RemoteException {
        return IntStream.range(1, waitingLists.length)
                .reduce((i, j) -> waitingLists[i] < waitingLists[j] ? i : j) // returns the index if the last occurrence of the lowest value
                .orElseThrow(() -> new RemoteException("Task counter is not initialized or populated."));
    }

    public void incrementCounterForZone(int zone) {
        // TODO: No limits?
        waitingLists[zone]++;
    }

    public void syncWaitingList(int zone, int queueLength) {
        // TODO: No limits?
        waitingLists[zone] = queueLength;
    }
}
