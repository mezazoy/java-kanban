package ru.yandex.taskTreker.model;

import ru.yandex.taskTreker.service.Status;

import java.util.Objects;

public class Task {
    protected int id;
    protected String taskName;
    protected String description;
    private Status statusTask;

    public Task(String taskName, String description, Status statusTask) {

        this.taskName = taskName;
        this.description = description;
        this.statusTask = statusTask;
    }

    public Task() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean equals(Object task) {
        if (this == task) return true;
        if (task == null) return false;
        if (this.getClass() != task.getClass()) return false;
        Task otherTask = (Task) task;

        return (id == otherTask.id) && Objects.equals(taskName, otherTask.taskName) &&
                Objects.equals(description, otherTask.description) && Objects.equals(statusTask, otherTask.statusTask);
    }

@Override
    public int hashCode() {
        int hash = 17;
        if (taskName != null) {
            hash = hash + taskName.hashCode();
        }
        hash = hash * 31;

        if (description != null) {
            hash = hash + description.hashCode();
        }
        hash = hash * 31;

        if (statusTask != null) {
            hash = hash + statusTask.hashCode();
        }

        return hash;
    }

    @Override
    public String toString() {
        return " id = " + id + " taskName - " + taskName + " description - " + description + " statusTask - "
                + statusTask;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatusTask(Status statusTask) {
        this.statusTask = statusTask;
    }

    public Status getStatusTask() {
        return statusTask;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getDescription() {
        return description;
    }
}


