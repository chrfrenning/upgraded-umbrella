package g7asmt1.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.LocateRegistry;
import java.util.logging.Logger;

public class Server extends UnicastRemoteObject implements StatisticsService {
    private static final Logger LOGGER = Logger.getLogger(Server.class.getName());
    private static final int PORT = 1099;
    private final int zone;

    protected Server(int zone) throws RemoteException {
        super();
        this.zone = zone;
        LOGGER.info(String.format("server.Server in zone %d is created.%n", zone));
    }

    @Override
    public Result getPopulationOfCountry(String countryName) throws RemoteException {
        // TODO
        return new Result("getPopulationOfCountry", 0, 0, 0, zone); // Placeholder
    }

    @Override
    public Result getNumberOfCities(String countryName, int min) throws RemoteException {
        // TODO
        return new Result("getNumberOfCities", 0, 0, 0, zone); // Placeholder
    }

    @Override
    public Result getNumberOfCountries(int cityCount, int minPopulation) throws RemoteException {
        // TODO
        return new Result("getNumberOfCountries", 0, 0, 0, zone); // Placeholder
    }

    @Override
    public Result getNumberOfCountries(int cityCount, int minPopulation, int maxPopulation) throws RemoteException {
        // TODO
        return new Result("getNumberOfCountries", 0, 0, 0, zone); // Placeholder
    }

    @Override
    public int getQueueLength() throws RemoteException {
        // TODO
        return 0;
    }

    /** Initiates a service in a zone and binds to the registry in a given port.
     *
     * @param args A unique zone number as the first argument
     */
    public static void main(String[] args) {
        try {
            if (args.length != 1) {
                System.out.println("Usage: java server.Server <zone number>");
                System.exit(0);
            }
            int zone = Integer.parseInt(args[0]);
            // Get the registry on the PORT and bind a server instance to the registry
            LocateRegistry.getRegistry(PORT).bind(String.valueOf(zone), new Server(zone));
            LOGGER.info(String.format("server.Server in zone %d is registered.%n", zone));
        } catch (Exception e) {
            LOGGER.severe("Failed to create or register a server to the registry.");
            e.printStackTrace();
        }
    }
}