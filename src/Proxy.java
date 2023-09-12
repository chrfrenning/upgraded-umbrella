import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Proxy extends Remote {
    String chooseServer(String clientZone) throws RemoteException;
}
