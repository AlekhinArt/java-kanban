package service.manager;

import service.history.HistoryManager;
import service.task.*;

import java.time.Duration;
import java.util.*;

public class InMemoryTaskManager implements TaskManager {
    private int id;
    private final Map<Integer, Epic> epics = new HashMap<>();
    private final Map<Integer, SubTask> subs = new HashMap<>();
    private final Map<Integer, Task> tasks = new HashMap<>();
    private final Set<Task> prioritizedTasks = new TreeSet<>(Comparator.comparing(Task::getStartTime));
    private final List<Task> tasksNoDate = new LinkedList<>();
    protected final HistoryManager historyManager = Managers.getDefaultHistory();

    @Override
    public void addEpic(Epic epic) {
        if (epic == null) return;
        epic.setId(generateId());
        setEpicStatus(epic);
        epics.put(epic.getId(), epic);
    }

    @Override
    public void addSub(SubTask subTask) {
        if (subTask == null) return;
        Epic epic = epics.get(subTask.getEpicId());
        if (epic == null) return;
        if (checkTimeIntersection(subTask)) return;
        subTask.setId(generateId());
        subs.put(subTask.getId(), subTask);
        List<Integer> subsListId = epic.getSubsId();
        subsListId.add(subTask.getId());
        epic.setSubsId(subsListId);
        setEpicStatus(epic);
        setEpicTime(epic);
        checkTimeAndAdd(subTask);

    }

    @Override
    public void addTask(Task task) {
        if (task == null) return;
        if (checkTimeIntersection(task)) return;
        task.setId(generateId());
        tasks.put(task.getId(), task);
        checkTimeAndAdd(task);
    }

    @Override
    public void updateEpic(Epic epic, int id) {
        if (epic == null) return;
        if (epics.containsKey(id)) {
            epics.put(id, epic);
        }
    }

    @Override
    public void updateSub(SubTask subTask, int id) {
        if (subTask == null) return;
        if (checkTimeIntersection(subTask)) return;
        if (subs.containsKey(id)) {
            subs.put(id, subTask);
            setEpicStatus(epics.get(subTask.getEpicId()));
            setEpicTime(epics.get(subTask.getEpicId()));
            checkTimeAndAdd(subTask);
        }
    }

    @Override
    public void updateTask(Task task, int id) {
        if (task == null) return;
        if (checkTimeIntersection(task)) return;
        if (tasks.containsKey(id)) {
            tasks.put(id, task);
            checkTimeAndAdd(task);
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
            checkTimeAndRemove(subs.get(id));
            deleteSub(id);
        }
    }

    @Override
    public void deleteAllTasks() {
        List<Integer> tasksId = new ArrayList<>();
        tasksId.addAll(tasks.keySet());
        for (Integer id : tasksId) {
            checkTimeAndRemove(tasks.get(id));
            deleteTask(id);
        }
    }

    @Override
    public List<Epic> getEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public ArrayList<SubTask> getEpicSubtasks(int id) {
        Epic epic = epics.get(id);
        List<Integer> subsId = new ArrayList<>(epic.getSubsId());
        ArrayList<SubTask> subsInEpic = new ArrayList<>();
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
            checkTimeAndRemove(subs.get(id));
            subs.remove(id);
            setEpicStatus(epics.get(epicId));
            setEpicTime(epics.get(epicId));
            historyManager.remove(id);
        }
    }

    @Override
    public void deleteTask(int id) {
        if (tasks.containsKey(id)) {
            checkTimeAndRemove(tasks.get(id));
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
                continue;
            }
            if (epic.getStatus().equals(Status.NEW)) {
                if (!subs.get(subId).getStatus().equals(Status.NEW)) {
                    epic.setStatus(Status.IN_PROGRESS);
                }
                continue;
            }
            if (epic.getStatus().equals(Status.DONE)) {
                if (!subs.get(subId).getStatus().equals(Status.DONE)) {
                    epic.setStatus(Status.IN_PROGRESS);
                }
                continue;
            }
            epic.setStatus(Status.DONE);
        }
    }

    private void setEpicTime(Epic epic) {
        if (epic.getSubTasks().isEmpty()) return;
        for (int id : epic.getSubsId()) {
            epic.getSubTasks().add(Managers.getDefault().getSubs().get(id));
        }
        epic.getSubTasks().sort(Comparator.comparing(Task::getStartTime));
        epic.setStartTime(epic.getSubTasks().get(0).getStartTime());
        epic.setEndTime(epic.getSubTasks().get(epic.getSubTasks().size() - 1).getStartTime());
        Duration durationBetween = Duration.between(epic.getStartTime(), epic.getEndTime());
        epic.setDuration(durationBetween.toMinutesPart());
    }

    private int generateId() {
        return id++;
    }

    @Override
    public List<Task> getPrioritizedTasks() {
        List<Task> tasks = new LinkedList<>();
        tasks.addAll(prioritizedTasks);
        tasks.addAll(tasksNoDate);
        return tasks;
    }

    private void checkTimeAndAdd(Task task) {
        if (task.getStartTime() != null) {
            prioritizedTasks.add(task);
        } else {
            tasksNoDate.add(task);
        }
    }
// Возможно не заметили, чуть ниже для удаления)

    private void checkTimeAndRemove(Task task) {
        if (task.getStartTime() != null) {
            prioritizedTasks.remove(task);
        } else {
            tasksNoDate.remove(task);
        }
    }

    private boolean checkTimeIntersection(Task task) {
        if (task.getStartTime() != null) {
            for (Task prioritizedTask : prioritizedTasks) {
                if (prioritizedTask.getStartTime().isAfter(task.getStartTime())
                        && prioritizedTask.getEndTime().isBefore(task.getEndTime())) {
                    return true;
                }
            }
        }
        return false;
    }

}
