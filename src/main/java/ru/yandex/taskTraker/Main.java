package ru.yandex.taskTraker;

import ru.yandex.taskTraker.model.Epic;
import ru.yandex.taskTraker.model.Subtask;
import ru.yandex.taskTraker.model.Task;
import ru.yandex.taskTraker.service.*;
import static ru.yandex.taskTraker.service.Status.IN_PROGRESS;
import static ru.yandex.taskTraker.service.Status.NEW;

public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = Managers.getDefaultFile();

        taskManager.createTask(new Task("task1", "taskdeskr", NEW, "180", "11.04.25 15:50"));
        taskManager.createEpic(new Epic("epic1", "epicdeskr"));

        taskManager.createSubtask(new Subtask("subtask", "subdeskr", NEW, 2, "120", "11.04.25 19:30"));
        taskManager.createSubtask(new Subtask("subtask2", "subdeskr2", NEW, 2, "60", "12.04.25 16:30"));
        taskManager.createSubtask(new Subtask("subtask3", "subdeskr3", NEW, 2, "120", "13.04.25 16:30"));
        taskManager.createSubtask(new Subtask("subtask4", "subdeskr4", IN_PROGRESS, 2, "350", "12.04.25 09:30"));
        taskManager.createSubtask(new Subtask("subtask3", "subdeskr3", NEW, 2, "120", "13.04.25 23:30"));

        System.out.println(taskManager.getTasks());
        System.out.println(taskManager.getEpics());
        System.out.println(taskManager.getSubtasks());


        System.out.println();
        System.out.println();

        System.out.println(taskManager.getTaskByIdentifier(1));
        System.out.println(taskManager.getTaskByIdentifier(1));
        System.out.println(taskManager.getEpicByIdentifier(2).getSubtasksId());
        System.out.println(taskManager.getSubtaskByIdentifier(3));
        System.out.println(taskManager.getSubtaskByIdentifier(4));

        System.out.println(taskManager.getTasks());
        System.out.println(taskManager.getEpics());
        System.out.println(taskManager.getPrioritizedTasks());
     System.out.println(taskManager.getEpicByIdentifier(2).getStartTime());
     System.out.println(taskManager.getEpicByIdentifier(2).getDuration());



    }
}
