import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Logger;

public class LoadBalancer extends UnicastRemoteObject implements Proxy {
    private static final Logger LOGGER = Logger.getLogger(Server.class.getName());

    protected LoadBalancer() throws RemoteException {
        super();
    }

    @Override
    public String chooseServer(int clientZone) throws RemoteException {
        // Query the RMI service to list all services that start with "StatisticsService:"
        // Iterate through the list of services and find the one with the least load
        Registry registry = LocateRegistry.getRegistry("localhost", 1099);
        ArrayList<String> serverList = new ArrayList<>();
        for (String zone : registry.list()) {
            LOGGER.info("Considering zone " + zone);
            if (zone.startsWith(String.valueOf(clientZone))) {
                serverList.add(zone);
                LOGGER.info("Chosen zone " + zone);
            }
        }

        return serverList.get(0);
    }
}
