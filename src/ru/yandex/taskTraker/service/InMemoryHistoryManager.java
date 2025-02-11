package ru.yandex.taskTraker.service;

import ru.yandex.taskTraker.model.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {


    private Node<Task> head = null;
    private Node<Task> tail = null;

    private Map<Integer, Node<Task>> historyMap = new HashMap<>();

    public Node<Task> getHead() {
        return head;
    }

    public Node<Task> getTail() {
        return tail;
    }

    private void linkLast(Task task) {
        if (tail == null) {
            final Node<Task> newNode = new Node<>(null, task, null);
            tail = newNode;
            head = newNode;
        } else {
            final Node<Task> newNode = new Node<>(tail, task, null);
            tail.next = newNode;
            tail = newNode;
        }
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
        if (task != null) {
            removeNode(task.getId());
            linkLast(task);
            historyMap.put(task.getId(), tail);
        }
    }


    @Override
    public ArrayList<Task> getHistory() {
        return getTasks();
    }

    @Override
    public void remove(int id) {
        removeNode(id);
        historyMap.remove(id);
    }
}