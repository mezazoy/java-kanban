package ru.yandex.taskTraker.service;

import java.time.Duration;
import java.util.*;
import ru.yandex.taskTraker.model.Epic;
import ru.yandex.taskTraker.model.Subtask;
import ru.yandex.taskTraker.model.Task;

public class InMemoryTaskManager implements TaskManager {
    private final Map<Integer, Task> tasks = new HashMap<>();
    private final Map<Integer, Task> epics = new HashMap<>();
    private final Map<Integer, Task> subtasks = new HashMap<>();
    private final TreeSet<Task> prioritizedTasks = new TreeSet<>(Comparator.comparing(Task::getStartTime));
    private int id = 0;
    private final HistoryManager historyManager = Managers.getDefaultHistory();


    @Override
    public int addTask(Task task) {
        if (task.getTaskType() == null) task.setTaskType(TaskTypes.TASK);
        try {
            if (intersectionCheck(task)) {
                tasks.put(task.getId(), task);
                if (task.getStartTime() != null) prioritizedTasks.add(task);
            }
        } catch (IntersectionTime ex) {
            System.out.println("Задача не модет быть добавлена, есть пересечения по времени с другими задачами!");
        }
        return task.getId();
    }

    @Override
    public int addEpic(Epic epic) {
        if (epic.getTaskType() == null) epic.setTaskType(TaskTypes.EPIC);
        epics.put(epic.getId(), epic);
        updateEpicStartTime(epic.getId());
        if (epic.getStartTime() != null) prioritizedTasks.add(epic);
        return epic.getId();
    }

    @Override
    public int addSubtask(Subtask subtask) {
        if (subtask.getTaskType() == null) subtask.setTaskType(TaskTypes.SUBTASK);
        try {
            if (intersectionCheck(subtask)) {
                subtasks.put(subtask.getId(), subtask);
                if (subtask.getStartTime() != null) prioritizedTasks.add(subtask);
                for (Integer num : epics.keySet()) {
                    Epic epic = (Epic) epics.get(num);
                    if (subtask.getEpicId() == epic.getId()) {
                        epic.addSubtaskId(subtask.getId());
                    }
                }
                updateStatusEpic(subtask.getEpicId());
                updateEpicStartTime(subtask.getEpicId());
            }
        } catch (IntersectionTime ex) {
            System.out.println("Задача не модет быть добавлена, есть пересечения по времени с другими задачами!");
        }
        return subtask.getId();
    }

    @Override
    public ArrayList<Task> getTasks() {
        ArrayList<Task> tasksList = new ArrayList<>();
        for (Task task : tasks.values()) {
            if (task != null) {
                tasksList.add(task);
            }
        }
        return tasksList;
    }

    @Override
    public void createTask(Task task) {
        id++;
        task.setId(id);
        task.setTaskType(TaskTypes.TASK);
        if (intersectionCheck(task)) {
            tasks.put(id, task);
            if (task.getStartTime() != null) prioritizedTasks.add(task);
        } else {
            System.out.println("Задача не модет быть добавлена, есть пересечения по времени с другими задачами!");
        }
    }

    @Override
    public void deleteAllTasks() {
        for (Task task : tasks.values()) {
            historyManager.remove(task.getId());
            prioritizedTasks.remove(task);
        }
        tasks.clear();
    }

    @Override
    public Task getTaskByIdentifier(int idNumber) {
        Task task = tasks.get(idNumber);
        if (task != null) {
            historyManager.add(task);
            return task;
        } else {
            return null;
        }
    }

    @Override
    public void updateTask(int idTask, Task task) {
        task.setId(idTask);
        try {
            if (intersectionCheck(task)) {
                prioritizedTasks.remove(tasks.get(idTask));
                tasks.put(idTask, task);
                if (task.getStartTime() != null) prioritizedTasks.add(task);
            }
        } catch (IntersectionTime ex) {
            System.out.println("Задача не модет быть обновлена, есть пересечения по времени с другими задачами!"
                    + ex.getMessage());
        }
    }

