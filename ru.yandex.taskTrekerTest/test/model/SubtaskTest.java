package test.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.taskTreker.model.Epic;
import ru.yandex.taskTreker.model.Subtask;
import ru.yandex.taskTreker.model.Task;
import ru.yandex.taskTreker.service.HistoryManager;
import ru.yandex.taskTreker.service.InMemoryTaskManager;
import ru.yandex.taskTreker.service.Managers;
import ru.yandex.taskTreker.service.Status;

import java.util.List;

public class SubtaskTest {
    InMemoryTaskManager taskManager = new InMemoryTaskManager();
    HistoryManager historyManager = Managers.getDefaultHistory();

    @Test
    void addNewSubtask() {
        Epic epic = new Epic("Test addNewTask", "Test addNewTask description");
        Subtask subtask = new Subtask("test name", "test deskription", Status.NEW, epic.getId());

        final int subtaskId = taskManager.add(subtask);

        final Subtask savedSubtask = taskManager.getSubtaskByIdentifier(subtaskId);

        Assertions.assertNotNull(savedSubtask, "Задача не найдена.");
        Assertions.assertEquals(subtask, savedSubtask, "Задачи не совпадают.");

        final List<Subtask> subtasks = taskManager.getSubtasks();

        Assertions.assertNotNull(subtasks, "Задачи не возвращаются.");
        Assertions.assertEquals(1, subtasks.size(), "Неверное количество задач.");
        Assertions.assertEquals(subtask, subtasks.get(0), "Задачи не совпадают.");
    }

    @Test
    void immutabilityTask() {
        Epic epic = new Epic("Test addNewTask", "Test addNewTask description");
        Subtask subtask = new Subtask("test name", "test deskription", Status.NEW, epic.getId());

        String descr1 = subtask.getDescription();
        String name1 = subtask.getTaskName();
        Status status1 = subtask.getStatusTask();
        int taskId = taskManager.add(subtask);
        Assertions.assertEquals(name1, taskManager.getTaskByIdentifier(taskId).getTaskName() ,"Поле изменилось");
        Assertions.assertEquals(status1, taskManager.getTaskByIdentifier(taskId).getStatusTask() ,"Поле изменилось");
        Assertions.assertEquals(descr1, taskManager.getTaskByIdentifier(
                taskId).getDescription() ,"Поле изменилось");
    }

    @Test
    void addHistory() {
        Epic epic = new Epic("Test addNewTask", "Test addNewTask description");
        Subtask subtask = new Subtask("test name", "test deskription", Status.NEW, epic.getId());

        historyManager.add(subtask);
        final List<Task> history = historyManager.getHistory();
        Assertions.assertNotNull(history, "История не пустая.");
        Assertions.assertEquals(1, history.size(), "История не пустая.");
    }
}
