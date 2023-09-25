package g7asmt1.client;

import g7asmt1.server.Proxy;
import g7asmt1.server.Result;
import g7asmt1.server.StatisticsService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class Client {
    private static final Logger LOGGER = Logger.getLogger(Client.class.getName());
    private static final int PORT = 1099;
    private static MethodStats getPopulationOfCountryStats = new MethodStats();
    private static MethodStats getNumberOfCitiesStats = new MethodStats();
    private static MethodStats getNumberOfCountries2Stats = new MethodStats();
    private static MethodStats getNumberOfCountries3Stats = new MethodStats();
    private static String cmdLineArgEnableLogging = "--log";
    private static String cmdLineArgRunCluster = "--cluster";
    
    public static void main(String[] args) {
        try {
            // Are we a single client or a simulated cluster?
            for (String arg : args) {
                if (arg.equals(cmdLineArgRunCluster)) {
                    runCluster(args);
                    return;
                }
            }

            // Disable logging for clean output, enable with --log
            LOGGER.setLevel(Level.OFF);
            for (String arg : args) {
                if (arg.equals(cmdLineArgEnableLogging)) {
                    LOGGER.setLevel(Level.ALL);
                }
            }

            // Let's have a cache
            Cache cache = new Cache();

            // Check argument list for --cache and enable the cache, else disable it
            cache.disable();
            for (String arg : args) {
                if (arg.equals("--cache")) {
                    cache.enable();
                }
            }

            // Connect to the loadbalancer
            Proxy loadBalancer = (Proxy) LocateRegistry.getRegistry("localhost", PORT).lookup("loadBalancer");

            // Open ./dataset/input.txt and read line by line
            BufferedReader reader = new BufferedReader(new FileReader("./dataset/input.txt"));
            String line = reader.readLine();

            while (line != null) {
                // Do something with the line
                System.out.println(line);

                // Input file has some double spaces, replace with single
                line = line.replaceAll("\\s+", " ");

                // split the line into an array
                String[] lineArray = line.split(" ");
                int zone = Integer.parseInt(lineArray[lineArray.length-1].split(":")[1]);

                switch( lineArray[0] ) {
                    case "getPopulationofCountry":
                        {
                            Result res = cache.get(lineArray[0], sliceArgs(lineArray, 1, lineArray.length-1));
                            if (res != null) {
                                LOGGER.info(res.toString());
                                res.waitingTime = res.executionTime = 0;
                                printResult(args, res, zone, cache.isEnabled());
                                break;
                            }
                            String country = String.join(" ", sliceArgs(lineArray, 1, lineArray.length-1));
                            TurnaroundTimer timer = new TurnaroundTimer();
                            res = getService(loadBalancer, zone).getPopulationOfCountry(country, zone);
                            LOGGER.info(res.toString());
                            printResult(lineArray, res, timer.clock(), cache.isEnabled());
                            getPopulationOfCountryStats.add(timer.clock(), res.executionTime, res.waitingTime);

                            cache.add(lineArray[0], sliceArgs(lineArray, 1, lineArray.length-1), res);
                            break;
                        }

                    case "getNumberofCities":
                        {
                            final int numArgs = 2;

                            Result res = cache.get(lineArray[0], sliceArgs(lineArray, 1, lineArray.length - numArgs));
                            if (res != null) {
                                LOGGER.info(res.toString());
                                res.waitingTime = res.executionTime = 0;
                                printResult(args, res, zone, cache.isEnabled());
                                break;
                            }

                            String country = String.join(" ", sliceArgs(lineArray, 1, lineArray.length - numArgs));
                            TurnaroundTimer timer = new TurnaroundTimer();
                            int minPopulation = Integer.parseInt(lineArray[lineArray.length - numArgs]);
                            res = getService(loadBalancer, zone).getNumberOfCities(country, minPopulation, zone);
                            LOGGER.info(res.toString());
                            printResult(lineArray, res, timer.clock(), cache.isEnabled());
                            getNumberOfCitiesStats.add(timer.clock(), res.executionTime, res.waitingTime);

                            cache.add(lineArray[0], sliceArgs(lineArray, 1, lineArray.length - numArgs), res);
                            break;
                        }
                        
                    case "getNumberofCountries":
                        {
                            if (lineArray.length == 5) {
                                // call getNumberofCountries with 3 arguments
                                final int numArgs = 3;

                                Result res = cache.get(lineArray[0], sliceArgs(lineArray, 1, lineArray.length - numArgs));
                                if (res != null) {
                                    LOGGER.info(res.toString());
                                    res.waitingTime = res.executionTime = 0;
                                    printResult(args, res, zone, cache.isEnabled());
                                    break;
                                }

                                int minCities = Integer.parseInt(lineArray[1]);
                                int minPopulation = Integer.parseInt(lineArray[2]);
                                int maxPopulation = Integer.parseInt(lineArray[3]);
                                TurnaroundTimer timer = new TurnaroundTimer();
                                res = getService(loadBalancer, zone).getNumberOfCountries(minCities, minPopulation, maxPopulation, zone);
                                LOGGER.info(res.toString());
                                printResult(lineArray, res, timer.clock(), cache.isEnabled());
                                getNumberOfCountries3Stats.add(timer.clock(), res.executionTime, res.waitingTime);

                                cache.add(lineArray[0], sliceArgs(lineArray, 1, lineArray.length - numArgs), res);
                                break;

                            } else {
                                // call getNumberofCountries with 2 arguments
                                final int numArgs = 2;

                                Result res = cache.get(lineArray[0], sliceArgs(lineArray, 1, lineArray.length - numArgs));
                                if (res != null) {
                                    LOGGER.info(res.toString());
                                    res.waitingTime = res.executionTime = 0;
                                    printResult(args, res, zone, cache.isEnabled());
                                    break;
                                }

                                int minCities = Integer.parseInt(lineArray[1]);
                                int minPopulation = Integer.parseInt(lineArray[2]);
                                TurnaroundTimer timer = new TurnaroundTimer();
                                res = getService(loadBalancer, zone).getNumberOfCountries(minCities, minPopulation, zone);
                                LOGGER.info(res.toString());
                                printResult(lineArray, res, timer.clock(), cache.isEnabled());
                                getNumberOfCountries2Stats.add(timer.clock(), res.executionTime, res.waitingTime);

                                cache.add(lineArray[0], sliceArgs(lineArray, 1, lineArray.length - numArgs), res);
                                break;
                            }
                            
                        }
                }

                line = reader.readLine();
            }
            reader.close();
        } catch (Exception e) {
            LOGGER.severe("Something went wrong on the client side while querying a service.");
            e.printStackTrace();
        }

        // Print statistics
        System.out.printf("--- Statistics:%n");

        String statline = String.format("%s turn around time: %.2f ms, execution time: %.2f ms, waiting time: %.2f ms, numcalls: %d%n",
            "getPopulationOfCountry",
            getPopulationOfCountryStats.avgTurnaround(),
            getPopulationOfCountryStats.avgExecution(),
            getPopulationOfCountryStats.avgWait(),
            getPopulationOfCountryStats.size()
            );
        System.out.println(statline);
        writeToLogFile("statistics.txt", statline);

        statline = String.format("%s turn around time: %.2f ms, execution time: %.2f ms, waiting time: %.2f ms, numcalls: %d%n",
            "getNumberOfCities",
            getNumberOfCitiesStats.avgTurnaround(),
            getNumberOfCitiesStats.avgExecution(),
            getNumberOfCitiesStats.avgWait(),
            getNumberOfCitiesStats.size()
            );
        System.out.println(statline);
        writeToLogFile("statistics.txt", statline);

        statline = String.format("%s turn around time: %.2f ms, execution time: %.2f ms, waiting time: %.2f ms, numcalls: %d%n",
            "getNumberOfCountries(minmax)",
            getNumberOfCountries2Stats.avgTurnaround(),
            getNumberOfCountries2Stats.avgExecution(),
            getNumberOfCountries2Stats.avgWait(),
            getNumberOfCountries2Stats.size()
            );
        System.out.println(statline);
        writeToLogFile("statistics.txt", statline);

        statline = String.format("%s turn around time: %.2f ms, execution time: %.2f ms, waiting time: %.2f ms, numcalls: %d%n",
            "getNumberOfCountries(min)",
            getNumberOfCountries3Stats.avgTurnaround(),
            getNumberOfCountries3Stats.avgExecution(),
            getNumberOfCountries3Stats.avgWait(),
            getNumberOfCountries3Stats.size()
            );
        System.out.println(statline);
        writeToLogFile("statistics.txt", statline);
    }

    private static void printResult(String[] args, Result res, long turnAroundTime, boolean clientCacheEnabled) {
        String command = String.join(" ", sliceArgs(args, 0, args.length - 1));

        String rstr = String.format("%d %s (turnaround time: %d ms, execution time: %d ms, waiting time: %d ms, processed by server #%d)", 
            res.result,
            command,
            turnAroundTime, // turnaround time
            res.executionTime,
            res.waitingTime,
            res.zone
            );

        System.out.println( rstr );

        // Write this to a log file as well
        writeToLogFile( decideFilename(res, clientCacheEnabled), rstr );
    }

    private static String decideFilename( Result res, boolean clientCacheEnabled ) {
        String filename = "naive_server.txt";
        if ( res.serverCacheEnabled ) {
            filename = "server_cache.txt";
        } else if ( clientCacheEnabled ) {
            filename = "client_cache.txt";
        }
        return filename;
    }

    private static void writeToLogFile(String filename, String str) {
        try {
            FileWriter fw = new FileWriter(filename, true);
            fw.write(str + "\n");
            fw.close();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error writing to file", e);
        }
    }

    private static String[] sliceArgs(String[] args, int start, int end) {
        String[] slicedArgs = new String[end - start];
        for (int i = start; i < end; i++) {
            slicedArgs[i - start] = args[i];
        }
        return slicedArgs;
    }

    private static StatisticsService getService(Proxy loadBalancer, int zone) throws Exception {
        String serverName = loadBalancer.chooseServer(zone);
        StatisticsService service = (StatisticsService) Naming.lookup(String.format("rmi://localhost:%d/%s", PORT, serverName));
        return service;
    }

    private static void runCluster(String[] args) {
        LOGGER.info("--- !!! Starting in Cluster Mode !!! ---");

        final int NUMBER_OF_SIM_CLIENTS = 20;
        CountDownLatch latch = new CountDownLatch(NUMBER_OF_SIM_CLIENTS);

        for (int i = 1; i <= NUMBER_OF_SIM_CLIENTS; i++) {
            final int index = i;

            new Thread(() -> {
                LOGGER.info("Starting process " + index);
                main(removeArg(args, cmdLineArgRunCluster));
                LOGGER.info("Process " + index + " completed");
                latch.countDown();
            }).start();
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            LOGGER.log(Level.SEVERE, "Interrupted while waiting for processes to complete", e);
        }

        LOGGER.info("--- !!! Cluster Mode Completed !!! ---");
    }

    private static String[] removeArg(String[] args, String argToRemove) {
        List<String> argList = new ArrayList<>(Arrays.asList(args));
        argList.remove(argToRemove);
        return argList.toArray(new String[0]);
    }
}