    @Override
    public void deleteTaskByIdentifier(int idNumber) {
        if (tasks.containsKey(idNumber)) {
            tasks.remove(idNumber);
            historyManager.remove(idNumber);
            prioritizedTasks.remove(tasks.get(idNumber));
        } else {
            System.out.println("Такого идентефикатора нет!");
        }
    }


    @Override
    public ArrayList<Epic> getEpics() {
        ArrayList<Epic> epicsList = new ArrayList<>();
        for (Task epic : epics.values()) {
            if (epic != null) {
                epicsList.add((Epic) epic);
            }
        }
        return epicsList;
    }

    @Override
    public void createEpic(Epic epic) {
        id++;
        epic.setId(id);
        epic.setTaskType(TaskTypes.EPIC);
        epics.put(epic.getId(), epic);
        updateStatusEpic(id);
        updateEpicStartTime(id);
    }

    @Override
    public void deleteAllEpics() {
        for (Task epic : epics.values()) {
            historyManager.remove(epic.getId());
            prioritizedTasks.remove(epic);
            for (Task subtask : subtasks.values()) {
                historyManager.remove(subtask.getId());
                prioritizedTasks.remove(subtask);
            }
        }
        epics.clear();
        subtasks.clear();
    }

    @Override
    public Epic getEpicByIdentifier(int idNumber) {
        Epic epic = (Epic) epics.get(idNumber);
        if (epic != null) {
            historyManager.add(epic);
            return epic;
        } else {
            return null;
        }
    }

    @Override
    public void updateEpic(int idEpic, Epic epic) {
        epic.setId(idEpic);
        try {
            if (intersectionCheck(epic)) {
                prioritizedTasks.remove(epics.get(idEpic));
                updateStatusEpic(idEpic);
                updateEpicStartTime(idEpic);
                epics.put(idEpic, epic);
                if (epic.getStartTime() != null) prioritizedTasks.add(epic);
            }
        } catch (IntersectionTime ex) {
            System.out.println("Задача не модет быть обновлена, есть пересечения по времени с другими задачами!");
        }
    }

    @Override
    public void deleteEpicByIdentifier(int idNumber) {
        if (epics.containsKey(idNumber)) {
            Epic epic = (Epic) epics.get(idNumber);
            ArrayList<Integer> subtuskId = epic.getSubtasksId();
            for (int idsub : subtuskId) {
                subtasks.remove(idsub);
                prioritizedTasks.remove(subtasks.get(idsub));
            }
            epics.remove(idNumber);
            historyManager.remove(idNumber);
            prioritizedTasks.remove(epics.get(idNumber));
        } else {
            System.out.println("Такого идентефикатора нет!");
        }
    }

    @Override
    public ArrayList<Subtask> getSubtaskForEpic(int idEpic) {
        ArrayList<Subtask> subt = new ArrayList<>();
        for (Integer sub : subtasks.keySet()) {
            Subtask subtask = (Subtask) subtasks.get(sub);
            if (subtask.getEpicId() == idEpic) {
                subt.add(subtask);
            }
        }
        return subt;
    }


    @Override
    public ArrayList<Subtask> getSubtasks() {
        ArrayList<Subtask> subtasksList = new ArrayList<>();
        for (Task subtask : subtasks.values()) {
            if (subtask != null) {
                subtasksList.add((Subtask) subtask);
            }
        }
        return subtasksList;
    }

    @Override
    public void createSubtask(Subtask subtask) {
        id++;
        subtask.setId(id);
        subtask.setTaskType(TaskTypes.SUBTASK);
        try {
            if (intersectionCheck(subtask)) {
                subtasks.put(id, subtask);
                prioritizedTasks.add(subtask);
                for (Integer num : epics.keySet()) {
                    Epic epic = (Epic) epics.get(num);
                    if (subtask.getEpicId() == epic.getId()) {
                        epic.addSubtaskId(id);
                    }
                }
                updateStatusEpic(subtask.getEpicId());
                updateEpicStartTime(subtask.getEpicId());
                prioritizedTasks.remove(epics.get(subtask.getEpicId()));
                prioritizedTasks.add(epics.get(subtask.getEpicId()));
            }
        } catch (IntersectionTime ex) {
            System.out.println("Задача не модет быть добавлена, есть пересечения по времени с другими задачами!");
        }
    }

