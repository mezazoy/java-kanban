import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import ru.yandex.taskTraker.service.FileBackedTaskManager;
import static org.junit.jupiter.api.Assertions.*;

import ru.yandex.taskTraker.model.Subtask;
import ru.yandex.taskTraker.model.Task;
import ru.yandex.taskTraker.service.Status;
import ru.yandex.taskTraker.service.TaskTypes;
import java.io.File;

class FileBackedTaskManagerTest {

    private FileBackedTaskManager filedManager;

    @BeforeEach
    public void setUp() {
        File tempFile = new File("testfile.txt");
        filedManager = new FileBackedTaskManager(tempFile);
    }

    @Test
    public void fromStringToTask() {
        String input = "1,TASK,TaskName,IN_PROGRESS,Description,15,10.10.25 10:00";
        Task task = filedManager.fromString(input);

        assertNotNull(task);
        assertEquals(1, task.getId());
        assertEquals("TaskName", task.getTaskName());
        assertEquals("Description", task.getDescription());
        assertEquals(Status.IN_PROGRESS, ((Task)task).getStatusTask());
        assertEquals(TaskTypes.TASK, task.getTaskType());
    }

    @Test
    public void fromStringToEpic() {
        String input = "2,EPIC,epic1,NEW,epicdeskr";
        Task epic = filedManager.fromString(input);

        assertNotNull(epic);
        assertEquals(2, epic.getId());
        assertEquals(TaskTypes.EPIC, epic.getTaskType());
    }

    @Test
    public void fromStringToSubtask() {
        String input = "3,SUBTASK,SubtaskName,DONE,Description,20,12.10.25 12:00,5";
        Task subtask = filedManager.fromString(input);

        assertNotNull(subtask);
        assertEquals(3, subtask.getId());
        assertEquals("SubtaskName", subtask.getTaskName());
        assertEquals(Status.DONE, ((Subtask)subtask).getStatusTask());
        assertEquals(5, ((Subtask)subtask).getEpicId());
        assertEquals(TaskTypes.SUBTASK, subtask.getTaskType());
    }

    @Test
    public void invalidFormat() {
        String input = "1,TASK";
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            filedManager.fromString(input);
        });
        assertTrue(exception.getMessage().contains("Неверный формат входной строки"));
    }

    @Test
    public void UnknownType() {
        String input = "4,UNKNOWN,epic1,NEW,epicdeskr";
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            filedManager.fromString(input);
        });
        assertTrue(exception.getMessage().contains("Неизвестный тип задачи"));
    }
}