import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.taskTraker.model.Epic;
import ru.yandex.taskTraker.model.Subtask;
import ru.yandex.taskTraker.model.Task;
import ru.yandex.taskTraker.service.Managers;
import ru.yandex.taskTraker.service.Status;
import ru.yandex.taskTraker.service.TaskManager;
import java.util.List;

class TaskManagerTest<T extends TaskManager> {
    TaskManager taskManager = Managers.getDefault();

    @Test
    void addTask() {
        Task task = new Task("Testname", "testDeskription", Status.NEW, "30", "15.10.25 14:50");
        int taskId = taskManager.addTask(task);
        final Task savedTask = taskManager.getTaskByIdentifier(taskId);

        Assertions.assertNotNull(savedTask, "Задача не найдена.");
        Assertions.assertEquals(task, savedTask, "Задачи не совпадают.");
    }

    @Test
    void addEpic() {
        Epic epic = new Epic("Testname", "testDeskription");
        int epicId = taskManager.addTask(epic);
        final Task savedEpic = taskManager.getTaskByIdentifier(epicId);

        Assertions.assertNotNull(savedEpic, "Задача не найдена.");
        Assertions.assertEquals(epic, savedEpic, "Задачи не совпадают.");
    }

    @Test
    void addSubtask() {
        Epic epic = new Epic("Testname", "testDeskription");
        Subtask subtask = new Subtask("test name", "test deskription", Status.NEW, epic.getId(), "30"
                , "25.05.25 11:00");
        taskManager.addEpic(epic);

        int subtaskId = taskManager.addTask(subtask);
        final Task savedSubtask = taskManager.getTaskByIdentifier(subtaskId);

        Assertions.assertNotNull(savedSubtask, "Задача не найдена.");
        Assertions.assertEquals(subtask, savedSubtask, "Задачи не совпадают.");

        int epicId = subtask.getEpicId();
        Epic relatedEpicWithSubtask = taskManager.getEpicByIdentifier(epicId);

        Assertions.assertNotNull(relatedEpicWithSubtask, "Задача не найдена.");
        Assertions.assertEquals(epic, relatedEpicWithSubtask, "Задачи не совпадают.");
    }

    @Test
    void getTasks() {
        Task task = new Task("Testname", "testDeskription", Status.NEW, "30", "15.10.25 14:50");
        Task task1 = new Task("Testname1", "testDeskription1", Status.NEW, "30", "16.10.25 15:50");
        task.setId(1);
        task1.setId(2);
        taskManager.addTask(task);
        taskManager.addTask(task1);
        List<Task> tasks = taskManager.getTasks();

        Assertions.assertFalse(tasks.isEmpty(), "Менеджер вернул задачи!");
        Assertions.assertEquals(2, tasks.size(), "Менеджер вернул верное количество задач!");
    }

    @Test
    void createTask() {
        taskManager.createTask(new Task("testName", "testDescr", Status.NEW, "25", "19.05.25 11:00"));
        List<Task> tasks = taskManager.getTasks();

        Assertions.assertFalse(tasks.isEmpty(), "Менеджер добавил задачу!");
        Assertions.assertNotNull(tasks.getFirst(), "Менеджер создал не пустую задачу!");
    }

    @Test
    void deleteAllTasks() {
        taskManager.createTask(new Task("testName", "testDescr", Status.NEW, "25", "15.05.25 11:00"));
        taskManager.createTask(new Task("testName", "testDescr", Status.NEW, "25", "16.05.25 11:00"));
        taskManager.createTask(new Task("testName", "testDescr", Status.NEW, "25", "17.05.25 11:00"));
        taskManager.createTask(new Task("testName", "testDescr", Status.NEW, "25", "18.05.25 11:00"));
        taskManager.createTask(new Task("testName", "testDescr", Status.NEW, "25", "19.05.25 11:00"));
        List<Task> tasks = taskManager.getTasks();

        Assertions.assertFalse(tasks.isEmpty(), "задачи добавлены!");

        taskManager.deleteAllTasks();
        List<Task> tasks1 = taskManager.getTasks();

        Assertions.assertFalse(!tasks1.isEmpty(), "Все задачи удалены!");
    }

    @Test
    void getTaskByIdentifier() {
        taskManager.createTask(new Task("testName", "testDescr", Status.NEW, "25", "15.05.25 11:00"));
        taskManager.createTask(new Task("testName", "testDescr", Status.NEW, "25", "16.05.25 11:00"));
        taskManager.createTask(new Task("testName", "testDescr", Status.NEW, "25", "17.05.25 11:00"));


    }

    @Test
    void updateTask() {
    }

    @Test
    void deleteTaskByIdentifier() {
    }

    @Test
    void getEpics() {
    }

    @Test
    void createEpic() {
    }

    @Test
    void deleteAllEpics() {
    }

    @Test
    void getEpicByIdentifier() {
    }

    @Test
    void updateEpic() {
    }

    @Test
    void deleteEpicByIdentifier() {
    }

    @Test
    void getSubtaskForEpic() {
    }

    @Test
    void getSubtasks() {
    }

    @Test
    void createSubtask() {
    }

    @Test
    void deleteAllSubtasks() {
    }

    @Test
    void getSubtaskByIdentifier() {
    }

    @Test
    void updateSubtask() {
    }

    @Test
    void deleteSubtaskByIdentifier() {
    }

    @Test
    void updateStatusEpic() {
    }

    @Test
    void getStatus() {
    }

    @Test
    void getPrioritizedTasks() {
    }
}