package ru.yandex.taskTraker.model;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {

    private final ArrayList<Integer> subtaskId = new ArrayList<>();

    public Epic(String taskName, String description) {
        super();
        this.taskName = taskName;
        this.description = description;
    }


    public void addSubtaskId(int id) {
        if (id > this.id) subtaskId.add(id);
    }

    public ArrayList<Integer> getSubtasksId() {
        return new ArrayList<>(subtaskId);
    }
}


