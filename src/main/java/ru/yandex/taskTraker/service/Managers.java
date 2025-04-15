package ru.yandex.taskTraker.service;

import java.io.File;
import java.io.IOException;

public class Managers<T extends TaskManager> {
    public static InMemoryTaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static FileBackedTaskManager getDefaultFile() {

        try {
            File file = new File("TaskTracker.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            return FileBackedSyncronizedTaskTracker.loadFromFile(new File("TaskTracker.txt"));
        } catch (IOException e) {
            System.err.println("Error initializing FileBackedTaskManager: " + e.getMessage());
            throw new RuntimeException("Failed to initialize FileBackedTaskManager", e);
        }
    }

    public static HistoryManager getDefaultHistory() {

        return new InMemoryHistoryManager();
    }

}
