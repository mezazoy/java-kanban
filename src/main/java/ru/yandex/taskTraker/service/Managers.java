package ru.yandex.taskTraker.service;

import java.io.File;
import java.io.IOException;

public class Managers<T extends TaskManager> {

    public static TaskManager getDefault() {

        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory() {

        return new InMemoryHistoryManager();
    }

    public static FileBackedTaskManager getDefaultFileBacked() {

        try {
            File file = new File("TaskTracker.txt"); // Or use a configurable path
            if (!file.exists()) {
                file.createNewFile(); // Ensure file exists
            }
            return FileBackedSyncronizedTaskTracker.loadFromFile(new File("TaskTracker.txt"));
        } catch (IOException e) {
            System.err.println("Error initializing FileBackedTaskManager: " + e.getMessage());
            throw new RuntimeException("Failed to initialize FileBackedTaskManager", e);
        }
    }

}
