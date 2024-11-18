package ru.yandex.taskTreker.service;

import ru.yandex.taskTreker.model.Task;

import java.util.ArrayList;

public class InMemoryHistoryManager implements HistoryManager {

    ArrayList<Task> history = new ArrayList<>(10);

    @Override
    public void add(Task task) {
        if (history.size() == 10) {
            history.remove(0);
            history.add(task);
        } else {
            history.add(task);
        }
    }

    @Override
    public ArrayList<Task> getHistory() {
        return history;
    }
}