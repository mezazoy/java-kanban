package ru.yandex.taskTreker.service;

import ru.yandex.taskTreker.model.Task;

import java.util.ArrayList;

public interface HistoryManager {

    ArrayList<Task> getHistory();

    void add(Task task);
}
