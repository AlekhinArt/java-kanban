package service.task;

import java.time.LocalDateTime;
import java.util.Objects;

public class SubTask extends Task {
    private final int epicId;

    public SubTask(String name, String description, Status status, int epicId) {
        super(name, description, status);
        this.epicId = epicId;
        setType(Type.SUBTASK);

    }

    public SubTask(String name, String description, Status status, LocalDateTime startTime,
                   int duration, int epicId) {
        super(name, description, status, startTime, duration);
        this.epicId = epicId;
        setType(Type.SUBTASK);
    }

    public int getEpicId() {
        return epicId;
    }

    @Override
    public String toString() {
        return "service.task.Task{" +
                "taskName='" + getName() + '\'' +
                ", taskDescription='" + getDescription() + '\'' +
                ", taskId=" + getId() +
                ", taskStatus='" + getStatus() + '\'' +
                "epicId=" + epicId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SubTask subTask = (SubTask) o;
        return epicId == subTask.epicId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), epicId);
    }

}

