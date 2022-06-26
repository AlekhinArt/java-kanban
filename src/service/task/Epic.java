package service.task;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Epic extends Task {

    private List <Integer> subTasksId = new ArrayList<>();
    private Type type = Type.EPIC;

    public Epic(String name, String description, Status status) {
        super(name, description, status);
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
}
