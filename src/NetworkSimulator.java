import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class NetworkSimulator {

    public static void main(String[] args) {
        try {
            // The load balancer owns the registry
            // alas should be started as the first component
            Registry registry = LocateRegistry.createRegistry(1099);
            // Host the proxy
            Proxy proxy = new LoadBalancer();
            registry.bind("LoadBalancer", proxy); // Bind the remote object to the registry
            System.out.println("LoadBalancer is ready.");
        } catch (Exception e) {
            System.err.println("LoadBalancer exception:");
            e.printStackTrace();
        }
    }
}
