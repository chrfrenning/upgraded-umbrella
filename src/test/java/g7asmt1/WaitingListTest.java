package g7asmt1;

import g7asmt1.server.WaitingList;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class WaitingListTest {

    @ParameterizedTest
    @CsvSource({"1", "2", "3", "4", "5"})
    void testIsBusy_notBusy(int zone){
        WaitingList wl = new WaitingList(new int[] {0, 1, 5, 6, 7, 0});
        assertFalse(wl.isBusy(zone));
    }

    @ParameterizedTest
    @CsvSource({"1", "2", "3", "4", "5"})
    void testIsBusy_busy(int zone){
        WaitingList wl = new WaitingList(new int[] {0, 8, 9, 17, 18, 16});
        assert(wl.isBusy(zone));
    }

    @ParameterizedTest
    @CsvSource({"1", "2", "3", "4", "5"})
    void testIsOverloaded_notOverloaded(int zone){
        WaitingList wl = new WaitingList(new int[] {0, 7, 8, 9, 17, 1});
        assertFalse(wl.isOverloaded(zone));
    }

    @ParameterizedTest
    @CsvSource({"1", "2", "3", "4", "5"})
    void testIsOverloaded_overloaded(int zone){
        WaitingList wl = new WaitingList(new int[] {0, 18, 19, 20, 100, 1000});
        assert(wl.isOverloaded(zone));
    }

}
