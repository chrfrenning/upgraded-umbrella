package g7asmt1.server;

public class ZoneManager {
    private final int amountOfServers;

    public ZoneManager(int amountOfServers) {
        this.amountOfServers = amountOfServers;
    }

    public int closest(int clientZone) {
        return clientZone % amountOfServers + 1;
    }

    public int neighbour(int clientZone) {
        return (clientZone + 1) % amountOfServers + 1;
    }
}
