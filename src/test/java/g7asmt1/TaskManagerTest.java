package g7asmt1;

import g7asmt1.server.TaskManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import java.rmi.RemoteException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class TaskManagerTest {

    @ParameterizedTest
    @CsvSource({"1", "2", "3", "4", "5"})
    void testIsBusy_notBusy(int zone){
        TaskManager tm = new TaskManager(new int[] {0, 1, 5, 6, 7, 0});
        assertFalse(tm.isBusy(zone));
    }

    @ParameterizedTest
    @CsvSource({"1", "2", "3", "4", "5"})
    void testIsBusy_busy(int zone){
        TaskManager tm = new TaskManager(new int[] {0, 8, 9, 17, 18, 16});
        assert(tm.isBusy(zone));
    }

    @ParameterizedTest
    @CsvSource({"1", "2", "3", "4", "5"})
    void testIsOverloaded_notOverloaded(int zone){
        TaskManager tm = new TaskManager(new int[] {0, 7, 8, 9, 17, 1});
        assertFalse(tm.isOverloaded(zone));
    }

    @ParameterizedTest
    @CsvSource({"1", "2", "3", "4", "5"})
    void testIsOverloaded_overloaded(int zone){
        TaskManager tm = new TaskManager(new int[] {0, 18, 19, 20, 100, 1000});
        assert(tm.isOverloaded(zone));
    }

    @ParameterizedTest
    @MethodSource("testCases_lessTasks")
    public void testLessTasks(int[] counter, int expected) throws RemoteException {
        TaskManager tm = new TaskManager(counter);
        assertEquals(expected, tm.lessTasks());
    }

    private static Stream<Arguments> testCases_lessTasks() {
        return Stream.of(
                Arguments.of(new int[] {0, 1, 2, 3, 4, 5}, "1"),      // Minimum at index 0
                Arguments.of(new int[] {0, 5, 3, 2, 1, 4}, "4"),   // Minimum at index 3
                Arguments.of(new int[] {0, 5, 5, 5}, "3"),         // Minimum at index 0 (ties go to the first occurrence)
                Arguments.of(new int[] {0, 6}, "1")               // Single meaningful element
        );
    }

    @Test
    public void testLessTasksWithException_notInitialized() {
        TaskManager taskManager = new TaskManager(new int[0]); // An empty array that will trigger the exception
        assertThrows(RemoteException.class, taskManager::lessTasks);
    }

    @Test
    public void testLessTasksWithException_index0() {
        TaskManager taskManager = new TaskManager(new int[] {0}); // An empty array that will trigger the exception
        assertThrows(RemoteException.class, taskManager::lessTasks);
    }
}
