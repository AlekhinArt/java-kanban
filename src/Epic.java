import java.util.ArrayList;

public class Epic extends Task  {

    private ArrayList<SubTask> subTasks = new ArrayList<>();

    public Epic(String name, String description, String status) {
        super(name, description, status);
    }

    public Epic(String name, String description, int id) {
        super(name, description, id);
    }

    public ArrayList<SubTask> getSubs() {
        return subTasks;
    }
    public void setSubs(ArrayList<SubTask> subTasks) {
        this.subTasks = subTasks;
    }


}







