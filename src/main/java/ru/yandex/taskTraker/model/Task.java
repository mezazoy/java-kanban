package ru.yandex.taskTraker.model;

import ru.yandex.taskTraker.service.Status;
import ru.yandex.taskTraker.service.TaskTypes;

import java.sql.SQLOutput;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;


public class Task {
    protected int id;
    protected String taskName;
    protected String description;
    private Status statusTask;
    protected TaskTypes taskType;
    protected Duration duration;
    protected LocalDateTime startTime;
    protected DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");

    public Task(String taskName, String description, Status statusTask, String duration, String startTime) {

        this.taskName = taskName;
        this.description = description;
        this.statusTask = statusTask;
        try {
            this.duration = Duration.ofHours(Integer.parseInt(duration.trim()));
        } catch(NumberFormatException ex) {
            System.out.println("Неккоректный формат числа " + ex.getMessage());
        }
        this.startTime = LocalDateTime.parse(startTime, formatter);
    }
    public Task() {

    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return startTime.plus(duration);
    }

    public TaskTypes getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskTypes taskType) {
        this.taskType = taskType;
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
        return id + "," + taskType + "," + taskName + "," + statusTask + "," + description + "," + duration.toMinutes()
        + "," + startTime.format(formatter);
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


