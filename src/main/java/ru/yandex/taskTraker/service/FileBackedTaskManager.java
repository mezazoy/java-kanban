package ru.yandex.taskTraker.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import ru.yandex.taskTraker.model.Epic;
import ru.yandex.taskTraker.model.Subtask;
import ru.yandex.taskTraker.model.Task;

public class FileBackedTaskManager extends InMemoryTaskManager implements TaskManager {
    private final File file;

    public FileBackedTaskManager(File file) {
        this.file = file;
    }

    public Task fromString(String value) {
        try {
            String[] split = value.split(",");

            if (split.length < 5) {
                throw new IllegalArgumentException("Неверный формат входной строки: " + value);
            }

            int id = Integer.parseInt(split[0]);
            String taskName = split[2];
            String description = split[4];
            Task task;

            switch (split[1]) {
                case "TASK":
                    if (split.length < 5) throw new IllegalArgumentException("Неверное количество полей для TASK");
                    Status status = Status.valueOf(split[3]);
                    task = new Task(taskName, description, status);
                    task.setTaskType(TaskTypes.TASK);
                    break;

                case "EPIC":
                    task = new Epic(taskName, description);
                    task.setTaskType(TaskTypes.EPIC);
                    break;

                case "SUBTASK":
                    if (split.length < 6) throw new IllegalArgumentException("Неверное количество полей для SUBTASK");
                    status = Status.valueOf(split[3]);
                    int epicId = Integer.parseInt(split[5]);
                    task = new Subtask(taskName, description, status, epicId);
                    task.setTaskType(TaskTypes.SUBTASK);
                    break;

                default:
                    throw new IllegalArgumentException("Неизвестный тип задачи: " + split[1]);
            }

            task.setId(id);
            return task;

        } catch (Exception e) {
            System.err.println("Ошибка анализа задачи: " + e.getMessage());
            throw e;
        }
    }

    private void save() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            saveTasks(super.getTasks(), bw);
            saveTasks(super.getEpics(), bw);
            saveTasks(super.getSubtasks(), bw);
        } catch (IOException ex) {
            throw new ManagerSaveException("Failed to save tasks to file: " + file.getAbsolutePath(), ex);
        }
    }

    private <T extends Task> void saveTasks(Iterable<T> tasks, BufferedWriter bw) throws IOException {
        for (T task : tasks) {
            bw.write(task.toString());
            bw.write(System.lineSeparator());
        }
    }

    @Override
    public int addTask(Task task) {
        super.addTask(task);
        save();
        return task.getId();
    }

    @Override
    public int addEpic(Epic epic) {
        super.addEpic(epic);
        save();
        return epic.getId();
    }

    @Override
    public int addSubtask(Subtask subtask) {
        super.addSubtask(subtask);
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
