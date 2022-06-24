package service.manager;

import service.history.*;


public class Managers {
    private static final HistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();
    private static final TaskManager taskManager = new InMemoryTaskManager();
    private static final TaskManager fileBacked = new FileBackedTasksManager();

    static public HistoryManager getDefaultHistory() {
        return inMemoryHistoryManager;
    }

    static public TaskManager getDefault() {
        return taskManager;
    }

    static public TaskManager getFileBacked() {
        return fileBacked;
    }

}
