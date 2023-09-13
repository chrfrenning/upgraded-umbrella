import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.LocateRegistry;
import java.util.logging.Logger;

public class LoadBalancer extends UnicastRemoteObject implements Proxy {
    private static final Logger LOGGER = Logger.getLogger(Server.class.getName());
    private static final int PORT = 1099;

    protected LoadBalancer() throws RemoteException {
        super();
    }

    @Override
    public String chooseServer(int clientZone) throws RemoteException {
        // Query the RMI service to list all services that start with "StatisticsService:"
        // Iterate through the list of services and find the one with the least load
        for (String zone : LocateRegistry.getRegistry("localhost", PORT).list()) {
            LOGGER.info("Considering zone " + zone);
            if (zone.startsWith(String.valueOf(clientZone))) {
                return zone;
            }
        }
        throw new RemoteException("No server available at the moment.");
    }
}
