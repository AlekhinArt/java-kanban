import java.util.ArrayList;

public class Epic extends Task  {
    private int id;
    private ArrayList<Sub> subs = new ArrayList<>();

    public Epic(String name, String description, String status) {
        super(name, description, status);
    }

    public Epic(String name, String description, int id) {
        super(name, description, id);
    }

    public ArrayList<Sub> getSubs() {
        return subs;
    }
    public void setSubs(ArrayList<Sub> subs) {
        this.subs = subs;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }
}







