package ru.yandex.taskTraker.model;

import ru.yandex.taskTraker.service.Status;

public class Subtask extends Task {
    private final int epicId;

    public Subtask(String taskName, String description, Status statusTask, int epicId, String duration, String startTime) {
        super(taskName, description, statusTask, duration, startTime);
            this.epicId = epicId;
    }

    @Override
    public int getEpicId() {
        return epicId;
    }

    @Override
    public String toString() {
        return super.toString() + "," + epicId;
    }

}
