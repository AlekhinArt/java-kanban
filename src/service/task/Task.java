package service.task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Task {
    private String name;
    private String description;
    private int id;
    private Status status;
    private Type type = Type.TASK;
    private int duration;
    private String startTime;


    public Task(String name, String description, Status status) {
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public boolean checkTime() {
        return (startTime != null);
    }

    public LocalDateTime getFormatEndTime() {
        return getStartTimeInLocal().plus(getDuration(this.duration));
    }

    public String getEndTime() {
        LocalDateTime time = getStartTimeInLocal().plus(getDuration(this.duration));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

        return time.format(formatter);
    }

    public LocalDateTime formatTime(String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        return LocalDateTime.parse(time, formatter);
    }

    public LocalDateTime getStartTimeInLocal() {
        return formatTime(getStarTime());
    }

    public Duration getDuration(int duration) {
        return Duration.ofMinutes(duration);

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "service.task.Task{" +
                "taskName='" + name + '\'' +
                ", taskDescription='" + description + '\'' +
                ", Id=" + id +
                ", taskStatus='" + status + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task1 = (Task) o;
        return id == task1.id && Objects.equals(name, task1.name) && Objects.equals(description, task1.description) && Objects.equals(status, task1.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, id, status);
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public int getDuration() {
        return duration;
    }

    public String getStarTime() {
        return startTime;
    }

    public void setEndTime(String endTime) {
    }
}
