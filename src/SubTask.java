

public class SubTask extends Task {
    private int epicId;

    public SubTask(String taskName, String taskDescription, int taskId, String taskStatus, int epicId) {
        super(taskName, taskDescription, taskId, taskStatus);


        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }


    @Override
    public String toString() {
        return "Task{" +
                "taskName='" + getTaskName() + '\'' +
                ", taskDescription='" + getTaskDescription() + '\'' +
                ", taskId=" + getTaskId() +
                ", taskStatus='" + getTaskStatus() + '\'' +
                "epicId=" + epicId +
                '}';
    }
}

