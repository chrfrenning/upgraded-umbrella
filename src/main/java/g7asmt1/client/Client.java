package g7asmt1.client;

import g7asmt1.server.Proxy;
import g7asmt1.server.Result;
import g7asmt1.server.StatisticsService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.util.List;
import java.util.logging.Logger;

public class Client {
    private static final Logger LOGGER = Logger.getLogger(Client.class.getName());
    private static final int PORT = 1099;

    public static void main(String[] args) {
        try {
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
                                break;
                            }
                            String country = String.join(" ", sliceArgs(lineArray, 1, lineArray.length-1));
                            res = getService(loadBalancer, zone).getPopulationOfCountry(country, zone);
                            LOGGER.info(res.toString());

                            cache.add(lineArray[0], sliceArgs(lineArray, 1, lineArray.length-1), res);
                            break;
                        }

                    case "getNumberofCities":
                        {
                            final int numArgs = 2;

                            Result res = cache.get(lineArray[0], sliceArgs(lineArray, 1, lineArray.length - numArgs));
                            if (res != null) {
                                LOGGER.info(res.toString());
                                break;
                            }

                            String country = String.join(" ", sliceArgs(lineArray, 1, lineArray.length - numArgs));
                            int minPopulation = Integer.parseInt(lineArray[lineArray.length - numArgs]);
                            res = getService(loadBalancer, zone).getNumberOfCities(country, minPopulation, zone);
                            LOGGER.info(res.toString());

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
                                    break;
                                }

                                int minCities = Integer.parseInt(lineArray[1]);
                                int minPopulation = Integer.parseInt(lineArray[2]);
                                int maxPopulation = Integer.parseInt(lineArray[3]);
                                res = getService(loadBalancer, zone).getNumberOfCountries(minCities, minPopulation, maxPopulation, zone);
                                LOGGER.info(res.toString());

                                cache.add(lineArray[0], sliceArgs(lineArray, 1, lineArray.length - numArgs), res);
                                break;

                            } else {
                                // call getNumberofCountries with 2 arguments
                                final int numArgs = 2;

                                Result res = cache.get(lineArray[0], sliceArgs(lineArray, 1, lineArray.length - numArgs));
                                if (res != null) {
                                    LOGGER.info(res.toString());
                                    break;
                                }

                                int minCities = Integer.parseInt(lineArray[1]);
                                int minPopulation = Integer.parseInt(lineArray[2]);
                                res = getService(loadBalancer, zone).getNumberOfCountries(minCities, minPopulation, zone);
                                LOGGER.info(res.toString());

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
}
