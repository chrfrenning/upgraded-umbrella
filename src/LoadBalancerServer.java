import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class LoadBalancerServer extends UnicastRemoteObject implements LoadBalancerService {

    protected LoadBalancerServer() throws RemoteException {
        super();
    }

    @Override
    public String getStatisticsServer(String clientZone) throws RemoteException {
        // Query the RMI service to list all services that start with "StatisticsService:"
        // Iterate through the list of services and find the one with the least load
        Registry registry = LocateRegistry.getRegistry("localhost", 1099);
        ArrayList<String> serverList = new ArrayList<String>();
        for (String name : registry.list()) {
            System.out.println(name);
            if (name.startsWith("StatisticsService:")) {
                serverList.add(name);
                System.out.println("Added " + name);
            }
        }

        return serverList.get(0);
    }

    public static void main(String[] args) {
        try {
            // The load balancer owns the registry
            // alas should be started as the first component
            Registry registry = LocateRegistry.createRegistry(1099);
            // Host the service
            LoadBalancerService service = new LoadBalancerServer();
            registry.bind("LoadBalancer", service); // Bind the remote object to the registry
            System.out.println("LoadBalancer is ready.");
        } catch (Exception e) {
            System.err.println("LoadBalancer exception:");
            e.printStackTrace();
        }
    }
}
