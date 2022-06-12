package service.manager;

import service.task.*;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {

    void addEpic(Epic epic);

    void addSub(SubTask subTask);

    void addTask(Task task);

    void updateEpic(Epic epic, int id);

    void updateSub(SubTask subTask, int id);

    void updateTask(Task task, int id);

    void deleteAllEpics();

    void deleteAllSubTasks();

    void deleteAllTasks();

    List<Epic> getEpics();

    ArrayList<Task> getEpicSubtasks(int id);

    List<SubTask> getSubs();

    List<Task> getTasks();

    void deleteEpic(int id);

    void deleteSub(int id);

    void deleteTask(int id);

    Epic getEpic(int id);

    SubTask getSub(int id);

    Task getTask(int id);

    List <Task> getHistory();

}


