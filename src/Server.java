import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server extends UnicastRemoteObject implements QueryService {

    protected Server() throws RemoteException {
        super();
    }

    public QueryResult getPopulationOfCountry(String countryName) throws RemoteException {
        // Implement your code to return QueryResult
        return new QueryResult(0, 0, 0, 0, "Server1"); // Placeholder
    }

    public QueryResult getNumberOfCities(String countryName, int min) throws RemoteException {
        // Implement your code to return QueryResult
        return new QueryResult(0, 0, 0, 0, "Server1"); // Placeholder
    }

    public QueryResult getNumberOfCountries(int cityCount, int minPopulation) throws RemoteException {
        // Implement your code to return QueryResult
        return new QueryResult(0, 0, 0, 0, "Server1"); // Placeholder
    }

    public QueryResult getNumberOfCountries(int cityCount, int minPopulation, int maxPopulation) throws RemoteException {
        // Implement your code to return QueryResult
        return new QueryResult(0, 0, 0, 0, "Server1"); // Placeholder
    }

    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.createRegistry(1099); // Create a registry on port 1099
            QueryService countryService = new Server();
            registry.bind("CountryService", countryService); // Bind the remote object to the registry
            System.out.println("CountryService is ready.");
        } catch (Exception e) {
            System.err.println("CountryService exception:");
            e.printStackTrace();
        }
    }
}
