package g7asmt1;

import g7asmt1.server.TaskManager;
import g7asmt1.server.WaitingList;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;

public class TaskManagerTest {

    @ParameterizedTest
    @CsvSource({"1, 1", "2, 2", "3, 3", "4, 4", "5, 5"})
    public void when_initialState_choose_sameZone(int clientZone, int expected) {
        TaskManager tasks = new TaskManager(5, new WaitingList(new int[] {0, 0, 0, 0, 0, 0}));
        assertEquals(expected, tasks.chooseForZone(clientZone));
    }

    @ParameterizedTest
    @CsvSource({"1, 1", "2, 2", "3, 3", "4, 4", "5, 5"})
    public void when_notBusy_choose_sameZone(int clientZone, int expectedChosen) {
        TaskManager tasks = new TaskManager(5, new WaitingList(new int[] {0, 1, 1, 1, 1, 1}));
        assertEquals(expectedChosen, tasks.chooseForZone(clientZone));
    }

    @ParameterizedTest
    @CsvSource({"1, 1", "2, 2", "3, 3", "4, 4", "5, 5"})
    public void when_busy_choose_sameZone(int clientZone, int expectedChosen) {
        TaskManager tasks = new TaskManager(5, new WaitingList(new int[] {0, 8, 8, 8, 8, 8}));
        assertEquals(expectedChosen, tasks.chooseForZone(clientZone));
    }

    @ParameterizedTest
    @CsvSource({"1, 2", "2, 2", "3, 3", "4, 5", "5, 5"})
    public void when_overloaded_choose_closestZone_if_less_then_neighbour(int clientZone, int expectedChosen) {
        TaskManager tasks = new TaskManager(5, new WaitingList(new int[] {0, 18, 6, 7, 18, 7}));
        assertEquals(expectedChosen, tasks.chooseForZone(clientZone));
    }

    @ParameterizedTest
    @CsvSource({"1, 1", "2, 3", "3, 3", "4, 4", "5, 1"})
    public void when_overloaded_choose_closestZone2_if_less_then_neighbour(int clientZone, int expectedChosen) {
        TaskManager tasks = new TaskManager(5, new WaitingList(new int[] {0, 7, 18, 6, 7, 18}));
        assertEquals(expectedChosen, tasks.chooseForZone(clientZone));
    }

    @ParameterizedTest
    @CsvSource({"1, 3", "2, 2", "3, 3", "4, 4", "5, 5"})
    public void when_overloaded_and_busyClosest_choose_neighbourZone(int clientZone, int expectedChosen) {
        TaskManager tasks = new TaskManager(5, new WaitingList(new int[] {0, 18, 8, 7, 7, 7}));
        assertEquals(expectedChosen, tasks.chooseForZone(clientZone));
    }

    @ParameterizedTest
    @CsvSource({"1, 1", "2, 2", "3, 3", "4, 4", "5, 2"})
    public void when_overloaded_and_busyClosest_choose_neighbourZone2(int clientZone, int expectedChosen) {
        TaskManager tasks = new TaskManager(5, new WaitingList(new int[] {0, 8, 7, 7, 7, 18}));
        assertEquals(expectedChosen, tasks.chooseForZone(clientZone));
    }

    @ParameterizedTest
    @CsvSource({"1, 2", "2, 2", "3, 3", "4, 4", "5, 2"})
    public void when_overloaded_and_busyClosest_and_busyNeighbour_choose_leastTasks(int clientZone, int expectedChosen) {
        TaskManager tasks = new TaskManager(5, new WaitingList(new int[] {0, 18, 8, 9, 7, 18}));
        assertEquals(expectedChosen, tasks.chooseForZone(clientZone));
    }
/* // In order to mock randomness additional dependencies required
    @ParameterizedTest
    @CsvSource({"1, 2", "2, 2", "3, 3", "4, 4", "5, 2"})
    public void when_overloaded_and_equallyBusy_Closest_and_Neighbour_choose_randomly(int clientZone, int expectedChosen) {
        TaskManager tasks = new TaskManager(new int[] {0, 18, 9, 9, 7, 18});
        assertEquals(expectedChosen, tasks.chooseForZone(clientZone));
    }*/

    @ParameterizedTest
    @CsvSource({"1, 1", "2, 2", "3, 3", "4, 4", "5, 5"})
    public void when_all_overloaded_choose_sameZone(int clientZone, int expectedChosen)  {
        TaskManager tasks = new TaskManager(5, new WaitingList(new int[] {0, 18, 18, 18, 18, 19}));
        assertEquals(expectedChosen, tasks.chooseForZone(clientZone));
    }
}
