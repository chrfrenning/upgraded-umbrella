import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Proxy extends Remote {
    String chooseServer(int clientZone) throws RemoteException;
}
