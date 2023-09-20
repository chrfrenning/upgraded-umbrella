package g7asmt1.server;

import java.rmi.Naming;
import java.util.logging.Logger;

public class WaitingListSynchronizer extends Thread {
    private static final Logger LOGGER = Logger.getLogger(WaitingListSynchronizer.class.getName());

    private final WaitingList counter;
    private final int server;

    public WaitingListSynchronizer(WaitingList counter, int server) {
        this.counter = counter;
        this.server = server;
        LOGGER.info("Synchronizer server " + server + ": current " + counter.get(server) + "...................................");
    }

    @Override
    public void run() {
        String url = String.format("rmi://localhost:%d/%s", 1099, server);
        try {
            StatisticsService service = (StatisticsService) Naming.lookup(url);
            counter.sync(server, service.getQueueLength());
        } catch (Exception e) {
            LOGGER.severe("Something went wrong while accessing " + url);
            e.printStackTrace();
        }
        LOGGER.info("...............................DONE Synchronized value " + counter.get(server));
    }
}
