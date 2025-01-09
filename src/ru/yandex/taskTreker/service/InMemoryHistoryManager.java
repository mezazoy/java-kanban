package ru.yandex.taskTreker.service;

import ru.yandex.taskTreker.model.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {


    private Node<Task> head;
    private Node<Task> tail;

    public Node<Task> getHead() {
        return head;
    }

    public Node<Task> getTail() {
        return tail;
    }

    Map<Integer, Node<Task>> historyMap = new HashMap<>();

    private void lincLast(Task task) {
        final Node<Task> newNode = new Node<>(tail, task, null);
        if (tail == null) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
    }

    private ArrayList<Task> getTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        Node<Task> curNode = head;
        while (curNode != null) {
            tasks.add(curNode.data);
            curNode = curNode.next;
        }
        return tasks;
    }

    private void removeNode(int id) {
        Node<Task> node = historyMap.get(id);
        if (node == null) {
            return;
        }
        if (node.prev == null) {
            head = node.next;
            if (head == null) {
                tail = null;
            } else {
                head.prev = null;
            }
        } else if (node.next == null) {
            tail = tail.prev;
            tail.next = null;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
    }

    @Override
    public void add(Task task) {
        removeNode(task.getId());
        lincLast(task);
        historyMap.put(task.getId(), tail);

    }

    @Override
    public ArrayList<Task> getHistory() {
        return getTasks();
    }

    @Override
    public void remove(int id) {
        removeNode(id);
    }
}