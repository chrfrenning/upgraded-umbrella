package g7asmt1.client;

import g7asmt1.server.Proxy;
import g7asmt1.server.StatisticsService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.util.logging.Logger;

public class Client {
    private static final Logger LOGGER = Logger.getLogger(Client.class.getName());
    private static final int PORT = 1099;

    public static void main(String[] args) {
        try {
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
                            String country = String.join(" ", sliceArgs(lineArray, 1, lineArray.length-1));
                            LOGGER.info(getService(loadBalancer, zone).getPopulationOfCountry(country, zone).toString());
                            break;
                        }

                    case "getNumberofCities":
                        {
                            final int numArgs = 2;
                            String country = String.join(" ", sliceArgs(lineArray, 1, lineArray.length - numArgs));
                            int minPopulation = Integer.parseInt(lineArray[lineArray.length - numArgs]);
                            LOGGER.info(getService(loadBalancer, zone).getNumberOfCities(country, minPopulation, zone).toString());
                            break;
                        }
                        
                    case "getNumberofCountries":
                        {
                            if (lineArray.length == 5) {
                                // call getNumberofCountries with 3 arguments
                                int minCities = Integer.parseInt(lineArray[2]);
                                int minPopulation = Integer.parseInt(lineArray[3]);
                                int maxPopulation = Integer.parseInt(lineArray[4]);
                                LOGGER.info(getService(loadBalancer, zone).getNumberOfCountries(minCities, minPopulation, maxPopulation, zone).toString());
                            } else {
                                // call getNumberofCountries with 2 arguments
                                int minCities = Integer.parseInt(lineArray[2]);
                                int minPopulation = Integer.parseInt(lineArray[3]);
                                LOGGER.info(getService(loadBalancer, zone).getNumberOfCountries(minCities, minPopulation, zone).toString());
                            }
                            break;
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
