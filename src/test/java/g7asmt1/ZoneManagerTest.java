package g7asmt1;

import g7asmt1.server.ZoneManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ZoneManagerTest {
    private ZoneManager zm;

    @BeforeEach
    void setUp() {
        zm = new ZoneManager(5);
    }

    @ParameterizedTest
    @CsvSource({"1, 2", "2, 3", "3, 4", "4, 5", "5, 1"})
    void testClosestZone(int clientZone, int closestZone){
        assertEquals(closestZone, zm.closestZone(clientZone));
    }

    @ParameterizedTest
    @CsvSource({"1, 3", "2, 4", "3, 5", "4, 1", "5, 2"})
    void testNeighbourZone(int clientZone, int neighbourZone){
        assertEquals(neighbourZone, zm.neighbourZone(clientZone));
    }
}
