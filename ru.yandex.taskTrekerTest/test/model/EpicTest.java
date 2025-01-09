package test.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.taskTreker.model.Epic;
import ru.yandex.taskTreker.model.Task;
import ru.yandex.taskTreker.service.HistoryManager;
import ru.yandex.taskTreker.service.Managers;
import ru.yandex.taskTreker.service.Status;
import ru.yandex.taskTreker.service.TaskManager;

import java.util.List;

import static ru.yandex.taskTreker.service.Status.NEW;


public class EpicTest {
    TaskManager taskManager = Managers.getDefault();
    HistoryManager historyManager = Managers.getDefaultHistory();

    @Test
    void addNewEpic() {
        Epic epic = new Epic("Test addNewTask", "Test addNewTask description");

        final int epicId = taskManager.add(epic);

        final Epic savedEpic = taskManager.getEpicByIdentifier(epicId);

        Assertions.assertNotNull(savedEpic, "Задача не найдена.");
        Assertions.assertEquals(epic, savedEpic, "Задачи не совпадают.");

        final List<Epic> epics = taskManager.getEpics();

        Assertions.assertNotNull(epics, "Задачи не возвращаются.");
        Assertions.assertEquals(1, epics.size(), "Неверное количество задач.");
        Assertions.assertEquals(epic, epics.get(0), "Задачи не совпадают.");
    }

    @Test
    void makeTheEpicYourOwnSubtask() {
        taskManager.createEpic(new Epic("Test addNewTask", "Test addNewTask description"));

        Epic epic = taskManager.getEpicByIdentifier(1);
        epic.addSubtaskId(epic.getId());
        Assertions.assertEquals(epic.getSubtasksId(), null, "Эпик добавлен как subtask!");
    }

    @Test
    void immutabilityTask() {
        Epic epic = new Epic("Test addNewTask", "Test addNewTask description");

        String descr1 = epic.getDescription();
        String name1 = epic.getTaskName();
        Status status1 = epic.getStatusTask();
        int taskId = taskManager.add(epic);
        Assertions.assertEquals(name1, taskManager.getEpicByIdentifier(taskId).getTaskName() ,"Поле изменилось");
        Assertions.assertEquals(status1, taskManager.getEpicByIdentifier(taskId).getStatusTask() ,"Поле изменилось");
        Assertions.assertEquals(descr1, taskManager.getEpicByIdentifier(
                taskId).getDescription() ,"Поле изменилось");
    }

    @Test
    void addHistory() {
        Epic epic = new Epic("Test addNewTask", "Test addNewTask description");

        historyManager.add(epic);
        final List<Task> history = historyManager.getHistory();
        Assertions.assertNotNull(history, "История не пустая.");
        Assertions.assertEquals(1, history.size(), "История не пустая.");
    }
}
