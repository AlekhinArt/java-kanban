package service.history;

import service.task.*;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private List<Task> history = new ArrayList<>();
    private final static int NOTES = 10;

    @Override
    public void add(Task task) {
        if (history.size() < NOTES) {
            history.add(task);
        } else {
            history.remove(0);
            history.add(task);
        }
    }

    @Override
    public List<Task> getHistory() {
        return history;
    }

    @Override
    public String toString() {
        return "service.history.InMemoryHistoryManager{" +
                "history=" + history +
                '}';
    }
}
