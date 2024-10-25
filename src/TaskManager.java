import java.util.HashMap;

 class TaskManager {
    HashMap<Integer, Task> tasks;
    HashMap<Integer, Epic> epics;
    HashMap<Integer, Subtask> subtasks;




     public HashMap<Integer, Task> getTasks() {
         return tasks;
     }

     public void createTask(Task task) {
         task.setId(task.hashCode());
         tasks.put(task.getId(), task);
     }

     public void deletionAllTasks() {
         tasks.clear();
     }

     public String getTaskByIdentifier(int idNumber) {
        Task task = tasks.get(idNumber);
        return task.toString();
     }

     public void updateTask(Task task) {
             tasks.put(task.getId(), task);
     }

     public void deletionTaskByIdentifier(int idNumber) {
         if(tasks.containsKey(idNumber)) {
             tasks.remove(idNumber);
         } else {
             System.out.println("Такого идентефикатора нет!");
         }
     }


     public HashMap<Integer, Epic> getEpics() {
         return epics;
     }

     public void createEpic(Epic epic) {
         epic.setId(epic.hashCode());
         tasks.put(epic.getId(), epic);
     }

     public void deletionAllEpics() {
         epics.clear();
     }

     public String getEpicByIdentifier(int idNumber) {
         Epic epic = epics.get(idNumber);
         return epic.toString();
     }

     public void updateEpic(Epic epic) {
             epics.put(epic.getId(), epic);
     }

     public void deletionEpicByIdentifier(int idNumber) {
         if(epics.containsKey(idNumber)) {
             epics.remove(idNumber);
         } else {
             System.out.println("Такого идентефикатора нет!");
         }
     }

     public void getSubtaskForEpic(Epic epic) {
         for (Integer sub : subtasks.keySet()) {
             Subtask subtask = subtasks.get(sub);
             if (subtask.getId() == epic.getId()) {
                  subtask.toString();
             }
         }
     }





     public HashMap<Integer, Subtask> getSubtasks() {
         return subtasks;
     }

     public void createSubtask(Subtask subtask) {
         subtask.setId(subtask.hashCode());
         tasks.put(subtask.getId(), subtask);
     }

     public void deletionAllSubtasks() {
         subtasks.clear();
     }

     public String getSubtaskByIdentifier(int idNumber) {
         Subtask subtask = subtasks.get(idNumber);
         return subtask.toString();
     }

     public void updateSubtask(Subtask subtask) {
             tasks.put(subtask.getId(), subtask);
     }

     public void deletionSubtaskByIdentifier(int idNumber) {
         if(subtasks.containsKey(idNumber)) {
             subtasks.remove(idNumber);
         } else {
             System.out.println("Такого идентефикатора нет!");
         }
     }

 }
