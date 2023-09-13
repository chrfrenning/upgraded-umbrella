import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class NetworkSimulator {

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
            registry.bind("LoadBalancer", new LoadBalancer());
            System.out.println("LoadBalancer is ready.");
            // Bind remote objects to the registry
            for (int i = 1; i <= AMOUNT_OF_ZONES; i++) {
                registry.bind(String.valueOf(i), new Server(String.valueOf(i)));
                System.out.printf("Server in zone %d is ready.%n", i);
            }
        } catch (Exception e) {
            System.err.println("Cannot register load balancer or servers.");
            e.printStackTrace();
        }
    }
}
