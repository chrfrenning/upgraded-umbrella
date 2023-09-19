package g7asmt1;

import g7asmt1.server.ZoneManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class ZoneManagerTest {
    private ZoneManager zm;

    @BeforeEach
    void setUp() {
        zm = new ZoneManager(5);
    }

    @ParameterizedTest
    @CsvSource({"1, 1", "2, 2", "3, 3", "4, 4", "5, 5"})
    void isSameZoneTest(String zone, int clientZone){
        assert (zm.isSameZone(zone, clientZone));
    }

    @ParameterizedTest
    @CsvSource({"2, 1", "3, 2", "4, 3", "5, 4", "1, 5", "2, 6", "1, 0"})
    void isClosestZoneTest(String zone, int clientZone) {
        assert(zm.isClosestZone(zone, clientZone));
    }

    @ParameterizedTest
    @CsvSource({"3, 1", "4, 2", "5, 3", "1, 4", "2, 5"})
    void isNeighbourTest(String zone, int clientZone) {
        assert(zm.isNeighbour(zone, clientZone));
    }
}
