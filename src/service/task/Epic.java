package service.task;

import java.util.ArrayList;
import java.util.List;

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
    public void setType(Type type) {
        this.type = type;
    }
}







