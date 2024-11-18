package ru.yandex.taskTreker.model;

import ru.yandex.taskTreker.service.Status;

public class Subtask extends Task {
    private final int epicId;

    public Subtask(String taskName, String description, Status statusTask, int epicId) {
        super(taskName, description, statusTask);
            this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

}
