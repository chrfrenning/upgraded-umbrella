package g7asmt1;

import g7asmt1.server.LoadBalancer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import java.rmi.RemoteException;

public class LoadBalancerTest {
    private LoadBalancer lb;

    @BeforeEach
    void setUp() throws RemoteException {
        lb = new LoadBalancer(5);
    }

    @ParameterizedTest
    @CsvSource({"1, 1", "2, 2", "3, 3", "4, 4", "5, 5"})
    void isSameZoneTest(String zone, int clientZone){
        assert (lb.isSameZone(zone, clientZone));
    }

    @ParameterizedTest
    @CsvSource({"2, 1", "3, 2", "4, 3", "5, 4", "1, 5", "2, 6", "1, 0"})
    void isClosestZoneTest(String zone, int clientZone) {
        assert(lb.isClosestZone(zone, clientZone));
    }

    @ParameterizedTest
    @CsvSource({"3, 1", "4, 2", "5, 3", "1, 4", "2, 5"})
    void isNeighbourTest(String zone, int clientZone) {
        assert(lb.isNeighbour(zone, clientZone));
    }
}
