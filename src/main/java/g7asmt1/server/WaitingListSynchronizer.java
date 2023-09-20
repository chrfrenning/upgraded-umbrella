package g7asmt1.server;

import java.util.logging.Logger;

public class WaitingListSynchronizer extends Thread {
    private static final Logger LOGGER = Logger.getLogger(WaitingListSynchronizer.class.getName());

    private final WaitingList counter;
    private final int server;

    public WaitingListSynchronizer(WaitingList counter, int server) {
        this.counter = counter;
        this.server = server;
        LOGGER.info("Synchronizer server in zone " + server + "...................................");
    }

    @Override
    public void run() {
        counter.sync(server, 0);
        LOGGER.info("...............................DONE Synchronizer server in zone " + server);
    }
}
