package g7asmt1;

import g7asmt1.server.TaskManager;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import java.rmi.RemoteException;
import java.util.stream.Stream;

public class TaskManagerTest {
    private TaskManager tm;

    @BeforeEach
    void setUp() {
        tm = new TaskManager(5);
    }

    @ParameterizedTest
    @CsvSource({"1", "2", "3", "4", "5"})
    void testIsBusy(String zone){
        assert(tm.isBusy(zone));
    }
    @ParameterizedTest
    @MethodSource("testCases")
    public void testLessTasks(int[] counter, String expected) throws RemoteException {
        TaskManager taskManager = new TaskManager(counter);
        assertEquals(expected, taskManager.lessTasks());
    }

    private static Stream<Arguments> testCases() {
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
