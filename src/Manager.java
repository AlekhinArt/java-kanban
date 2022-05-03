import java.util.ArrayList;
import java.util.HashMap;

public class Manager {

    int id;
    HashMap <Integer, Epic> epicTasks = new HashMap<>();
    HashMap < Integer, Sub> subTasks = new HashMap<>();
    HashMap < Integer, Task> tasks =  new HashMap<>();


    void setEpicStatus (Epic epic){
        ArrayList< Sub > subs = epic.getSubs();
        if (subs == null){
            epic.setStatus("NEW");
            return;
        }
        for (Sub sub : subs){
            if (!sub.getStatus().equals("DONE")){
                epic.setStatus("IN_PROGRESS");
                return;
            } else{
                epic.setStatus("DONE");
            }
        }
    }

    void addEpic (Epic epic){
        epic.setId(generateId());
        setEpicStatus(epic);
        epicTasks.put(epic.getId(), epic);
    }

    void addSub (Sub sub){
        sub.setId(generateId());
        subTasks.put(sub.getId(), sub);
        Epic epic = epicTasks.get(sub.getEpicId());
        ArrayList <Sub> subsList = epic.getSubs();
        subsList.add(sub);
        epic.setSubs(subsList);
        setEpicStatus(epic);

    }

    void addTask (Task task){
        task.setId(generateId());
        tasks.put(task.getId(), task);
    }

    void updateEpic(Epic epic, int id){
        if (epicTasks.containsKey(id)){
            epicTasks.remove(id);
            epicTasks.put(id, epic);
        }
    }

    void updateSub(Sub sub, int id){
        if (subTasks.containsKey(id)){
            subTasks.remove(id);
            subTasks.put(id, sub);
        }
    }

    void updateTask ( Task task, int id){
        if (tasks.containsKey(id)){
            tasks.remove(id);
            tasks.put(id, task);
        }
    }

    void deleteAllEpics(){
        epicTasks.clear();
    }

    void deleteAllSubTasks(){
        subTasks.clear();
    }

    void deleteAllTasks(){
        tasks.clear();
    }

    void getEpics() {
        for (Integer id : epicTasks.keySet()){
        System.out.println("Индефикатор " + id + ". Задача : " + epicTasks.get(id));
        }
    }

    ArrayList<Sub> getAllSubOneEpic (int id){
        Epic epic = epicTasks.get(id);
        ArrayList<Sub> subs = epic.getSubs();
        return  subs;
    }

    void getSubs() {
        for (Integer id : subTasks.keySet()){
            System.out.println("индефикатор " + id + ". Задача : " + subTasks.get(id));
        }
    }

    void getTasks() {
        for (Integer id : tasks.keySet()){
            System.out.println("индефикатор " + id + ". Задача : " + tasks.get(id));
        }
    }

    void  deleteEpic(int id){
        if (epicTasks.containsKey(id)){
            epicTasks.remove(id);
        }
    }

    void  deleteSub(int id){
        if (subTasks.containsKey(id)){
            subTasks.remove(id);
        }
    }

    void  deleteTask(int id){
        if (tasks.containsKey(id)){
            tasks.remove(id);
        }
    }

    Epic getOneEpic (int id){
        return epicTasks.get(id);
    }

    Sub getOneSub (int id){
        return subTasks.get(id);
    }

    Task getOneTask(int id){
        return tasks.get(id);
    }

    public void print(){
     System.out.println(epicTasks);
     System.out.println(subTasks);
     System.out.println(tasks);
    }

    public int generateId(){
        return id++;
    }

}
