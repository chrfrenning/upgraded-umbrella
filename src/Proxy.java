import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Proxy extends Remote {
    String getStatisticsServer(String clientZone) throws RemoteException;
}
