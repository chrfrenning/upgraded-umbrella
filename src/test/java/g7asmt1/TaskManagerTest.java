package g7asmt1;

import g7asmt1.server.TaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class TaskManagerTest {
    private TaskManager tm;

    @BeforeEach
    void setUp() {
        tm = new TaskManager(5);
    }

    @ParameterizedTest
    @CsvSource({"1", "2", "3", "4", "5"})
    void isBusyTest(String zone){
        assert(tm.isBusy(zone));
    }
}
