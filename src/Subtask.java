import java.util.ArrayList;

public class Subtask extends Epic{
    private final int epicId;

    public Subtask(String taskName, String description, Status statusTask, int epicId) {
        super(taskName, description, statusTask);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

}
