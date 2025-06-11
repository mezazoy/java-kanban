package ru.yandexTest.taskTracker.modelTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.taskTraker.model.Task;
import ru.yandex.taskTraker.service.HistoryManager;
import ru.yandex.taskTraker.service.Managers;
import ru.yandex.taskTraker.service.Status;
import ru.yandex.taskTraker.service.TaskManager;
import java.util.List;
import static ru.yandex.taskTraker.service.Status.NEW;

class TaskTest {
    TaskManager taskManager = Managers.getDefault();
    HistoryManager historyManager = Managers.getDefaultHistory();


    @Test
    void addNewTask() {
        Task task = new Task("Test addNewTask", "Test addNewTask description", NEW, "30", "25.05.25 11:00");

        final int taskId = taskManager.addTask(task);

        final Task savedTask = taskManager.getTaskByIdentifier(taskId);

        Assertions.assertNotNull(savedTask, "Задача не найдена.");
        Assertions.assertEquals(task, savedTask, "Задачи не совпадают.");

        final List<Task> tasks = taskManager.getTasks();

        Assertions.assertNotNull(tasks, "Задачи не возвращаются.");
        Assertions.assertEquals(1, tasks.size(), "Неверное количество задач.");
        Assertions.assertEquals(task, tasks.get(0), "Задачи не совпадают.");
    }

    @Test
    void addHistory() {
        Task task = new Task("Test addNewTask", "Test addNewTask description", NEW, "30", "25.05.25 11:00");

        historyManager.add(task);
        final List<Task> history = historyManager.getHistory();
        Assertions.assertNotNull(history, "История не пустая.");
        Assertions.assertEquals(1, history.size(), "История не пустая.");
    }

    @Test
    void immutabilityTask() {
        Task task = new Task("Test addNewTask", "Test addNewTask description", NEW, "30", "25.05.25 11:00");

        String descr1 = task.getDescription();
        String name1 = task.getTaskName();
        Status status1 = task.getStatusTask();
        int taskId = taskManager.addTask(task);
        Assertions.assertEquals(name1, taskManager.getTaskByIdentifier(taskId).getTaskName(), "Поле изменилось");
        Assertions.assertEquals(status1, taskManager.getTaskByIdentifier(taskId).getStatusTask(), "Поле изменилось");
        Assertions.assertEquals(descr1, taskManager.getTaskByIdentifier(
                taskId).getDescription(), "Поле изменилось");
    }


}