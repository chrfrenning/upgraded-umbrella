package g7asmt1;

import g7asmt1.server.LoadBalancer;
import g7asmt1.server.TaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import java.rmi.RemoteException;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoadBalancerTest {
    private LoadBalancer lb;

    @BeforeEach
    void setUp() throws RemoteException {
        lb = new LoadBalancer(5);
    }

    @ParameterizedTest
    @CsvSource({"1, 1", "2, 2", "3, 3", "4, 4", "5, 5"})
    public void when_initialState_choose_sameZone(int clientZone, int expectedChosen) throws RemoteException {
        TaskManager tasks = new TaskManager(new int[] {0, 0, 0, 0, 0, 0});
        assertEquals(expectedChosen, lb.chooseFromList(clientZone, tasks));
    }

    @ParameterizedTest
    @CsvSource({"1, 1", "2, 2", "3, 3", "4, 4", "5, 5"})
    public void when_notBusy_choose_sameZone(int clientZone, int expectedChosen) throws RemoteException {
        TaskManager tasks = new TaskManager(new int[] {0, 1, 1, 1, 1, 1});
        assertEquals(expectedChosen, lb.chooseFromList(clientZone, tasks));
    }

    @ParameterizedTest
    @CsvSource({"1, 1", "2, 2", "3, 3", "4, 4", "5, 5"})
    public void when_busy_choose_sameZone(int clientZone, int expectedChosen) throws RemoteException {
        TaskManager tasks = new TaskManager(new int[] {0, 8, 8, 8, 8, 8});
        assertEquals(expectedChosen, lb.chooseFromList(clientZone, tasks));
    }

    @ParameterizedTest
    @CsvSource({"1, 2", "2, 2", "3, 3", "4, 5", "5, 5"})
    public void when_overloaded_choose_closestZone(int clientZone, int expectedChosen) throws RemoteException {
        TaskManager tasks = new TaskManager(new int[] {0, 18, 7, 7, 18, 7});
        assertEquals(expectedChosen, lb.chooseFromList(clientZone, tasks));
    }

    @ParameterizedTest
    @CsvSource({"1, 1", "2, 3", "3, 3", "4, 4", "5, 1"})
    public void when_overloaded_choose_closestZone2(int clientZone, int expectedChosen) throws RemoteException {
        TaskManager tasks = new TaskManager(new int[] {0, 7, 18, 7, 7, 18});
        assertEquals(expectedChosen, lb.chooseFromList(clientZone, tasks));
    }

    @ParameterizedTest
    @CsvSource({"1, 3", "2, 2", "3, 3", "4, 4", "5, 5"})
    public void when_overloaded_and_busyClosest_choose_neighbourZone(int clientZone, int expectedChosen) throws RemoteException {
        TaskManager tasks = new TaskManager(new int[] {0, 18, 8, 7, 7, 7});
        assertEquals(expectedChosen, lb.chooseFromList(clientZone, tasks));
    }

    @ParameterizedTest
    @CsvSource({"1, 1", "2, 2", "3, 3", "4, 4", "5, 2"})
    public void when_overloaded_and_busyClosest_choose_neighbourZone2(int clientZone, int expectedChosen) throws RemoteException {
        TaskManager tasks = new TaskManager(new int[] {0, 8, 7, 7, 7, 18});
        assertEquals(expectedChosen, lb.chooseFromList(clientZone, tasks));
    }

    @ParameterizedTest
    @CsvSource({"1, 4", "2, 2", "3, 3", "4, 4", "5, 4"})
    public void when_overloaded_and_busyClosest_and_busyNeighbour_choose_lessTasks(int clientZone, int expectedChosen) throws RemoteException {
        TaskManager tasks = new TaskManager(new int[] {0, 18, 8, 8, 7, 18});
        assertEquals(expectedChosen, lb.chooseFromList(clientZone, tasks));
    }

    @ParameterizedTest
    @CsvSource({"1, 5", "2, 5", "3, 5", "4, 5", "5, 5"})
    public void when_all_overloaded_choose_lessTasks5(int clientZone, int expectedChosen) throws RemoteException {
        TaskManager tasks = new TaskManager(new int[] {0, 18, 18, 18, 18, 18});
        assertEquals(expectedChosen, lb.chooseFromList(clientZone, tasks));
    }

    @ParameterizedTest
    @CsvSource({"1, 4", "2, 4", "3, 4", "4, 4", "5, 4"})
    public void when_all_overloaded_choose_lessTasks4(int clientZone, int expectedChosen) throws RemoteException {
        TaskManager tasks = new TaskManager(new int[] {0, 18, 18, 18, 18, 19});
        assertEquals(expectedChosen, lb.chooseFromList(clientZone, tasks));
    }
}
