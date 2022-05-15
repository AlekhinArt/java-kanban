package service.task;

import java.util.ArrayList;

public class Epic extends Task {

    private ArrayList<Integer> subTasksId = new ArrayList<>();

    public Epic(String name, String description, Status status) {
        super(name, description, status);
    }

    public ArrayList<Integer> getSubsId() {
        return subTasksId;
    }

    public void setSubsId(ArrayList<Integer> subTasks) {
        this.subTasksId = subTasks;
    }


}







