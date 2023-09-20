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
    private final WaitingList counter;

    public LoadBalancer(int amountOfServers) throws RemoteException {
        counter = new WaitingList(new int[amountOfServers + 1]); // add 1 to simplify conversion from zone to indexes
        this.tasks = new TaskManager(amountOfServers, counter);
    }

    @Override
    public String chooseServer(int clientZone) throws RemoteException {
        int chosen = tasks.chooseForZone(clientZone);
        tasks.incrementCounterForZone(chosen);
        return String.valueOf(chosen);
    }
}
