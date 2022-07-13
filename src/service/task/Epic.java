package service.task;

import service.manager.Managers;

import java.time.Duration;

import java.time.LocalDateTime;
import java.util.*;

public class Epic extends Task {

    private List<Integer> subTasksId = new ArrayList<>();

    public List<SubTask> getSubTasks() {
        return subTasks;
    }

    private final List<SubTask> subTasks = new LinkedList<>();
    private final Type type = Type.EPIC;
    private LocalDateTime endTime;


    public Epic(String name, String description, Status status) {
        super(name, description, status);
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
    public Type getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Epic epic = (Epic) o;
        return Objects.equals(subTasksId, epic.subTasksId) && type == epic.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), subTasksId, type);
    }

    @Override
    public String toString() {
        return "Epic{" +
                "subTasksId=" + subTasksId +
                ", type=" + type +
                '}';
    }

}
