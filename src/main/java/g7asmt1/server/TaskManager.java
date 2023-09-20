package g7asmt1.server;

public class TaskManager {
    private final WaitingList counter; // waiting lists
    private static ZoneManager zones;
    public TaskManager(int amountOfServers, WaitingList counter) {
        this.counter = counter; // For simplicity the index 0 will not be used
        zones = new ZoneManager(amountOfServers);
    }

    @Deprecated(since = "For test purposes only!")
    public TaskManager(int[] waitingLists) {
        this.counter = new WaitingList(waitingLists);
        zones = new ZoneManager(waitingLists.length - 1);
    }

    public int chooseForZone(int zone) {
        int closest = zones.closest(zone);
        int neighbour = zones.neighbour(zone);
        return !counter.isOverloaded(zone)
                || (counter.isOverloaded(closest) && counter.isOverloaded(neighbour))
                ? zone
                : !counter.isBusy(closest)
                ? closest
                : !counter.isBusy(neighbour)
                ? neighbour
                : !counter.isOverloaded(closest) && counter.get(closest) < counter.get(neighbour)
                ? closest
                : !counter.isOverloaded(neighbour) && counter.get(closest) > counter.get(neighbour)
                ? neighbour
                : counter.get(closest) == counter.get(neighbour) && Math.random() < 0.5
                ? closest// random
                : neighbour;
    }

    public void incrementCounterForZone(int zone) {
        counter.incrementForZone(zone);
        if (counter.get(zone) >= 18) {
            new WaitingListSynchronizer(counter, zone).start();
        }
    }
}