    @Override
    public void deleteAllSubtasks() {
        for (Task subtask : subtasks.values()) {
            historyManager.remove(subtask.getId());
            prioritizedTasks.remove(subtask);
        }
        subtasks.clear();
    }

    @Override
    public Subtask getSubtaskByIdentifier(int idNumber) {
        Subtask subtask = (Subtask) subtasks.get(idNumber);
        if (subtask != null) {
            historyManager.add(subtask);
            return subtask;
        } else {
            return null;
        }
    }

    @Override
    public void updateSubtask(int idSubtask, Subtask subtask) {
        subtask.setId(idSubtask);
        try {
            if (intersectionCheck(subtask)) {
                prioritizedTasks.remove(subtasks.get(idSubtask));
                subtasks.put(idSubtask, subtask);
                updateStatusEpic(subtask.getEpicId());
                updateEpicStartTime(subtask.getEpicId());
                prioritizedTasks.remove(epics.get(subtask.getEpicId()));
                prioritizedTasks.add(epics.get(subtask.getEpicId()));
                prioritizedTasks.add(subtask);
            }
        } catch (IntersectionTime ex) {
            System.out.println("Задача не модет быть добавлена, есть пересечения по времени с другими задачами!");
        }
    }

    @Override
    public void deleteSubtaskByIdentifier(int idNumber) {
        if (subtasks.containsKey(idNumber)) {
            subtasks.remove(idNumber);
            historyManager.remove(idNumber);
            prioritizedTasks.remove(subtasks.get(idNumber));
        } else {
            System.out.println("Такого идентефикатора нет!");
        }
    }

    @Override
    public void updateStatusEpic(int epicId) {
        Epic epic = (Epic) epics.get(epicId);
        ArrayList<Integer> subtaskId = epic.getSubtasksId();
        int inProgress = 0;
        int isNew = 0;
        int done = 0;
        if (subtasks.isEmpty()) {
            epic.setStatusTask(Status.NEW);
        } else {
            for (int subId : subtaskId) {
                Subtask subtask = (Subtask) subtasks.get(subId);
                if (subtask.getStatusTask() == Status.IN_PROGRESS) {
                    inProgress++;
                } else if (subtask.getStatusTask() == Status.DONE) {
                    done++;
                } else if (subtask.getStatusTask() == Status.NEW) {
                    isNew++;
                }

            }
            if (inProgress > 0 || done > 0 && isNew > 0) {
                epic.setStatusTask(Status.IN_PROGRESS);
            } else if (isNew == 0 && inProgress == 0) {
                epic.setStatusTask(Status.DONE);
            } else if (inProgress == 0 && done == 0) {
                epic.setStatusTask(Status.NEW);
            }

        }

    }

    public void updateEpicStartTime(int epicId) {
        Epic epic = (Epic) epics.get(epicId);
        List<Integer> subtaskId = epic.getSubtasksId();
        if (!subtaskId.isEmpty()) {
            Duration duration = Duration.ofHours(0);
            TreeSet<Subtask> subtaskSort = new TreeSet<>(Comparator.comparing(Subtask::getStartTime));

            for (int idSub : subtaskId) {
                subtaskSort.add((Subtask) subtasks.get(idSub));
                duration = duration.plus(subtasks.get(idSub).getDuration());
            }

            epic.setStartTime(subtaskSort.first().getStartTime());
            epic.setDuration(duration);
            epic.setEndTime(subtaskSort.last().getEndTime());
        }
    }

    public ArrayList<Task> getPrioritizedTasks() {
        return new ArrayList<>(prioritizedTasks);
    }

    @Override
    public Status getStatus(int idEpic) {
        Epic epic = (Epic) epics.get(idEpic);
        return epic.getStatusTask();
    }

    public boolean intersectionCheck(Task t1) {
        for (Task task : getPrioritizedTasks()) {
            if (task.getStartTime().isBefore(t1.getEndTime()) && task.getEndTime().isAfter(t1.getStartTime()))
                return false;
        }
        return true;
    }
}
