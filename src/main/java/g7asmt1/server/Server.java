package g7asmt1.server;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.List;
import java.util.stream.Collectors;
import java.util.logging.Logger;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.LocateRegistry;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;



public class Server extends UnicastRemoteObject implements StatisticsService {
    private static final Logger LOGGER = Logger.getLogger(Server.class.getName());
    private static final int PORT = 1099;
    private final int zone;
    private final ExecutorService threadPool;
    private int queueLength;
    
    protected Server(int zone) throws RemoteException {
        this.zone = zone;
        this.queueLength = 0;
        this.threadPool = Executors.newSingleThreadExecutor();
        LOGGER.info(String.format("server.Server in zone %d is created.%n", zone));
    }

    /*
     * Our request methods, these are invoked by RMI, we then queue the request
     * for processing in a single thread threadpool, which we know java uses a fifo
     * queue for so we can guarantee the ordering of the requests per assignment text.
     */
    
    @Override
    public Result getPopulationOfCountry(String countryName, int clientZone) throws RemoteException {
        final Date waitingTimeStart = new Date();
        incrementQueueLength();
        
        Future<Result> futureResult = threadPool.submit(new Callable<Result>() {
            @Override
            public Result call() throws Exception {
                try {
                    Date waitingTimeEnd = new Date();
                    Date executionTimeStart = new Date();

                    sleepMs(clientZone == zone ? 80 : 170);

                    // Query the dataset
                    long population = 0;
                    try {
                        List<GeoBean> beans = LoadDataFile();
                        population = beans.stream()
                            .filter(bean -> countryName.equals(bean.getCountryNameEN()))
                            .mapToLong(GeoBean::getPopulation)
                            .sum();
                    } catch (IOException e) {
                        throw new Exception("Error occurred while processing the dataset", e);
                    }

                    Date executionTimeEnd = new Date();
                    return new Result("getPopulation", population, waitingTimeEnd.getTime() - waitingTimeStart.getTime(), executionTimeEnd.getTime() - executionTimeStart.getTime(), zone);
                } finally {
                    decrementQueueLength();
                }
            }
        });
        
        try {
            return futureResult.get();  // This will block until the future is completed
        } catch (InterruptedException | ExecutionException e) {
            throw new RemoteException("Error occurred while processing", e);
        }
    }
    
    @Override
    public Result getNumberOfCities(String countryName, int min, int clientZone) throws RemoteException {
        final Date waitingTimeStart = new Date();
        incrementQueueLength();
        
        Future<Result> futureResult = threadPool.submit(new Callable<Result>() {
            @Override
            public Result call() throws Exception {
                try {
                    Date waitingTimeEnd = new Date();
                    Date executionTimeStart = new Date();

                    sleepMs(clientZone == zone ? 80 : 170);

                    // Query the dataset
                    long cities = 0;
                    try {
                        List<GeoBean> beans = LoadDataFile();
                        cities = beans.stream()
                            .filter(bean -> countryName.equals(bean.getCountryNameEN()))
                            .mapToLong(GeoBean::getPopulation)
                            .filter(population -> population >= min)
                            .count();
                    } catch (IOException e) {
                        throw new Exception("Error occurred while processing the dataset", e);
                    }

                    Date executionTimeEnd = new Date();
                    return new Result("getNumberOfCities", cities, waitingTimeEnd.getTime() - waitingTimeStart.getTime(), executionTimeEnd.getTime() - executionTimeStart.getTime(), zone);
                } finally {
                    decrementQueueLength();
                }
            }
        });
        
        try {
            return futureResult.get();  // This will block until the future is completed
        } catch (InterruptedException | ExecutionException e) {
            throw new RemoteException("Error occurred while processing", e);
        }
    }
    
    @Override
    public Result getNumberOfCountries(int cityCount, int minPopulation, int clientZone) throws RemoteException {
        return getNumberOfCountries(cityCount, minPopulation, Integer.MAX_VALUE);
    }
    
    @Override
    public Result getNumberOfCountries(int cityCount, int minPopulation, int maxPopulation, int clientZone) throws RemoteException {
        final Date waitingTimeStart = new Date();
        incrementQueueLength();
        
        Future<Result> futureResult = threadPool.submit(new Callable<Result>() {
            @Override
            public Result call() throws Exception {
                try {
                    Date waitingTimeEnd = new Date();
                    Date executionTimeStart = new Date();

                    sleepMs(clientZone == zone ? 80 : 170);

                    // Query the dataset
                    long countries = 0;
                    try {
                        List<GeoBean> beans = LoadDataFile();
                        countries = beans.stream()
                            .filter(geoBean -> geoBean.getPopulation() >= minPopulation && geoBean.getPopulation() <= maxPopulation)
                            .collect(Collectors.groupingBy(GeoBean::getCountryNameEN))
                            .entrySet().stream()
                            .filter(entry -> entry.getValue().size() >= cityCount)
                            .count();
                    } catch (IOException e) {
                        throw new Exception("Error occurred while processing the dataset", e);
                    }

                    Date executionTimeEnd = new Date();
                    return new Result("getNumberOfCountries", countries, waitingTimeEnd.getTime() - waitingTimeStart.getTime(), executionTimeEnd.getTime() - executionTimeStart.getTime(), zone);
                } finally {
                    decrementQueueLength();
                }
            }
        });
        
        try {
            return futureResult.get();  // This will block until the future is completed
        } catch (InterruptedException | ExecutionException e) {
            throw new RemoteException("Error occurred while processing", e);
        }
    }



    /* 
     * Manages the queue length, we manually increment and decrement as we queue
     * and process the requests.
     */
    
    @Override
    public int getQueueLength() throws RemoteException {
        return internalGetQueueLength();
    }
    
    private synchronized void incrementQueueLength() {
        queueLength++;
    }
    
    private synchronized void decrementQueueLength() {
        queueLength--;
    }
    
    private synchronized int internalGetQueueLength() {
        return queueLength;
    }



    /*
     * Utilities
     */

    private void sleepMs(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
        }
    }



    /* 
     * Loading our dataset, note we're not caching or keeping this,
     * this is the raw version that always loads from the file (note
     * disk caching will be substantial on any modern os with sufficient memory)
     */
    
    private List<GeoBean> LoadDataFile() throws IOException {
        Reader reader = Files.newBufferedReader(Paths.get("./dataset/dataset.csv"));
        CsvToBean<GeoBean> csvToBean = new CsvToBeanBuilder<GeoBean>(reader)
            .withType(GeoBean.class)
            .withSeparator(';')
            .build();
        
        List<GeoBean> beans = csvToBean.parse();
        return beans;
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
