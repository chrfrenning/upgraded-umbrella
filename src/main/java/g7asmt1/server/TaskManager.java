package g7asmt1.server;

import java.rmi.RemoteException;
import java.util.logging.Logger;
import java.util.stream.IntStream;

public class TaskManager {
    private static final Logger LOGGER = Logger.getLogger(TaskManager.class.getName());

    private final int[] counter;
    public TaskManager(int amountOfServers) {
        this.counter = new int[amountOfServers + 1]; // For simplicity the index 0 will not be used
    }

    @Deprecated(since = "For test purposes only!")
    public TaskManager(int[] counter) {
        this.counter = counter;
    }
    public boolean isBusy(String zone) {
        LOGGER.info("Zone " + zone + " is busy.");
        return counter[Integer.parseInt(zone) - 1] < 18;
    }

    public String lessTasks() throws RemoteException {
        return String.valueOf(IntStream.range(1, counter.length)
                .reduce((i, j) -> counter[i] < counter[j] ? i : j) // returns the index if the last occurrence of the lowest value
                .orElseThrow(() -> new RemoteException("Task counter is not initialized or populated.")));
    }

}
