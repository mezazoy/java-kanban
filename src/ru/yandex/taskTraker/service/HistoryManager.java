package ru.yandex.taskTraker.service;

import ru.yandex.taskTraker.model.Task;

import java.util.ArrayList;

public interface HistoryManager {

    ArrayList<Task> getHistory();

    void add(Task task);

    void remove(int id);

    Node<Task> getHead();

    Node<Task> getTail();
}
