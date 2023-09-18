package g7asmt1.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface StatisticsService extends Remote {
    Result getPopulationOfCountry(String countryName, int clientZone) throws RemoteException;
    Result getNumberOfCities(String countryName, int min, int clientZone) throws RemoteException;
    Result getNumberOfCountries(int cityCount, int minPopulation, int clientZone) throws RemoteException;
    Result getNumberOfCountries(int cityCount, int minPopulation, int maxPopulation, int clientZone) throws RemoteException;
    int getQueueLength() throws RemoteException;
}
