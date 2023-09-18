package g7asmt1.client;

import g7asmt1.server.Proxy;
import g7asmt1.server.StatisticsService;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.util.logging.Logger;

public class Client {
    private static final Logger LOGGER = Logger.getLogger(Client.class.getName());
    private static final int PORT = 1099;

    public static void main(String[] args) {
        try {
            // Check that we have correct arguments
            if (args.length != 1) {
                System.out.println("Usage: java client.Client <zone>");
                return;
            }
            // Get the client zone number from command line arguments
            int zone = Integer.parseInt(args[0]);
            LOGGER.info("Requesting proxy for zone " + zone + " on PORT " + PORT + "...");
            Proxy loadBalancer = (Proxy) LocateRegistry.getRegistry("localhost", PORT).lookup("loadBalancer");
            String serverName = loadBalancer.chooseServer(zone);
            
            LOGGER.info("Requesting service...");
            StatisticsService service = (StatisticsService) Naming.lookup(String.format("rmi://localhost:%d/%s", PORT, serverName));

            LOGGER.info("Querying service...");
            LOGGER.info(service.getPopulationOfCountry("USA").toString());
            LOGGER.info(service.getPopulationOfCountry("USA").toString());
            LOGGER.info(service.getPopulationOfCountry("USA").toString());
            LOGGER.info(service.getPopulationOfCountry("USA").toString());
            LOGGER.info(service.getPopulationOfCountry("USA").toString());
            LOGGER.info(service.getPopulationOfCountry("USA").toString());
            
            LOGGER.info(service.getNumberOfCities("USA", 100000).toString());
            LOGGER.info(service.getNumberOfCountries(10, 1000000).toString());
            LOGGER.info(service.getNumberOfCountries(10, 1000000, 5000000).toString());

        } catch (Exception e) {
            LOGGER.severe("Something went wrong on the client side while querying a service.");
            e.printStackTrace();
        }
    }
}
