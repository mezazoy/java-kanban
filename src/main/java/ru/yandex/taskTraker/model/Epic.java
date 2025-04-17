package ru.yandex.taskTraker.model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Epic extends Task {

    private final ArrayList<Integer> subtaskId = new ArrayList<>();
    private LocalDateTime endTime;

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

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public LocalDateTime getEndTime() {
        return endTime;
    }

    @Override
    public String toString() {
        return id + "," + taskType + "," + taskName + "," + statusTask + "," + description;
    }
}


