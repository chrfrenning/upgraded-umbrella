package g7asmt1.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.LocateRegistry;
import java.util.logging.Logger;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Server extends UnicastRemoteObject implements StatisticsService {
    private static final Logger LOGGER = Logger.getLogger(Server.class.getName());
    private static final int PORT = 1099;
    private final int zone;
    private final ExecutorService threadPool;

    protected Server(int zone) throws RemoteException {
        this.zone = zone;
        this.threadPool = Executors.newSingleThreadExecutor();
        LOGGER.info(String.format("server.Server in zone %d is created.%n", zone));
    }

    @Override
    public Result getPopulationOfCountry(String countryName) throws RemoteException {
        Date waitingTimeStart = new Date();

        Future<Result> futureResult = threadPool.submit(new Callable<Result>() {
            @Override
            public Result call() {
                Date waitingTimeEnd = new Date();
                // Perform the time-consuming task here
                Date executionTimeStart = new Date();
                int population = getPopulation(countryName);  // Replace with real implementation
                int area = getArea(countryName);  // Replace with real implementation
                Date executionTimeEnd = new Date();
                return new Result("getPopulation", population, waitingTimeStart.getTime() - waitingTimeEnd.getTime(), executionTimeEnd.getTime() - executionTimeStart.getTime(), zone);
            }
        });

        try {
            return futureResult.get();  // This will block until the future is completed
        } catch (InterruptedException | ExecutionException e) {
            throw new RemoteException("Error occurred while processing", e);
        }
    }

    private int getPopulation(String countryName) {
        // TODO
        return 0;
    }

    private int getArea(String countryName) {
        // TODO
        return 0;
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
