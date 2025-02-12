package ru.yandex.taskTraker.service;

public class Managers<T extends TaskManager> {

    public static TaskManager getDefault() {

        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory() {

        return new InMemoryHistoryManager();
    }

}
