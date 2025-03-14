package ru.yandex.taskTraker;

import ru.yandex.taskTraker.model.Epic;
import ru.yandex.taskTraker.model.Subtask;
import ru.yandex.taskTraker.model.Task;
import ru.yandex.taskTraker.service.*;
import static ru.yandex.taskTraker.service.Status.IN_PROGRESS;
import static ru.yandex.taskTraker.service.Status.NEW;

public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = Managers.getDefault();

        taskManager.createTask(new Task("task1", "taskdeskr", NEW));
        taskManager.createEpic(new Epic("epic1", "epicdeskr"));

        taskManager.createSubtask(new Subtask("subtask", "subdeskr", NEW, 2));
        taskManager.createSubtask(new Subtask("subtask2", "subdeskr2", NEW, 2));
        taskManager.createSubtask(new Subtask("subtask3", "subdeskr3", NEW, 2));
        taskManager.createSubtask(new Subtask("subtask4", "subdeskr4", IN_PROGRESS, 2));
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



    }
}
