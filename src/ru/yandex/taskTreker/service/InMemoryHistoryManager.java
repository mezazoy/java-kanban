package ru.yandex.taskTreker.service;

import ru.yandex.taskTreker.model.Task;

import java.util.ArrayList;

public class InMemoryHistoryManager implements HistoryManager {

    private ArrayList<Task> history = new ArrayList<>();

    @Override
    public void add(Task task) {
            history.add(task);
            remove(task.getId());
    }

    @Override
    public ArrayList<Task> getHistory() {
        return new ArrayList<>(history);
    }

    @Override
    public void remove(int id) {
        for (Task task : history) {
            if (task.getId() == id) {
                history.remove(task);
            }
        }
    }
}