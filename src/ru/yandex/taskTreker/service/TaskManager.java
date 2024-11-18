package ru.yandex.taskTreker.service;

import ru.yandex.taskTreker.model.Epic;
import ru.yandex.taskTreker.model.Subtask;
import ru.yandex.taskTreker.model.Task;

import java.util.ArrayList;

public interface TaskManager {

    int add(Task task);

    int add(Epic epic);

    int add(Subtask subtask);

    ArrayList<Task> getTasks();

    void createTask(Task task);

    void deleteAllTasks();

    Task getTaskByIdentifier(int idNumber);

    void updateTask(int idTask, Task task);

    void deleteTaskByIdentifier(int idNumber);

    ArrayList<Epic> getEpics();

    void createEpic(Epic epic);

    void deleteAllEpics();

    Epic getEpicByIdentifier(int idNumber);

    void updateEpic(int idEpic, Epic epic);

    void deleteEpicByIdentifier(int idNumber);

    ArrayList<Subtask> getSubtaskForEpic(int idEpic);

    ArrayList<Subtask> getSubtasks();

    void createSubtask(Subtask subtask);

    void deleteAllSubtasks();

    Subtask getSubtaskByIdentifier(int idNumber);

    void updateSubtask(int idSubtask, Subtask subtask);

    void deleteSubtaskByIdentifier(int idNumber);

    void updateStatusEpic(int epicId);

    Status getStatus(int idEpic);

}
