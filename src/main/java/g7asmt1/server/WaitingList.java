package g7asmt1.server;

public class WaitingList {
    private final int[] waitingLists; // waiting lists
    private static final int BUSY = 8;
    private static final int OVERLOAD = 18;
    public WaitingList(int[] waitingLists) {
        this.waitingLists = waitingLists;
    }

    public synchronized int get(int index) {
        return waitingLists[index];
    }
    public synchronized void incrementForZone(int zone) {
        // TODO: No limits?
        waitingLists[zone]++;
    }

    public synchronized boolean isBusy(int zone) {
        return waitingLists[zone] >= BUSY;
    }

    public synchronized boolean isOverloaded(int zone) {
        return waitingLists[zone] >= OVERLOAD;
    }

    public synchronized void sync(int zone, int queryLength) {
        // TODO: input validation
        waitingLists[zone] = queryLength;
    }
}
