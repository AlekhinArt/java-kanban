package service.task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Task {
    private final String name;
    private String description;
    private int id;
    private Status status;
    private Type type = Type.TASK;
    private int duration;
    private LocalDateTime startTime;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    public Task(String name, String description, Status status) {
        this.name = name;
        this.description = description;
        this.status = status;
    }


    public Task(String name, String description, Status status, LocalDateTime startTime, int duration) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.startTime = startTime;
        this.duration = duration;
    }


    public LocalDateTime getEndTime() {
        return startTime.plus(getDurationInFormat(this.duration));
    }

    public Duration getDurationInFormat(int duration) {
        return Duration.ofMinutes(duration);
    }

    public LocalDateTime toFormatTime(String time) {
        return LocalDateTime.parse(time, formatter);
    }

    public String toStringTime(LocalDateTime time) {
        return time.format(formatter);
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

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public int getDuration() {
        return duration;
    }


    public void setDuration(int duration) {
        this.duration = duration;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
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

}
