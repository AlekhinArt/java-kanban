package service.manager;

import service.history.HistoryManager;
import service.task.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {
    private int id;
    private final Map<Integer, Epic> epics = new HashMap<>();
    private final Map<Integer, SubTask> subs = new HashMap<>();
    private final Map<Integer, Task> tasks = new HashMap<>();
    private final HistoryManager historyManager = Managers.getDefaultHistory();

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
        List<Integer> subsListId = new ArrayList<>(epic.getSubsId());
        subsListId.add(subTask.getId());
        epic.setSubsId(subsListId);
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
        List<Integer> epicsId = new ArrayList<>();
        epicsId.addAll(epics.keySet());
        for (Integer id : epicsId) {
            deleteEpic(id);
        }
    }

    @Override
    public void deleteAllSubTasks() {
        List<Integer> subsId = new ArrayList<>();
        subsId.addAll(subs.keySet());
        for (Integer id : subsId) {
            deleteSub(id);
        }
    }

    @Override
    public void deleteAllTasks() {
        List<Integer> tasksId = new ArrayList<>();
        tasksId.addAll(tasks.keySet());
        for (Integer id : tasksId) {
            deleteTask(id);
        }
    }

    @Override
    public List<Epic> getEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public ArrayList<Task> getEpicSubtasks(int id) {
        Epic epic = epics.get(id);
        List<Integer> subsId = new ArrayList<>(epic.getSubsId());
        ArrayList<Task> subsInEpic = new ArrayList<>();
        for (Integer subId : subsId) {
            subsInEpic.add(subs.get(subId));
        }
        return subsInEpic;
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
            List<Integer> delSubsId = new ArrayList<>(epics.get(id).getSubsId());
            epics.remove(id);
            for (Integer delSubId : delSubsId) {
                historyManager.remove(delSubId);

                subs.remove(delSubId);
            }
            historyManager.remove(id);
        }
    }

    @Override
    public void deleteSub(int id) {
        if (subs.containsKey(id)) {
            int epicId = subs.get(id).getEpicId();
            SubTask subTask = subs.get(id);
            Epic epic = epics.get(subTask.getEpicId());
            List<Integer> subsListId = epic.getSubsId();
            subsListId.removeIf(i -> i.equals(subTask.getId()));
            epic.setSubsId(subsListId);
            subs.remove(id);
            setEpicStatus(epics.get(epicId));
            historyManager.remove(id);
        }
    }

    @Override
    public void deleteTask(int id) {
        if (tasks.containsKey(id)) {
            tasks.remove(id);
            historyManager.remove(id);
        }
    }

    @Override
    public Epic getEpic(int id) {
        if (!epics.containsKey(id)) return null;
        Epic epic = epics.get(id);
        historyManager.add(epic);
        return epic;
    }

    @Override
    public SubTask getSub(int id) {
        if (!subs.containsKey(id)) return null;
        SubTask subTask = subs.get(id);
        historyManager.add(subTask);
        return subTask;
    }

    @Override
    public Task getTask(int id) {
        if (!tasks.containsKey(id)) return null;
        Task task = tasks.get(id);
        historyManager.add(task);
        return task;
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    private void setEpicStatus(Epic epic) {
        List<Integer> subTasksId = epic.getSubsId();
        for (Integer subId : subTasksId) {
            if (subs.get(subId) == null) {
                epic.setStatus(Status.NEW);
                return;
            }
            if (!subs.get(subId).getStatus().equals(Status.DONE)) {
                epic.setStatus(Status.IN_PROGRESS);
                return;
            } else {
                epic.setStatus(Status.DONE);
            }
        }
    }

    private int generateId() {
        return id++;
    }

}
