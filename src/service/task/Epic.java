package service.task;

import service.manager.Managers;

import java.time.Duration;

import java.time.LocalDateTime;
import java.util.*;

public class Epic extends Task {

    private List<Integer> subTasksId = new ArrayList<>();
    private final List<SubTask> subTasks = new LinkedList<>();
    private final Type type = Type.EPIC;
    private int duration;
    private LocalDateTime starTime;
    private LocalDateTime endTime;

    public Epic(String name, String description, Status status) {
        super(name, description, status);
    }

    public void setTime() {
        if (subTasks.isEmpty()) return;
        for (int id : subTasksId) {
            subTasks.add(Managers.getDefault().getSubs().get(id));
        }
        this.subTasks.sort(Comparator.comparing(Task::getStartTime));
        this.starTime = this.subTasks.get(0).getStartTime();
        this.endTime = this.subTasks.get(this.subTasks.size() - 1).getStartTime();
        Duration durationBetween = Duration.between(this.starTime, this.endTime);
        this.duration = durationBetween.toMinutesPart();
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

    public LocalDateTime getStarTime() {
        return starTime;
    }

    public void setStarTime(LocalDateTime starTime) {
        this.starTime = starTime;
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
