package service;

import service.history.*;

public class Managers {
    private static service.manager.TaskManager TaskManager;
    private static HistoryManager InMemoryHistoryManager = new InMemoryHistoryManager();

    static public service.manager.TaskManager getDefault(){
        return TaskManager;
    }

    static public HistoryManager getDefaultHistory(){
        return InMemoryHistoryManager;
    }

}
