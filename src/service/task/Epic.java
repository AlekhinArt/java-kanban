package service.task;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {

    private List <Integer> subTasksId = new ArrayList<>();

    public Epic(String name, String description, Status status) {
        super(name, description, status);
    }

    public List<Integer> getSubsId() {
        return subTasksId;
    }

    public void setSubsId(List<Integer> subTasks) {
        this.subTasksId = subTasks;
    }


}







