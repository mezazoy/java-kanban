import org.junit.jupiter.api.Test;
import ru.yandex.taskTraker.service.HistoryManager;
import ru.yandex.taskTraker.service.Managers;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest<T extends HistoryManager> {
    HistoryManager historyManager = Managers.getDefaultHistory();

    @Test
    void add() {
    }

    @Test
    void getHistory() {
    }

    @Test
    void remove() {
    }
}