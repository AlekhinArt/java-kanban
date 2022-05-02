import java.util.ArrayList;
import java.util.HashMap;

public class Manager {

    int obscureID;
    HashMap <Integer, EpicTask > epicTaskHashMap = new HashMap<>();
    HashMap < Integer, SubTask> subTaskHashMap = new HashMap<>();
    HashMap < Integer, Task> taskHashMap =  new HashMap<>();
    HashMap <Integer, ArrayList<Integer>> epicAndSubId = new HashMap<>();

    void epicStatus (EpicTask obj){
        ArrayList<Integer> subs = epicAndSubId.get(obj.getTaskId());
        if (subs == null){
            obj.setTaskStatus("NEW");
            return;
        }
        for (Integer subId: subs){
            subTaskHashMap.get(subId);
            String stat =subTaskHashMap.get(subId).getTaskStatus();
            if (!stat.equals("DONE")){
                obj.setTaskStatus("IN_PROGRESS");
                return;
            } else{
                obj.setTaskStatus("DONE");
            }
        }
    }

    void saveAndCreateEpic (EpicTask o){
        epicStatus(o);
        epicTaskHashMap.put(o.getTaskId(), o);
    }

    void saveAndCreateSubTask (SubTask o){
        subTaskHashMap.put(o.getTaskId(), o);
        ArrayList<Integer> subs;
        if ( epicAndSubId.containsKey(o.getEpicId())){
            subs = epicAndSubId.get(o.getEpicId());
        }else {
            subs = new ArrayList<>();
        }
        subs.add(o.getTaskId());
        epicAndSubId.put(o.getEpicId(), subs);
        epicStatus(epicTaskHashMap.get(o.getEpicId()));

    }
    void saveAndCreateTask (Task o){
        taskHashMap.put(o.getTaskId(), o);
    }

    void updateEpic( EpicTask o, int id){
        if (epicTaskHashMap.containsKey(id)){
            epicTaskHashMap.remove(id);
            epicTaskHashMap.put(id, o);
        }
    }

    void updateSubTask( SubTask o, int id){
        if (subTaskHashMap.containsKey(id)){
            subTaskHashMap.remove(id);
            subTaskHashMap.put(id, o);
        }
    }

    void updateTask ( Task o, int id){
        if (taskHashMap.containsKey(id)){
            taskHashMap.remove(id);
            taskHashMap.put(id, o);
        }
    }

    void removeAllEpic(){
        epicTaskHashMap.clear();
    }

    void removeAllSubTask(){
        subTaskHashMap.clear();
    }

    void removeAllTask(){
        taskHashMap.clear();
    }

    void getAllEpic() {
        for (Integer id : epicTaskHashMap.keySet()){
        System.out.println("Индефикатор " + id + ". Задача : " + epicTaskHashMap.get(id));
        }
    }

    SubTask getAllSubOneEpic (int id){
        ArrayList<Integer> subs = epicAndSubId.get(id);
        for (Integer subId: subs){
            return subTaskHashMap.get(subId);
        }


        return null;
    }

    void getAllSubTask() {
        for (Integer id : subTaskHashMap.keySet()){
            System.out.println("индефикатор " + id + ". Задача : " + subTaskHashMap.get(id));
        }
    }

    void getAllTask() {
        for (Integer id : taskHashMap.keySet()){
            System.out.println("индефикатор " + id + ". Задача : " + taskHashMap.get(id));
        }
    }

    void  removeOneEpic(int id){
        if (epicTaskHashMap.containsKey(id)){
            epicTaskHashMap.remove(id);
        }
    }

    void  removeOneSubTask(int id){
        if (subTaskHashMap.containsKey(id)){
            subTaskHashMap.remove(id);
        }
    }

    void  removeOneTask(int id){
        if (taskHashMap.containsKey(id)){
            taskHashMap.remove(id);
        }
    }

    EpicTask getOneEpic (int id){
        return epicTaskHashMap.get(id);
    }

    SubTask getOneSubTask (int id){
        return subTaskHashMap.get(id);
    }

    Task getOneTask(int id){
        return taskHashMap.get(id);
    }

    public void print(){
     System.out.println(epicTaskHashMap);
     System.out.println(subTaskHashMap);
     System.out.println(taskHashMap);
    }

    public int createId(){
        return obscureID++;
    }

}
