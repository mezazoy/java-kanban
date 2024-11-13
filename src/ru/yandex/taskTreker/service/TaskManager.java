package ru.yandex.taskTreker.service;

import ru.yandex.taskTreker.model.Epic;
import ru.yandex.taskTreker.model.Subtask;
import ru.yandex.taskTreker.model.Task;

import java.util.ArrayList;
import java.util.HashMap;

 public class TaskManager {
     private final HashMap<Integer, Task> tasks = new HashMap<>();
     private final HashMap<Integer, Epic> epics = new HashMap<>();
     private final HashMap<Integer, Subtask> subtasks = new HashMap<>();
     private int id = 0;


     public ArrayList<Task> getTasks() {
         ArrayList<Task> tasksList = new ArrayList<>();
         for(Task task : tasks.values()) {
             if(task != null) {
                 tasksList.add(task);
             }
         }
         return tasksList;
     }

     public void createTask(Task task) {
         id++;
         task.setId(id);
         tasks.put(id, task);
     }

     public void deleteAllTasks() {
         tasks.clear();
     }

     public Task getTaskByIdentifier(int idNumber) {
         if (tasks.containsKey(idNumber)) {
             Task task = tasks.get(idNumber);
             return task;
         } else {
             return null;
         }
     }

     public void updateTask(int idTask, Task task) {
         task.setId(idTask);
         tasks.put(idTask, task);
     }

     public void deleteTaskByIdentifier(int idNumber) {
         if (tasks.containsKey(idNumber)) {
             tasks.remove(idNumber);
         } else {
             System.out.println("Такого идентефикатора нет!");
         }
     }


     public ArrayList<Epic> getEpics() {
         ArrayList<Epic> epicsList = new ArrayList<>();
         for(Epic epic : epics.values()) {
             if(epic != null) {
                 epicsList.add(epic);
             }
         }
         return epicsList;
     }

     public void createEpic(Epic epic) {
         id++;
         epic.setId(id);
         epics.put(epic.getId(), epic);
         updateStatusEpic(id);
     }

     public void deleteAllEpics() {
         epics.clear();
         subtasks.clear();
     }

     public Epic getEpicByIdentifier(int idNumber) {
         if (epics.containsKey(idNumber)) {
             Epic epic = epics.get(idNumber);
             return epic;
         } else {
             return null;
         }
     }

     public void updateEpic(int idEpic, Epic epic) {
         epic.setId(idEpic);
         updateStatusEpic(idEpic);
         epics.put(idEpic, epic);
     }

     public void deleteEpicByIdentifier(int idNumber) {
         if (epics.containsKey(idNumber)) {
             Epic epic = epics.get(idNumber);
             ArrayList<Integer> subtuskId = epic.getSubtasksId();
             for (int idsub : subtuskId) {
                 subtasks.remove(idsub);
             }
             epics.remove(idNumber);
         } else {
             System.out.println("Такого идентефикатора нет!");
         }
     }

     public ArrayList<Subtask> getSubtaskForEpic(int idEpic) {
         ArrayList<Subtask> subt = new ArrayList<>();
         for (Integer sub : subtasks.keySet()) {
             Subtask subtask = subtasks.get(sub);
             if (subtask.getEpicId() == idEpic) {
                 subt.add(subtask);
             }
         }
         return subt;
     }


     public ArrayList<Subtask> getSubtasks() {
         ArrayList<Subtask> subtasksList = new ArrayList<>();
         for(Subtask subtask : subtasks.values()) {
             if(subtask != null) {
                 subtasksList.add(subtask);
             }
         }
         return subtasksList;
     }

     public void createSubtask(Subtask subtask) {
         id++;
         subtask.setId(id);
         subtasks.put(id, subtask);
         for (Integer num : epics.keySet()) {
             Epic epic = epics.get(num);
             if (subtask.getEpicId() == epic.getId()) {
                 epic.addSubtaskId(id);
             }
         }
         updateStatusEpic(subtask.getEpicId());
     }

     public void deleteAllSubtasks() {
         subtasks.clear();
     }

     public Subtask getSubtaskByIdentifier(int idNumber) {
         if (subtasks.containsKey(idNumber)) {
             Subtask subtask = subtasks.get(idNumber);
             return subtask;
         } else {
             return null;
         }
     }

     public void updateSubtask(int idSubtask, Subtask subtask) {
         subtask.setId(idSubtask);
         subtasks.put(idSubtask, subtask);
         updateStatusEpic(subtask.getEpicId());
     }

     public void deleteSubtaskByIdentifier(int idNumber) {
         if (subtasks.containsKey(idNumber)) {
             subtasks.remove(idNumber);
         } else {
             System.out.println("Такого идентефикатора нет!");
         }
     }

     private void updateStatusEpic(int epicId) {
         Epic epic = epics.get(epicId);
         ArrayList<Integer> subtaskId = epic.getSubtasksId();
         int inProgress = 0;
         int isNew = 0;
         int done = 0;
         if (subtasks.isEmpty()) {
             epic.setStatusTask(Status.NEW);
         } else {
             for (int subId : subtaskId) {
                 Subtask subtask = subtasks.get(subId);
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

     public Status getStatus(int idEpic) {
         Epic epic = epics.get(idEpic);
         return epic.getStatusTask();
     }

 }
