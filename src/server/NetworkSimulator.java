package server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.*;

public class NetworkSimulator {
    private static final Logger LOGGER = Logger.getLogger(NetworkSimulator.class.getName());
    private static final int AMOUNT_OF_ZONES = 5;
    private static final int PORT = 1099;

    /**
     * The method creates a registry.
     * THen it binds an instance of a load balancer and a few instances of server stubs.
     */
    public static void main(String[] args) {
        try {
            // Create a registry
            Registry registry = LocateRegistry.createRegistry(PORT);
            // Bind the load balancer to the registry
            registry.bind("loadBalancer", new LoadBalancer());
            LOGGER.info(String.format("loadBalancer is running on port %d.", PORT));
            // Bind remote objects to the registry
            for (int zone = 1; zone <= AMOUNT_OF_ZONES; zone++) {
                registry.bind(String.valueOf(zone), new Server(zone));
                LOGGER.info(String.format("server.Server in zone %d is registered.%n", zone));
            }
        } catch (Exception e) {
            LOGGER.severe("Cannot register load balancer or servers.");
            e.printStackTrace();
        }
    }
}
