package service.manager;

import service.task.*;

import java.util.ArrayList;

public interface TaskManager {

    void setEpicStatus(Epic epic);

    void addEpic(Epic epic);

    void addSub (SubTask subTask);

    void addTask(Task task);

    void updateEpic(Epic epic, int id);

    void updateSub(SubTask subTask, int id);

    void updateTask(Task task, int id);

    void deleteAllEpics();

    void deleteAllSubTasks();

    void deleteAllTasks();

    ArrayList<Epic> getEpics();

    ArrayList<SubTask> getEpicSubtasks(int id);

    ArrayList<SubTask> getSubs();

    ArrayList<Task> getTasks();

    void  deleteEpic(int id);

    void  deleteSub(int id);

    void  deleteTask(int id);

    Epic getEpic (int id);

    SubTask getSub (int id);

    Task getTask(int id);

    public void print();

    public int generateId();

}


