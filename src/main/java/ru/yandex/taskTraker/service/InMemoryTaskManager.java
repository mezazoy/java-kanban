package ru.yandex.taskTraker.service;

import ru.yandex.taskTraker.model.Epic;
import ru.yandex.taskTraker.model.Subtask;
import ru.yandex.taskTraker.model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {
    private final Map<Integer, Task> tasks = new HashMap<>();
    private final Map<Integer, Task> epics = new HashMap<>();
    private final Map<Integer, Task> subtasks = new HashMap<>();
    private int id = 0;
    private final HistoryManager historyManager = Managers.getDefaultHistory();

    @Override
    public int addTask(Task task) {
        if (task.getTaskType() == null) task.setTaskType(TaskTypes.TASK);
        tasks.put(task.getId(), task);
        return task.getId();
    }

    @Override
    public int addEpic(Epic epic) {
        if (epic.getTaskType() == null) epic.setTaskType(TaskTypes.EPIC);
        epics.put(epic.getId(), epic);
        return epic.getId();
    }

    @Override
    public int addSubtask(Subtask subtask) {
        if (subtask.getTaskType() == null) subtask.setTaskType(TaskTypes.SUBTASK);
        subtasks.put(subtask.getId(), subtask);
        for (Integer num : epics.keySet()) {
            Epic epic = (Epic) epics.get(num);
            if (subtask.getEpicId() == epic.getId()) {
                epic.addSubtaskId(subtask.getId());
            }
        }
        updateStatusEpic(subtask.getEpicId());
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
        tasks.put(id, task);
    }

    @Override
    public void deleteAllTasks() {
        for (Task task : tasks.values()) {
            historyManager.remove(task.getId());
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
        tasks.put(idTask, task);
    }

    @Override
    public void deleteTaskByIdentifier(int idNumber) {
        if (tasks.containsKey(idNumber)) {
            tasks.remove(idNumber);
            historyManager.remove(idNumber);
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
    }

    @Override
    public void deleteAllEpics() {
        for (Task epic : epics.values()) {
            historyManager.remove(epic.getId());
            for (Task subtask : subtasks.values()) {
                historyManager.remove(subtask.getId());
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
        updateStatusEpic(idEpic);
        epics.put(idEpic, epic);
    }

    @Override
    public void deleteEpicByIdentifier(int idNumber) {
        if (epics.containsKey(idNumber)) {
            Epic epic = (Epic) epics.get(idNumber);
            ArrayList<Integer> subtuskId = epic.getSubtasksId();
            for (int idsub : subtuskId) {
                subtasks.remove(idsub);
            }
            epics.remove(idNumber);
            historyManager.remove(idNumber);
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
        subtasks.put(id, subtask);
        for (Integer num : epics.keySet()) {
            Epic epic = (Epic) epics.get(num);
            if (subtask.getEpicId() == epic.getId()) {
                epic.addSubtaskId(id);
            }
        }
        updateStatusEpic(subtask.getEpicId());
    }

    @Override
    public void deleteAllSubtasks() {
        for (Task subtask : subtasks.values()) {
            historyManager.remove(subtask.getId());
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
        subtasks.put(idSubtask, subtask);
        updateStatusEpic(subtask.getEpicId());
    }

    @Override
    public void deleteSubtaskByIdentifier(int idNumber) {
        if (subtasks.containsKey(idNumber)) {
            subtasks.remove(idNumber);
            historyManager.remove(idNumber);
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

    @Override
    public Status getStatus(int idEpic) {
        Epic epic = (Epic) epics.get(idEpic);
        return epic.getStatusTask();
    }

}
