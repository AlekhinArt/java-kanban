package service.task;

import java.time.LocalDateTime;
import java.util.*;

public class Epic extends Task {

    private List<Integer> subTasksId = new ArrayList<>();
    private final List<SubTask> subTasks = new LinkedList<>();
    private LocalDateTime endTime;

    public List<SubTask> getSubTasks() {
        return subTasks;
    }

    public Epic(String name, String description, Status status) {
        super(name, description, status);
        setType(Type.EPIC);
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public List<Integer> getSubsId() {
        return subTasksId;
    }

    public void setSubsId(List<Integer> subTasks) {
        this.subTasksId = subTasks;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Epic epic = (Epic) o;
        return Objects.equals(subTasksId, epic.subTasksId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), subTasksId);
    }

    @Override
    public String toString() {
        return "Epic{" +
                "subTasksId=" + subTasksId +
                ", type=" +
                '}';
    }

}
