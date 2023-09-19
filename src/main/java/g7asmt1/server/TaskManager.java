package g7asmt1.server;

import java.util.logging.Logger;

public class TaskManager {
    private static final Logger LOGGER = Logger.getLogger(TaskManager.class.getName());

    private final int[] counter;
    public TaskManager(int amountOfServers) {
        this.counter = new int[amountOfServers + 1];
    }


    public boolean isBusy(String zone) {
        LOGGER.info("Zone " + zone + " is busy.");
        return counter[Integer.parseInt(zone)] < 18;
    }

}
