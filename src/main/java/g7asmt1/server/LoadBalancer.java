package g7asmt1.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.LocateRegistry;
import java.util.Arrays;
import java.util.Optional;
import java.util.logging.Logger;

public class LoadBalancer extends UnicastRemoteObject implements Proxy {
    private static final Logger LOGGER = Logger.getLogger(LoadBalancer.class.getName());
    private static final int PORT = 1099;

    /** DECISION: final amount of servers
     * The amount of servers is final in this implementation to achieve simplicity.
     * In real world systems the complexity should be worth it -- a load balancer
     * should  be able to handle new servers added or removed without disturbance of the
     * unchanged servers' progress.
    */
    private final ZoneManager zones;
    private final TaskManager tasks;

    public LoadBalancer(int amountOfServers) throws RemoteException {
        this.zones = new ZoneManager(amountOfServers);
        this.tasks = new TaskManager(amountOfServers);
    }

    @Override
    public String chooseServer(int clientZone) throws RemoteException {
        // TODO: validate input - positive, upper limits?
        // TODO: if clientZone > amount of servers - random server?

        // Query the RMI service to list all services that start with "server.StatisticsService:"
        // Iterate through the list of services and find the one with the least load
        Optional<String> chosenByZone = Arrays.stream(LocateRegistry.getRegistry("localhost", PORT).list())
                .peek(zone -> LOGGER.info("Considering zone " + zone))
                .filter(zone -> !tasks.isOverloaded(zone) && (zones.isSameZone(zone, clientZone)))
                .filter(zone -> !tasks.isBusy(zone)
                        || zones.isClosestZone(zone, clientZone)
                        || zones.isNeighbour(zone, clientZone))
                .findFirst();
        String chosen = chosenByZone.orElse(tasks.lessTasks());
        tasks.countTaskForZone(chosen);
        return chosen;
    }
}
