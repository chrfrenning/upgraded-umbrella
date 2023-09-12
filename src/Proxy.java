import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Proxy extends Remote {
    String choseServer(String clientZone) throws RemoteException;
}
