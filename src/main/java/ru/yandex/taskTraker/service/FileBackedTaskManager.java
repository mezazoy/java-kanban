package ru.yandex.taskTraker.service;

import ru.yandex.taskTraker.model.Epic;
import ru.yandex.taskTraker.model.Subtask;
import ru.yandex.taskTraker.model.Task;

import static java.lang.Integer.parseInt;

public class FileBackedTaskManager extends InMemoryTaskManager implements TaskManager {

    private String toString(Task task) {
        return task.toString();
    }

    private Task fromString(String value) {
        String[] split = value.split(",");
        if(split[1].equals("TASK")) {
            Task task = new Task(split[2], split[4], Status.valueOf(split[3]));
            task.setId(parseInt(split[0]));
            task.setTaskType(TaskTypes.TASK);
            return task;
        } else if(split[1].equals("EPIC")) {
            Task epic = new Epic(split[2], split[4]);
            epic.setId(parseInt(split[0]));
            epic.setTaskType(TaskTypes.EPIC);
            return epic;
        } else {
            Task subtask = new Subtask(split[2], split[4], Status.valueOf(split[3]), parseInt(split[5]));
            subtask.setId(parseInt(split[0]));
            subtask.setTaskType(TaskTypes.SUBTASK);
            return subtask;
        }
    }

    private void save() {

    }

    @Override
    public int add(Task task) {
        super.add(task);
        save();
        return task.getId();
    }

    @Override
    public int add(Epic epic) {
        super.add(epic);
        save();
        return epic.getId();
    }

    @Override
    public int add(Subtask subtask) {
        super.add(subtask);
        save();
        return subtask.getId();
    }

    @Override
    public void createTask(Task task) {
        super.createTask(task);
        save();
    }

    @Override
    public void updateTask(int idTask, Task task) {
        super.updateTask(idTask, task);
        save();
    }

    @Override
    public void createEpic(Epic epic) {
        super.createEpic(epic);
        save();
    }

    @Override
    public void updateEpic(int idEpic, Epic epic) {
        super.updateEpic(idEpic, epic);
        save();
    }

    @Override
    public void createSubtask(Subtask subtask) {
        super.createSubtask(subtask);
        save();
    }

    @Override
    public void updateSubtask(int idSubtask, Subtask subtask) {
        super.updateSubtask(idSubtask, subtask);
        save();
    }
}
