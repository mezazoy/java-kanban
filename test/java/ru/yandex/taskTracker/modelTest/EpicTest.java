package ru.yandex.taskTracker.modelTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.taskTraker.model.Epic;
import ru.yandex.taskTraker.model.Subtask;
import ru.yandex.taskTraker.model.Task;
import ru.yandex.taskTraker.service.HistoryManager;
import ru.yandex.taskTraker.service.Managers;
import ru.yandex.taskTraker.service.Status;
import ru.yandex.taskTraker.service.TaskManager;

import java.util.List;

public class EpicTest {
    TaskManager taskManager = Managers.getDefault();
    HistoryManager historyManager = Managers.getDefaultHistory();

    @Test
    void addNewEpic() {
        Epic epic = new Epic("Test addNewTask", "Test addNewTask description");

        final int epicId = taskManager.addEpic(epic);

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
        Assertions.assertEquals(epic.getSubtasksId().size(), 0, "Эпик добавлен как subtask!");
    }

    @Test
    void immutabilityTask() {
        Epic epic = new Epic("Test addNewTask", "Test addNewTask description");

        String descr1 = epic.getDescription();
        String name1 = epic.getTaskName();
        Status status1 = epic.getStatusTask();
        int taskId = taskManager.addEpic(epic);
        Assertions.assertEquals(name1, taskManager.getEpicByIdentifier(taskId).getTaskName(), "Поле изменилось");
        Assertions.assertEquals(status1, taskManager.getEpicByIdentifier(taskId).getStatusTask(), "Поле изменилось");
        Assertions.assertEquals(descr1, taskManager.getEpicByIdentifier(
                taskId).getDescription(), "Поле изменилось");
    }

    @Test
    void addHistory() {
        Epic epic = new Epic("Test addNewTask", "Test addNewTask description");

        historyManager.add(epic);
        final List<Task> history = historyManager.getHistory();
        Assertions.assertNotNull(history, "История не пустая.");
        Assertions.assertEquals(1, history.size(), "История не пустая.");
    }

    @Test
    void epicStatusTest() {
        taskManager.createEpic(new Epic("Test addNewTask1", "Test addNewTask description"));
        taskManager.createEpic(new Epic("Test addNewTask2", "Test addNewTask description"));
        taskManager.createEpic(new Epic("Test addNewTask3", "Test addNewTask description"));
        taskManager.createEpic(new Epic("Test addNewTask4", "Test addNewTask description"));
        Epic epic1 = taskManager.getEpicByIdentifier(1);
        Epic epic2 = taskManager.getEpicByIdentifier(2);
        Epic epic3 = taskManager.getEpicByIdentifier(3);
        Epic epic4 = taskManager.getEpicByIdentifier(4);
        taskManager.createSubtask(new Subtask("Test", "test", Status.NEW, epic1.getId(), "10", "12.05.25 10:20"));
        taskManager.createSubtask(new Subtask("Test", "test", Status.NEW, epic1.getId(), "60", "12.05.25 11:20"));
        Assertions.assertEquals(Status.NEW, epic1.getStatusTask(), "Статус расчитан верно");

        taskManager.createSubtask(new Subtask("Test", "test", Status.DONE, epic2.getId(), "10", "13.05.25 10:20"));
        taskManager.createSubtask(new Subtask("Test", "test", Status.DONE, epic2.getId(), "60", "13.05.25 11:20"));
        Assertions.assertEquals(Status.DONE, epic2.getStatusTask(), "Статус рассчитан верно");

        taskManager.createSubtask(new Subtask("Test", "test", Status.NEW, epic3.getId(), "10", "14.05.25 10:20"));
        taskManager.createSubtask(new Subtask("Test", "test", Status.DONE, epic3.getId(), "60", "14.05.25 11:20"));
        Assertions.assertEquals(Status.IN_PROGRESS, epic3.getStatusTask(), "Статус рассчитан верно");


        taskManager.createSubtask(new Subtask("Test", "test", Status.NEW, epic4.getId(), "10", "15.05.25 10:20"));
        taskManager.createSubtask(new Subtask("Test", "test", Status.IN_PROGRESS, epic4.getId(), "60", "15.05.25 11:20"));
        taskManager.createSubtask(new Subtask("Test", "test", Status.DONE, epic4.getId(), "60", "15.05.25 15:20"));
        Assertions.assertEquals(Status.IN_PROGRESS, epic4.getStatusTask(), "Статус рассчитан верно");
    }
}
