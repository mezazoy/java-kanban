import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.taskTraker.model.Epic;
import ru.yandex.taskTraker.model.Subtask;
import ru.yandex.taskTraker.model.Task;
import ru.yandex.taskTraker.service.Managers;
import ru.yandex.taskTraker.service.Status;
import ru.yandex.taskTraker.service.TaskManager;
import ru.yandex.taskTraker.service.TaskTypes;
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

        Assertions.assertFalse(tasks.isEmpty(), "Менеджер  не вернул задачи!");
        Assertions.assertEquals(2, tasks.size(), "Менеджер вернул не верное количество задач!");
    }

    @Test
    void createTask() {
        taskManager.createTask(new Task("testName", "testDescr", Status.NEW, "25", "19.05.25 11:00"));
        List<Task> tasks = taskManager.getTasks();

        Assertions.assertFalse(tasks.isEmpty(), "Менеджер не добавил задачу!");
        Assertions.assertNotNull(tasks.getFirst(), "Менеджер создал пустую задачу!");
    }

    @Test
    void deleteAllTasks() {
        taskManager.createTask(new Task("testName", "testDescr", Status.NEW, "25", "15.05.25 11:00"));
        taskManager.createTask(new Task("testName", "testDescr", Status.NEW, "25", "16.05.25 11:00"));
        taskManager.createTask(new Task("testName", "testDescr", Status.NEW, "25", "17.05.25 11:00"));
        taskManager.createTask(new Task("testName", "testDescr", Status.NEW, "25", "18.05.25 11:00"));
        taskManager.createTask(new Task("testName", "testDescr", Status.NEW, "25", "19.05.25 11:00"));
        List<Task> tasks = taskManager.getTasks();

        Assertions.assertFalse(tasks.isEmpty(), "задачи не добавлены!");

        taskManager.deleteAllTasks();
        List<Task> tasks1 = taskManager.getTasks();

        Assertions.assertFalse(!tasks1.isEmpty(), "Не все задачи удалены!");
    }

    @Test
    void getTaskByIdentifier() {
        taskManager.createTask(new Task("testName", "testDescr", Status.NEW, "25", "15.05.25 11:00"));
        taskManager.createTask(new Task("testName", "testDescr", Status.NEW, "25", "16.05.25 11:00"));
        taskManager.createTask(new Task("testName", "testDescr", Status.NEW, "25", "17.05.25 11:00"));
        Task task = taskManager.getTasks().getFirst();
        Task task1 = taskManager.getTaskByIdentifier(1);

        Assertions.assertSame(task, task1, "Задачи не совпадают!");
    }

    @Test
    void updateTask() {
        taskManager.createTask(new Task("testName", "testDescr", Status.NEW, "25", "15.05.25 11:00"));
        Task updateTask = new Task("testName1", "testDescrip", Status.DONE, "30", "16.05.25 11:00");
        updateTask.setTaskType(TaskTypes.TASK);
        taskManager.updateTask(1, updateTask);
        Task task = taskManager.getTaskByIdentifier(1);

        Assertions.assertSame(updateTask, task, "Задачи не совпадают!");
    }

    @Test
    void deleteTaskByIdentifier() {
        taskManager.createTask(new Task("testName", "testDescr", Status.NEW, "25", "15.05.25 11:00"));
        taskManager.createTask(new Task("testName", "testDescr", Status.NEW, "25", "16.05.25 11:00"));
        taskManager.createTask(new Task("testName", "testDescr", Status.NEW, "25", "17.05.25 11:00"));
        taskManager.deleteTaskByIdentifier(1);
        List<Task> tasks1 = taskManager.getTasks();

        Assertions.assertEquals(2, tasks1.size(), "Количество задач не изменилось, задача не удалена!");
    }

    @Test
    void getEpics() {
        Epic epic = new Epic("Testname", "testDeskription");
        Epic epic1 = new Epic("Testname1", "testDeskription1");
        epic.setId(1);
        epic1.setId(2);
        taskManager.addEpic(epic);
        taskManager.addEpic(epic1);
        List<Epic> epics = taskManager.getEpics();

        Assertions.assertFalse(epics.isEmpty(), "Менеджер  не вернул Эпики!");
        Assertions.assertEquals(2, epics.size(), "Менеджер вернул не верное количество Эпиков!");
    }

    @Test
    void createEpic() {
        taskManager.createEpic(new Epic("testName", "testDescr"));
        List<Epic> epics = taskManager.getEpics();

        Assertions.assertFalse(epics.isEmpty(), "Менеджер не добавил эпик!");
        Assertions.assertNotNull(epics.getFirst(), "Менеджер создал пустой эпик!");
    }

    @Test
    void deleteAllEpics() {
        taskManager.createEpic(new Epic("testName", "testDescr"));
        taskManager.createEpic(new Epic("testName1", "testDescr"));
        taskManager.createEpic(new Epic("testName2", "testDescr"));
        taskManager.createEpic(new Epic("testName3", "testDescr"));
        taskManager.createEpic(new Epic("testName4", "testDescr"));
        List<Epic> epics = taskManager.getEpics();

        Assertions.assertFalse(epics.isEmpty(), "задачи не добавлены!");

        taskManager.deleteAllEpics();
        List<Epic> epics1 = taskManager.getEpics();

        Assertions.assertFalse(!epics1.isEmpty(), "Не все задачи удалены!");
    }

    @Test
    void getEpicByIdentifier() {
        taskManager.createEpic(new Epic("testName", "testDescr"));
        taskManager.createEpic(new Epic("testName", "testDescr"));
        taskManager.createEpic(new Epic("testName", "testDescr"));
        Task epic = taskManager.getEpics().getFirst();
        Task epic1 = taskManager.getEpicByIdentifier(1);

        Assertions.assertSame(epic, epic1, "Задачи не совпадают!");
    }

    @Test
    void updateEpic() {
        taskManager.createEpic(new Epic("testName", "testDescr"));
        Epic updateEpic = new Epic("testName1", "testDescrip");
        updateEpic.setTaskType(TaskTypes.EPIC);
        taskManager.updateEpic(1, updateEpic);
        Epic epic = taskManager.getEpicByIdentifier(1);

        Assertions.assertSame(updateEpic, epic, "Задачи не совпадают!");
    }

    @Test
    void deleteEpicByIdentifier() {
        taskManager.createEpic(new Epic("testName", "testDescr"));
        taskManager.createEpic(new Epic("testName", "testDescr"));
        taskManager.createEpic(new Epic("testName", "testDescr"));
        taskManager.deleteEpicByIdentifier(1);
        List<Epic> epics = taskManager.getEpics();

        Assertions.assertEquals(2, epics.size(), "Количество задач не изменилось, задача не удалена!");
    }

    @Test
    void getSubtaskForEpic() {
        taskManager.createEpic(new Epic("testName", "testDescr"));
        Epic epic = taskManager.getEpicByIdentifier(1);
        taskManager.createSubtask(new Subtask("subName", "descr", Status.NEW, epic.getId(), "25", "18.05.25 11:05"));
        taskManager.createSubtask(new Subtask("subName", "descr", Status.IN_PROGRESS, epic.getId(), "25", "19.05.25 11:05"));
        taskManager.createSubtask(new Subtask("subName", "descr", Status.DONE, epic.getId(), "25", "20.05.25 11:05"));
        List<Subtask> subtasks = taskManager.getSubtaskForEpic(epic.getId());

        Assertions.assertEquals(3, subtasks.size(), "Не верное количество Сабтасков");
    }

    @Test
    void getSubtasks() {
        taskManager.createEpic(new Epic("testName", "testDescr"));
        taskManager.createSubtask(new Subtask("Testname", "testDeskription", Status.NEW, 1, "30", "15.10.25 14:50"));
        Subtask subtask1 = new Subtask("Testname1", "testDeskription1", Status.NEW, 1, "30", "16.10.25 15:50");
        taskManager.addSubtask(subtask1);
        List<Subtask> subtasks = taskManager.getSubtasks();

        Assertions.assertFalse(subtasks.isEmpty(), "Менеджер  не вернул задачи!");
        Assertions.assertEquals(2, subtasks.size(), "Менеджер вернул не верное количество задач!");
    }

    @Test
    void createSubtask() {
        taskManager.createEpic(new Epic("testName", "testDescr"));
        taskManager.createSubtask(new Subtask("Testname", "testDeskription", Status.NEW, 1, "30", "15.10.25 14:50"));
        List<Subtask> subtasks = taskManager.getSubtasks();

        Assertions.assertFalse(subtasks.isEmpty(), "Менеджер не добавил подзадачу!");
        Assertions.assertNotNull(subtasks.getFirst(), "Менеджер создал пустую подзадачу!");
    }

    @Test
    void deleteAllSubtasks() {
        taskManager.createEpic(new Epic("testName", "testDescr"));
        taskManager.createSubtask(new Subtask("Testname", "testDeskription", Status.NEW, 1, "30", "15.10.25 14:50"));
        taskManager.createSubtask(new Subtask("Testname", "testDeskription", Status.NEW, 1, "30", "16.10.25 14:50"));
        taskManager.createSubtask(new Subtask("Testname", "testDeskription", Status.NEW, 1, "30", "17.10.25 14:50"));
        taskManager.createSubtask(new Subtask("Testname", "testDeskription", Status.NEW, 1, "30", "18.10.25 14:50"));
        List<Subtask> subtasks = taskManager.getSubtasks();

        Assertions.assertFalse(subtasks.isEmpty(), "задачи не добавлены!");

        taskManager.deleteAllSubtasks();
        List<Subtask> subtasks1 = taskManager.getSubtasks();

        Assertions.assertFalse(!subtasks1.isEmpty(), "Не все задачи удалены!");
    }

    @Test
    void getSubtaskByIdentifier() {
        taskManager.createEpic(new Epic("testName", "testDescr"));
        taskManager.createSubtask(new Subtask("Testname", "testDeskription", Status.NEW, 1, "30", "15.10.25 14:50"));
        taskManager.createSubtask(new Subtask("Testname", "testDeskription", Status.NEW, 1, "30", "16.10.25 14:50"));
        taskManager.createSubtask(new Subtask("Testname", "testDeskription", Status.NEW, 1, "30", "17.10.25 14:50"));
        Task subtask = taskManager.getSubtasks().getFirst();
        Task subtask1 = taskManager.getSubtaskByIdentifier(2);

        Assertions.assertSame(subtask, subtask1, "Задачи не совпадают!");
    }

    @Test
    void updateSubtask() {
        taskManager.createEpic(new Epic("testName", "testDescr"));
        taskManager.createSubtask(new Subtask("Testname", "testDeskription", Status.NEW, 1, "30", "15.10.25 14:50"));
        Subtask updateSubtask = new Subtask("subName", "subDescr", Status.IN_PROGRESS, 1, "40", "16.10.25 14:00");
        updateSubtask.setTaskType(TaskTypes.SUBTASK);
        taskManager.updateSubtask(2, updateSubtask);
        Subtask subtask = taskManager.getSubtaskByIdentifier(2);

        Assertions.assertSame(updateSubtask, subtask, "Задачи не совпадают!");
    }

    @Test
    void deleteSubtaskByIdentifier() {
        taskManager.createEpic(new Epic("testName", "testDescr"));
        taskManager.createSubtask(new Subtask("Testname", "testDeskription", Status.NEW, 1, "30", "15.10.25 14:50"));
        taskManager.createSubtask(new Subtask("Testname", "testDeskription", Status.IN_PROGRESS, 1, "30", "16.10.25 14:50"));
        taskManager.createSubtask(new Subtask("Testname", "testDeskription", Status.DONE, 1, "30", "17.10.25 14:50"));
        taskManager.deleteSubtaskByIdentifier(3);
        List<Subtask> subtasks = taskManager.getSubtasks();

        Assertions.assertEquals(2, subtasks.size(), "Количество задач не изменилось, задача не удалена!");
    }

    @Test
    void getPrioritizedTasks() {
        taskManager.createEpic(new Epic("testName", "testDescr"));
        taskManager.createTask(new Task("testName", "testDescr", Status.NEW, "25", "15.05.25 11:00"));
        taskManager.createSubtask(new Subtask("Testname", "testDeskription", Status.NEW, 1, "30", "15.10.25 14:50"));
        taskManager.createTask(new Task("testName", "testDescr", Status.NEW, "25", "16.05.25 11:00"));
        taskManager.createSubtask(new Subtask("Testname", "testDeskription", Status.NEW, 1, "30", "16.10.25 14:50"));
        List<Task> prioritized = taskManager.getPrioritizedTasks();

        Assertions.assertFalse(prioritized.isEmpty(), "Список пуст, задачи не добавлены в хронологическом порядке!");
    }
}