package ru.yandex.taskTracker.modelTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.taskTraker.model.Epic;
import ru.yandex.taskTraker.model.Subtask;
import ru.yandex.taskTraker.model.Task;
import ru.yandex.taskTraker.service.HistoryManager;
import ru.yandex.taskTraker.service.InMemoryTaskManager;
import ru.yandex.taskTraker.service.Managers;
import ru.yandex.taskTraker.service.Status;

import java.util.List;

public class SubtaskTest {
    InMemoryTaskManager taskManager = Managers.getDefault();
    HistoryManager historyManager = Managers.getDefaultHistory();

    @Test
    void addNewSubtask() {
        Epic epic = new Epic("Test addNewTask", "Test addNewTask description");
        Subtask subtask = new Subtask("test name", "test deskription", Status.NEW, epic.getId());
        taskManager.addEpic(epic);

        final int subtaskId = taskManager.addSubtask(subtask);

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
        taskManager.addEpic(epic);

        String descr1 = subtask.getDescription();
        String name1 = subtask.getTaskName();
        Status status1 = subtask.getStatusTask();
        int taskId = taskManager.addSubtask(subtask);
        Assertions.assertEquals(name1, taskManager.getSubtaskByIdentifier(taskId).getTaskName(), "Поле изменилось");
        Assertions.assertEquals(status1, taskManager.getSubtaskByIdentifier(taskId).getStatusTask(), "Поле изменилось");
        Assertions.assertEquals(descr1, taskManager.getSubtaskByIdentifier(
                taskId).getDescription(), "Поле изменилось");
    }

    @Test
    void addHistory() {
        Epic epic = new Epic("Test addNewTask", "Test addNewTask description");
        Subtask subtask = new Subtask("test name", "test deskription", Status.NEW, epic.getId());
        taskManager.addEpic(epic);

        historyManager.add(subtask);
        final List<Task> history = historyManager.getHistory();
        Assertions.assertNotNull(history, "История не пустая.");
        Assertions.assertEquals(1, history.size(), "История не пустая.");
    }
}
