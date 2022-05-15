package service.history;

import service.task.*;


import java.util.LinkedList;


public class InMemoryHistoryManager implements HistoryManager {
    private final LinkedList<Task> history = new LinkedList<>();
    private final static int NOTES = 10;

    @Override
    public void add(Task task) {
        if (task == null) return;
        if (history.size() > NOTES) {
            history.remove(0);
        }
        history.add(task);
    }

    @Override
    public LinkedList<Task> getHistory() {
        return history;
    }

    @Override
    public String toString() {
        return "service.history.InMemoryHistoryManager{" +
                "history=" + history +
                '}';
    }
}
