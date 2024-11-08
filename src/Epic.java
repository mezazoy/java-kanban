import java.util.ArrayList;
public class Epic extends Task{
    private final ArrayList<Integer> subtaskId = new ArrayList<>();

    public Epic(String taskName, String description, Status statusTask) {
        super(taskName, description, statusTask);
    }


    public void setSubtaskId(int subtaskId) {
        this.subtaskId.add(subtaskId);
    }

    public ArrayList<Integer> getSubtaskId() {
        return subtaskId;
    }

}
