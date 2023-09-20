package g7asmt1.server;

public class TaskManager {
    private int[] waitingLists; // waiting lists
    private static final int BUSY = 8;
    private static final int OVERLOAD = 18;
    private static ZoneManager zones;
    public TaskManager(int amountOfServers) {
        this.waitingLists = new int[amountOfServers + 1]; // For simplicity the index 0 will not be used
        zones = new ZoneManager(amountOfServers);
    }

    @Deprecated(since = "For test purposes only!")
    public TaskManager(int[] waitingLists) {
        this.waitingLists = waitingLists;
        zones = new ZoneManager(waitingLists.length - 1);
    }
    public boolean isBusy(int zone) {
        // TODO handle negatives
        return waitingLists[zone] >= BUSY;
    }

    public boolean isOverloaded(int zone) {
        // TODO handle negatives
        return waitingLists[zone] >= OVERLOAD;
    }

    public int chooseForZone(int zone) {
        int closest = zones.closest(zone);
        int neighbour = zones.neighbour(zone);
        return !isOverloaded(zone)
                || (isOverloaded(closest) && isOverloaded(neighbour))
                ? zone
                : !isBusy(closest)
                ? closest
                : !isBusy(neighbour)
                ? neighbour
                : !isOverloaded(closest) && waitingLists[closest] < waitingLists[neighbour]
                ? closest
                : !isOverloaded(neighbour) && waitingLists[closest] > waitingLists[neighbour]
                ? neighbour
                : waitingLists[closest] == waitingLists[neighbour] && Math.random() < 0.5
                ? closest// random
                : neighbour;
    }

    public void incrementCounterForZone(int zone) {
        // TODO: No limits?
        // TODO: remove test data
        waitingLists[zone] = waitingLists[zone] >= 19 ? 0 : waitingLists[zone]++;
    }

    public void syncWaitingList(int zone, int queueLength) {
        // TODO: No limits?
        waitingLists[zone] = queueLength;
    }
}
