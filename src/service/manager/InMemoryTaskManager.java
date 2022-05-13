package service.manager;

import service.Managers;
import service.task.*;

import java.util.ArrayList;
import java.util.HashMap;

public class InMemoryTaskManager implements TaskManager {
    private int id;
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private HashMap<Integer, SubTask> subs = new HashMap<>();
    private HashMap<Integer, Task> tasks = new HashMap<>();

    private void setEpicStatus(Epic epic) {
        ArrayList<SubTask> subTasks = epic.getSubs();
        if (subTasks == null) {
            epic.setStatus(Status.NEW);
            return;
        }
        for (SubTask subTask : subTasks) {
            if (!subTask.getStatus().equals(Status.DONE)) {
                epic.setStatus(Status.IN_PROGRESS);
                return;
            } else {
                epic.setStatus(Status.DONE);
            }
        }
    }

    @Override
    public void addEpic(Epic epic) {
        epic.setId(generateId());
        setEpicStatus(epic);
        epics.put(epic.getId(), epic);
    }

    @Override
    public void addSub(SubTask subTask) {
        subTask.setId(generateId());
        subs.put(subTask.getId(), subTask);
        Epic epic = epics.get(subTask.getEpicId());
        ArrayList<SubTask> subsList = epic.getSubs();
        subsList.add(subTask);
        epic.setSubs(subsList);
        setEpicStatus(epic);
    }

    @Override
    public void addTask(Task task) {
        task.setId(generateId());
        tasks.put(task.getId(), task);
    }

    @Override
    public void updateEpic(Epic epic, int id) {
        if (epics.containsKey(id)) {
            epics.put(id, epic);
        }
    }

    @Override
    public void updateSub(SubTask subTask, int id) {
        if (subs.containsKey(id)) {
            subs.put(id, subTask);
            setEpicStatus(epics.get(subTask.getEpicId()));
        }
    }

    @Override
    public void updateTask(Task task, int id) {
        if (tasks.containsKey(id)) {
            tasks.put(id, task);
        }
    }

    @Override
    public void deleteAllEpics() {
        epics.clear();
        deleteAllSubTasks();
    }

    @Override
    public void deleteAllSubTasks() {
        subs.clear();
    }

    @Override
    public void deleteAllTasks() {
        tasks.clear();
    }

    @Override
    public ArrayList<Epic> getEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public ArrayList<SubTask> getEpicSubtasks(int id) {
        Epic epic = epics.get(id);
        return epic.getSubs();
    }

    @Override
    public ArrayList<SubTask> getSubs() {
        return new ArrayList<>(subs.values());
    }

    @Override
    public ArrayList<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public void deleteEpic(int id) {
        if (epics.containsKey(id)) {
            epics.remove(id);
            ArrayList<SubTask> delSubs = epics.get(id).getSubs();
            for (SubTask sub : delSubs) {
                subs.remove(sub.getId());
            }
        }
    }

    @Override
    public void deleteSub(int id) {
        if (subs.containsKey(id)) {
            subs.remove(id);
            setEpicStatus(epics.get(subs.get(id).getEpicId()));
        }
    }

    @Override
    public void deleteTask(int id) {
        if (tasks.containsKey(id)) {
            tasks.remove(id);
        }
    }

    @Override
    public Epic getEpic(int id) {
        Epic epic = epics.get(id);
        if (epic != null) {
            Managers.getDefaultHistory().add(epic);
        }
        return epic;
    }

    @Override
    public SubTask getSub(int id) {
        SubTask subTask = subs.get(id);
        if (subTask != null) {
            Managers.getDefaultHistory().add(subTask);
        }
        return subTask;
    }

    @Override
    public Task getTask(int id) {
        Task task = tasks.get(id);
        if (task != null) {
            Managers.getDefaultHistory().add(task);
        }
        return task;
    }

    private int generateId() {
        return id++;
    }

}
