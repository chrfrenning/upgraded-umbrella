package server;

import client.Result;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface StatisticsService extends Remote {
    Result getPopulationOfCountry(String countryName) throws RemoteException;
    Result getNumberOfCities(String countryName, int min) throws RemoteException;
    Result getNumberOfCountries(int cityCount, int minPopulation) throws RemoteException;
    Result getNumberOfCountries(int cityCount, int minPopulation, int maxPopulation) throws RemoteException;
    int getQueueLength() throws RemoteException;
}
