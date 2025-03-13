package ru.yandex.taskTraker.service;

import ru.yandex.taskTraker.model.Epic;
import ru.yandex.taskTraker.model.Subtask;
import ru.yandex.taskTraker.model.Task;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileBackedSyncronizedTaskTracker {
    public static FileBackedTaskManager loadFromFile(File file) {

        FileBackedTaskManager fb = new FileBackedTaskManager(file);
        try(FileReader reader = new FileReader(file); BufferedReader br = new BufferedReader(reader)) {
            while (br.ready()) {
                Task task = fb.fromString(br.readLine());
                if (task.getTaskType() == TaskTypes.TASK) {
                    fb.addTask(task);
                } else if (task.getTaskType() == TaskTypes.EPIC) {
                    fb.addEpic((Epic) task);
                } else {
                    fb.addSubtask((Subtask) task);
                }
            }
        } catch (IOException ex) {
            System.out.println("Произошла ошибка во время чтения файла");
        }
        return fb;
    }
}
