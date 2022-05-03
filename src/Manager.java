import java.util.ArrayList;
import java.util.HashMap;

public class Manager {

    int id;
    HashMap <Integer, Epic> epics = new HashMap<>();
    HashMap < Integer, SubTask> subs = new HashMap<>(); // тогда правильнее и эту переменную переименовать
    HashMap < Integer, Task> tasks =  new HashMap<>();

    void setEpicStatus(Epic epic){
        ArrayList<SubTask> subTasks = epic.getSubs();
        if (subTasks == null){
            epic.setStatus("NEW");
            return;
        }
        for (SubTask subTask : subTasks){
            if (!subTask.getStatus().equals("DONE")){
                epic.setStatus("IN_PROGRESS");
                return;
            } else{
                epic.setStatus("DONE");
            }
        }
    }

    void addEpic(Epic epic){
        epic.setId(generateId());
        setEpicStatus(epic);
        epics.put(epic.getId(), epic);
    }

    void addSub (SubTask subTask){
        subTask.setId(generateId());
        subs.put(subTask.getId(), subTask);
        Epic epic = epics.get(subTask.getEpicId());
        ArrayList <SubTask> subsList = epic.getSubs();
        subsList.add(subTask);
        epic.setSubs(subsList);
        setEpicStatus(epic);
    }

    void addTask(Task task){
        task.setId(generateId());
        tasks.put(task.getId(), task);
    }

    void updateEpic(Epic epic, int id){
        if (epics.containsKey(id)){
            epics.remove(id);
            epics.put(id, epic);
        }
    }

    void updateSub(SubTask subTask, int id){
        if (subs.containsKey(id)){
            subs.remove(id);
            subs.put(id, subTask);
        }
    }

    void updateTask(Task task, int id){
        if (tasks.containsKey(id)){
            tasks.remove(id);
            tasks.put(id, task);
        }
    }

    void deleteAllEpics(){
        epics.clear();
    }

    void deleteAllSubTasks(){
        subs.clear();
    }

    void deleteAllTasks(){
        tasks.clear();
    }
    /*9
    Вообще по заданию, получение списка всех задач и я думал вопрос будет к этому
    поэтому позволь, я оставлю имя, но изменю сами методы)
    Возможно так будет еще и правильнее)*/

    ArrayList<Epic> getEpics() {
        ArrayList<Epic> epicsList = new ArrayList<>();
        for (Integer id : epics.keySet()){
        epicsList.add(epics.get(id));
        }
        return epicsList;
    }
    // 10. По мне читалось как получить все сабы одного эпика вполне логично, но твой вариант тоже хорош))
    ArrayList<SubTask> getEpicSubtasks(int id){
        Epic epic = epics.get(id);
        ArrayList<SubTask> subTasks = epic.getSubs();
        return subTasks;
    }

    ArrayList<SubTask> getSubs() {
        ArrayList<SubTask> subsList = new ArrayList<>();
        for (Integer id : subs.keySet()){
            subsList.add(subs.get(id));
        }
        return subsList;
    }

    ArrayList<Task> getTasks() {
        ArrayList<Task> tasksList = new ArrayList<>();
        for (Integer id : tasks.keySet()){
            tasksList.add(tasks.get(id));
        }
        return tasksList;
    }

    void  deleteEpic(int id){
        if (epics.containsKey(id)){
            epics.remove(id);
        }
    }

    void  deleteSub(int id){
        if (subs.containsKey(id)){
            subs.remove(id);
        }
    }

    void  deleteTask(int id){
        if (tasks.containsKey(id)){
            tasks.remove(id);
        }
    }

    Epic getOneEpic (int id){
        return epics.get(id);
    }

    SubTask getOneSub (int id){
        return subs.get(id);
    }

    Task getOneTask(int id){
        return tasks.get(id);
    }

    public void print(){
     System.out.println(epics);
     System.out.println(subs);
     System.out.println(tasks);
    }

    public int generateId(){
        return id++;
    }

}
