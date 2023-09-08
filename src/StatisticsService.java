import java.rmi.Remote;
import java.rmi.RemoteException;

public interface StatisticsService extends Remote {
    QueryResult getPopulationOfCountry(String countryName) throws RemoteException;
    QueryResult getNumberOfCities(String countryName, int min) throws RemoteException;
    QueryResult getNumberOfCountries(int cityCount, int minPopulation) throws RemoteException;
    QueryResult getNumberOfCountries(int cityCount, int minPopulation, int maxPopulation) throws RemoteException;
}
