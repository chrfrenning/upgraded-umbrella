package g7asmt1.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.LocateRegistry;
import java.util.Arrays;
import java.util.logging.Logger;

public class LoadBalancer extends UnicastRemoteObject implements Proxy {
    private static final Logger LOGGER = Logger.getLogger(Server.class.getName());
    private static final int PORT = 1099;

    /** DECISION: final amount of servers
     * The amount of servers is final in this implementation to achieve simplicity.
     * In real world systems the complexity should be worth it -- a load balancer
     * should  be able to handle new servers added or removed without disturbance of the
     * unchanged servers' progress.
    */
    private final int amountOfServers;

    public LoadBalancer(int amountOfServers) throws RemoteException {
        this.amountOfServers = amountOfServers;
    }

    @Override
    public String chooseServer(int clientZone) throws RemoteException {
        // TODO: validate input - positive, upper limits?
        // TODO: if clientZone > amount of servers - random server?

        // Query the RMI service to list all services that start with "server.StatisticsService:"
        // Iterate through the list of services and find the one with the least load
        return Arrays.stream(LocateRegistry.getRegistry("localhost", PORT).list())
                .peek(zone -> LOGGER.info("Considering zone " + zone))
                .filter(zone -> !isBusy(zone)
                        && (isSameZone(zone, clientZone)
                        || isClosestZone(zone, clientZone)
                        || isNeighbour(zone, clientZone)))
                .findFirst()
                .orElseThrow(() -> new RemoteException("No server available at the moment for zone " + clientZone));
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
        int neighbour = (clientZone + 2) % amountOfServers;
        return zone.equals(String.valueOf(neighbour == 0 ? amountOfServers : neighbour));
    }

    public boolean isBusy(String zone) {
        LOGGER.info("Zone " + zone + " is busy.");
        // FIXME: remove test data
        return zone.equals("2");
    }
}
