import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class LoadBalancer extends UnicastRemoteObject implements Proxy {

    protected LoadBalancer() throws RemoteException {
        super();
    }

    @Override
    public String chooseServer(String clientZone) throws RemoteException {
        // Query the RMI service to list all services that start with "StatisticsService:"
        // Iterate through the list of services and find the one with the least load
        Registry registry = LocateRegistry.getRegistry("localhost", 1099);
        ArrayList<String> serverList = new ArrayList<>();
        for (String name : registry.list()) {
            System.out.println(name);
            if (name.startsWith("StatisticsService:")) {
                serverList.add(name);
                System.out.println("Added " + name);
            }
        }

        return serverList.get(0);
    }
}
