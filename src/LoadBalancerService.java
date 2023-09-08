import java.rmi.Remote;
import java.rmi.RemoteException;

public interface LoadBalancerService extends Remote {
    String getStatisticsServer(String clientZone) throws RemoteException;
}
