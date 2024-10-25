import java.util.ArrayList;

public class Subtask extends Epic{
    private int epicId;

    public Subtask(String taskName, String description, Status statusTask, int epicId) {
        super(taskName, description, statusTask);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    /*@Override
    public boolean equals(Object task) {
        Subtask otherTask = (Subtask) task;
        return (epicId == otherTask.epicId) && super.equals(task);
    }*/
}
