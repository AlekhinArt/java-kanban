/*Основной класс задач, в нем должны быть созданы все переменные
    класса епик и саба
   Эпик который состоит из саба, который состоит из тасков

    */



public class Task {
   private String taskName;
   private String taskDescription;
   private int taskId;
   private String taskStatus;

    //enum status {NEW, IN_PROGRESS, DONE };

    public Task(String taskName, String taskDescription, int taskId, String taskStatus) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskId = taskId;
        this.taskStatus = taskStatus;
    }


    public Task(String taskName, String taskDescription, int taskId) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskId = taskId;
    }

    public int getTaskId() {
        return taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskName='" + taskName + '\'' +
                ", taskDescription='" + taskDescription + '\'' +
                ", taskId=" + taskId +
                ", taskStatus='" + taskStatus + '\'' +
                '}';
    }
}
