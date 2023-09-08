import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
    public static void main(String[] args) {
        try {
            // Check that we have correct arguments
            if (args.length != 1) {
                System.out.println("Usage: java Client <zone>");
                return;
            }
            // Get zone from command line arguments
            String zone = args[0];

            // Connect to the RMI registry on localhost, port 1099
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);

            // Connect to the load balancer and get a server to talk to
            LoadBalancerService loadBalancer = (LoadBalancerService) registry.lookup("LoadBalancer");
            String serverName = loadBalancer.getStatisticsServer(zone);
            
            // Connect to the given statistics server

            StatisticsService service = (StatisticsService) Naming.lookup(String.format("rmi://localhost:1099/%s", serverName));

            // Call the remote methods and print the results
            QueryResult populationResult = service.getPopulationOfCountry("USA");
            System.out.println("Population of USA: " + populationResult.getResult());
            System.out.println("Server name: " + populationResult.getServerName());

            QueryResult cityCountResult = service.getNumberOfCities("USA", 100000);
            System.out.println("Number of cities in USA with population > 100000: " + cityCountResult.getResult());
            System.out.println("Server name: " + cityCountResult.getServerName());

            QueryResult countryCountResult1 = service.getNumberOfCountries(10, 1000000);
            System.out.println("Number of countries with at least 10 cities and population > 1000000: " + countryCountResult1.getResult());
            System.out.println("Server name: " + countryCountResult1.getServerName());

            QueryResult countryCountResult2 = service.getNumberOfCountries(10, 1000000, 5000000);
            System.out.println("Number of countries with at least 10 cities and population between 1000000 and 5000000: " + countryCountResult2.getResult());
            System.out.println("Server name: " + countryCountResult2.getServerName());
            
        } catch (Exception e) {
            System.err.println("Client exception:");
            e.printStackTrace();
        }
    }
}
