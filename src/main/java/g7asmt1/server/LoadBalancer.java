package g7asmt1.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class LoadBalancer extends UnicastRemoteObject implements Proxy {
    /** DECISION: final amount of servers
     * The amount of servers is final in this implementation to achieve simplicity.
     * In real world systems the complexity should be worth it -- a load balancer
     * should  be able to handle new servers added or removed without disturbance of the
     * unchanged servers' progress.
    */
    private final TaskManager tasks;

    public LoadBalancer(int amountOfServers) throws RemoteException {
        this.tasks = new TaskManager(amountOfServers);
    }

    @Override
    public String chooseServer(int clientZone) throws RemoteException {
        int chosen = tasks.chooseForZone(clientZone);
        tasks.incrementCounterForZone(chosen);
        return String.valueOf(chosen);
    }
}
