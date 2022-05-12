package service.manager;

import service.Managers;
import service.task.*;

import java.util.ArrayList;
import java.util.HashMap;

public class InMemoryTaskManager implements TaskManager {
    int id;
    HashMap <Integer, Epic> epics = new HashMap<>();
    HashMap < Integer, SubTask> subs = new HashMap<>();
    HashMap < Integer, Task> tasks =  new HashMap<>();

    @Override
    public void setEpicStatus(Epic epic){
        ArrayList<SubTask> subTasks = epic.getSubs();
        if (subTasks == null){
            epic.setStatus(Status.NEW);
            return;
        }
        for (SubTask subTask : subTasks){
            if (!subTask.getStatus().equals(Status.DONE)){
                epic.setStatus(Status.IN_PROGRESS);
                return;
            } else{
                epic.setStatus(Status.DONE);
            }
        }
    }

    @Override
    public void addEpic(Epic epic){
        epic.setId(generateId());
        setEpicStatus(epic);
        epics.put(epic.getId(), epic);
    }

    @Override
    public void addSub (SubTask subTask){
        subTask.setId(generateId());
        subs.put(subTask.getId(), subTask);
        Epic epic = epics.get(subTask.getEpicId());
        ArrayList <SubTask> subsList = epic.getSubs();
        subsList.add(subTask);
        epic.setSubs(subsList);
        setEpicStatus(epic);
    }

    @Override
    public void addTask(Task task){
        task.setId(generateId());
        tasks.put(task.getId(), task);
    }

    @Override
    public void updateEpic(Epic epic, int id){
        if (epics.containsKey(id)){
            epics.remove(id);
            epics.put(id, epic);
        }
    }

    @Override
    public void updateSub(SubTask subTask, int id){
        if (subs.containsKey(id)){
            subs.remove(id);
            subs.put(id, subTask);
        }
    }

    @Override
    public void updateTask(Task task, int id){
        if (tasks.containsKey(id)){
            tasks.remove(id);
            tasks.put(id, task);
        }
    }

    @Override
    public void deleteAllEpics(){
        epics.clear();
    }

    @Override
    public void deleteAllSubTasks(){
        subs.clear();
    }

    @Override
    public void deleteAllTasks(){
        tasks.clear();
    }

    @Override
    public ArrayList<Epic> getEpics() {
        ArrayList<Epic> epicsList = new ArrayList<>();
        for (Integer id : epics.keySet()){
        epicsList.add(epics.get(id));
        }
        return epicsList;
    }

    @Override
    public ArrayList<SubTask> getEpicSubtasks(int id){
        Epic epic = epics.get(id);
        return epic.getSubs();
    }

    @Override
    public ArrayList<SubTask> getSubs() {
        ArrayList<SubTask> subsList = new ArrayList<>();
        for (Integer id : subs.keySet()){
            subsList.add(subs.get(id));
        }
        return subsList;
    }

    @Override
    public ArrayList<Task> getTasks() {
        ArrayList<Task> tasksList = new ArrayList<>();
        for (Integer id : tasks.keySet()){
            tasksList.add(tasks.get(id));
        }
        return tasksList;
    }

    @Override
    public void  deleteEpic(int id){
        if (epics.containsKey(id)){
            epics.remove(id);
        }
    }

    @Override
    public void  deleteSub(int id){
        if (subs.containsKey(id)){
            subs.remove(id);
        }
    }

    @Override
    public void  deleteTask(int id){
        if (tasks.containsKey(id)){
            tasks.remove(id);
        }
    }

    @Override
    public Epic getEpic (int id){
        Managers.getDefaultHistory().add(epics.get(id));
        return epics.get(id);
    }

    @Override
    public SubTask getSub (int id){
       Managers.getDefaultHistory().add(subs.get(id));
        return subs.get(id);
    }

    @Override
    public Task getTask(int id){
        Managers.getDefaultHistory().add(tasks.get(id));
        return tasks.get(id);
    }

    @Override
    public void print(){
     System.out.println(epics);
     System.out.println(subs);
     System.out.println(tasks);
    }

    @Override
    public int generateId(){
        return id++;
    }

}
