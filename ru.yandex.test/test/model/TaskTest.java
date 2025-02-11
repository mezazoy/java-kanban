package test.model;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.taskTraker.model.Task;
import ru.yandex.taskTraker.service.*;

import java.util.List;

import static ru.yandex.taskTraker.service.Status.NEW;

class TaskTest {
    TaskManager taskManager = Managers.getDefault();
    HistoryManager historyManager = Managers.getDefaultHistory();


    @Test
    void addNewTask() {
        Task task = new Task("Test addNewTask", "Test addNewTask description", NEW);

        final int taskId = taskManager.add(task);

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
        Task task = new Task("Test addNewTask", "Test addNewTask description", NEW);

        historyManager.add(task);
        final List<Task> history = historyManager.getHistory();
        Assertions.assertNotNull(history, "История не пустая.");
        Assertions.assertEquals(1, history.size(), "История не пустая.");
    }

    @Test
    void immutabilityTask() {
        Task task = new Task("Test addNewTask", "Test addNewTask description", NEW);

        String descr1 = task.getDescription();
        String name1 = task.getTaskName();
        Status status1 = task.getStatusTask();
        int taskId = taskManager.add(task);
        Assertions.assertEquals(name1, taskManager.getTaskByIdentifier(taskId).getTaskName(), "Поле изменилось");
        Assertions.assertEquals(status1, taskManager.getTaskByIdentifier(taskId).getStatusTask(), "Поле изменилось");
        Assertions.assertEquals(descr1, taskManager.getTaskByIdentifier(
                taskId).getDescription(), "Поле изменилось");
    }


}