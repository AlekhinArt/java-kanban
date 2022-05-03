/*2.
        Думаю необходимости в переопределение hashcode and equals на данный момент нет
        все паремтры предполагаемые к сравниванию, уже находятся в супер классе */

public class Sub extends Task {
    private int epicId;

    public Sub(String name, String description, String status, int epicId) {
        super(name, description, status);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskName='" + getTask() + '\'' +
                ", taskDescription='" + getDescription() + '\'' +
                ", taskId=" + getId() +
                ", taskStatus='" + getStatus() + '\'' +
                "epicId=" + epicId +
                '}';
    }
}

