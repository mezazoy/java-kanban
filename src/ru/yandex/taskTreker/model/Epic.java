package ru.yandex.taskTreker.model;

import java.util.ArrayList;
public class Epic extends Task {
    private final ArrayList<Integer> subtaskId = new ArrayList<>();

    public Epic(String taskName, String description) {
        super();
        this.taskName = taskName;
        this.description = description;
    }


    public void addSubtaskId(int subtaskId) {
        this.subtaskId.add(subtaskId);
    }

    public ArrayList<Integer> getSubtasksId() {
        return subtaskId;
    }

}
