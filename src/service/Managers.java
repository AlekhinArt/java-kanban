package service;

import service.history.*;
import service.manager.*;

public class Managers {
    private static HistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();
    private static TaskManager taskManager = new InMemoryTaskManager();

    static public HistoryManager getDefaultHistory() {
        return inMemoryHistoryManager;
    }

    static public TaskManager getDefault() {
        return taskManager;
    }

}
