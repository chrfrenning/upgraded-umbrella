import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class StatisticsServer extends UnicastRemoteObject implements StatisticsService {
    private String serverName;

    protected StatisticsServer(String serverName) throws RemoteException {
        super();
        this.serverName = serverName;
    }

    public QueryResult getPopulationOfCountry(String countryName) throws RemoteException {
        // Implement your code to return QueryResult
        return new QueryResult(0, 0, 0, serverName); // Placeholder
    }

    public QueryResult getNumberOfCities(String countryName, int min) throws RemoteException {
        // Implement your code to return QueryResult
        return new QueryResult(0, 0, 0, serverName); // Placeholder
    }

    public QueryResult getNumberOfCountries(int cityCount, int minPopulation) throws RemoteException {
        // Implement your code to return QueryResult
        return new QueryResult(0, 0, 0, serverName); // Placeholder
    }

    public QueryResult getNumberOfCountries(int cityCount, int minPopulation, int maxPopulation) throws RemoteException {
        // Implement your code to return QueryResult
        return new QueryResult(0, 0, 0, serverName); // Placeholder
    }

    public static void main(String[] args) {
        try {
            // Expect servername as argument
            if (args.length != 1) {
                System.out.println("Usage: java StatisticsServer <servername>");
                System.exit(0);
            }
            String serverName = args[0];
            // Expect the LoadBalancer to run first and host the registry
            Registry registry = LocateRegistry.getRegistry(1099);
            // Create and register the statistics service
            StatisticsService service = new StatisticsServer(serverName);
            registry.bind(String.format("StatisticsService:%s", serverName), service); // Bind the remote object to the registry
            System.out.println(String.format("StatisticsService:%s is ready.", serverName));
        } catch (Exception e) {
            System.err.println("StatisticsService exception:");
            e.printStackTrace();
        }
    }
}
