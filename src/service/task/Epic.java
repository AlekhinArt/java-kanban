package service.task;

import service.manager.Managers;

import java.time.Duration;

import java.util.*;

public class Epic extends Task {


    private List<Integer> subTasksId = new ArrayList<>();
    private final List<SubTask> subTasks = new LinkedList<>();
    private final Type type = Type.EPIC;
    private final int duration = 0;
    private final String starTime = "";
    private String endTime = "";


    public Epic(String name, String description, Status status) {
        super(name, description, status);
    }

    public void setTime() {
        if (subTasks.isEmpty()) return;
        for (int id : subTasksId) {
            subTasks.add(Managers.getDefault().getSubs().get(id));
        }
        this.subTasks.sort(Comparator.comparing(Task::getStartTimeInLocal));
        setStartTime(this.subTasks.get(0).getStarTime());
        setEndTime(this.subTasks.get(this.subTasks.size() - 1).getStarTime());
        Duration duration = Duration.between(formatTime(getStarTime()), formatTime(getEndTime()));
        setDuration(duration.toMinutesPart());
    }

    public String getEndTime() {
        return endTime;
    }

    public List<Integer> getSubsId() {
        return subTasksId;
    }

    public void setSubsId(List<Integer> subTasks) {
        this.subTasksId = subTasks;
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

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
