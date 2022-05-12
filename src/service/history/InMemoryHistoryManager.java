package service.history;

import service.task.*;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private List <Task> history = new ArrayList<>();
    private final static int notes = 10;

    @Override
    public void add(Task task) {
        if (history.size() < notes){
            history.add(0, task);
        }else {
            history.remove(history.size()-1);
            history.add(0,task);
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
