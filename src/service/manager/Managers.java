package service.manager;

import service.history.*;


public class Managers {
    private static final HistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();
    private static final TaskManager taskManager = new InMemoryTaskManager();

    static public HistoryManager getDefaultHistory() {
        return inMemoryHistoryManager;
    }

    static public TaskManager getDefault() {
        return taskManager;
    }

}
