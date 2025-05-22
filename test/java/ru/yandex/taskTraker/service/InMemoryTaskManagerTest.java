import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.taskTraker.model.Epic;
import ru.yandex.taskTraker.model.Subtask;
import ru.yandex.taskTraker.model.Task;
import ru.yandex.taskTraker.service.Status;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;
import ru.yandex.taskTraker.service.Managers;
import ru.yandex.taskTraker.service.TaskManager;

class InMemoryTaskManagerTest<T extends TaskManager> {
    TaskManager taskManager = Managers.getDefault();

    @Test
    void updateEpicStartTime() {
        taskManager.createEpic(new Epic("testName", "testDescr"));
        Epic epic = taskManager.getEpicByIdentifier(1);
        taskManager.createSubtask(new Subtask("subName", "descr", Status.NEW, epic.getId(), "25", "18.05.25 11:05"));
        taskManager.createSubtask(new Subtask("subName", "descr", Status.IN_PROGRESS, epic.getId(), "25", "18.05.25 15:05"));
        taskManager.createSubtask(new Subtask("subName", "descr", Status.DONE, epic.getId(), "25", "20.05.25 11:05"));

        Assertions.assertEquals(epic.getStartTime(), taskManager.getSubtasks().getFirst().getStartTime()
                , "Даты не совпадают!");
    }

    @Test
    void intersectionCheck() {
        taskManager.createTask(new Task("testName", "testDescr", Status.NEW, "25", "15.05.25 11:00"));
        taskManager.createTask(new Task("testName", "testDescr", Status.NEW, "25", "15.05.25 11:00"));
        taskManager.createTask(new Task("testName", "testDescr", Status.NEW, "25", "16.05.25 11:00"));
        taskManager.createTask(new Task("testName", "testDescr", Status.NEW, "25", "17.05.25 11:00"));
        taskManager.createTask(new Task("testName", "testDescr", Status.NEW, "25", "16.05.25 11:00"));

        Assertions.assertEquals(3, taskManager.getPrioritizedTasks().size(), "Добавлены пересекающиеся задачи!");
    }
}