package g7asmt1.server;

import java.util.logging.Logger;

public class ZoneManager {
    private static final Logger LOGGER = Logger.getLogger(ZoneManager.class.getName());
    private final int amountOfServers;
    public ZoneManager(int amountOfServers) {
        this.amountOfServers = amountOfServers;
    }


    public boolean isSameZone(String zone, int clientZone) {
        return zone.equals(String.valueOf(clientZone));
    }

    public  boolean isClosestZone(String zone, int clientZone) {
        LOGGER.info("Zone " + zone + " is closest to client zone " + clientZone);
        return zone.equals(String.valueOf(clientZone % amountOfServers + 1));
    }

    public boolean isNeighbour(String zone, int clientZone) {
        LOGGER.info("Zone " + zone + " is neighbour to client zone " + clientZone);
        return zone.equals(String.valueOf((clientZone + 1) % amountOfServers + 1));
    }
}
