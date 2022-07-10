package service.test;

import org.junit.jupiter.api.Test;
import service.history.InMemoryHistoryManager;
import service.task.Status;
import service.task.Task;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {
    InMemoryHistoryManager historyManager = new InMemoryHistoryManager();

    @Test
    void add() {
        Task task = new Task("Test addNew", "Test addNew description", Status.DONE);
        historyManager.add(task);
        final List<Task> history = historyManager.getHistory();
        assertNotNull(history, "История пустая.");
        assertEquals(1, history.size(), "История пустая.");

        historyManager.add(task);
        assertNotNull(history, "История пустая.");
        assertEquals(1, history.size(), "В истории более одной задачи");
    }

    @Test
    void remove() {
        Task task = new Task("Test addNew", "Test addNew description", Status.DONE);
        historyManager.add(task);
        final int id = task.getId();
        final List<Task> history = historyManager.getHistory();

        historyManager.remove(id);
        assertNotNull(history, "История не пустая.");
        assertEquals(1, history.size(), "История не пустая.");

        historyManager.remove(id);
        assertNotNull(history, "История не пустая.");
        assertEquals(1, history.size(), "История не пустая.");

        historyManager.remove(-1);
        assertNotNull(history, "История не пустая.");
        assertEquals(1, history.size(), "История не пустая.");

        Task task1 = new Task("Test addNew", "Test addNew description", Status.DONE);
        Task task2 = new Task("Test addNew", "Test addNew description", Status.DONE);
        Task task3 = new Task("Test addNew", "Test addNew description", Status.DONE);
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task3);

    }

    @Test
    void getHistory() {
        Task task = new Task("Test addNew", "Test addNew description", Status.DONE);
        historyManager.add(task);

        List<Task> history = historyManager.getHistory();

        assertNotNull(history, "Задачи на возвращаются.");
        assertEquals(1, history.size(), "Неверное количество задач.");
        assertEquals(task, history.get(0), "Задачи не совпадают.");

        task = new Task("Test addNew", "Test addNew description", Status.DONE);
        historyManager.add(task);
        assertNotNull(history, "Задачи на возвращаются.");
        assertEquals(1, history.size(), "Неверное количество задач.");
        assertEquals(task, history.get(0), "Задачи не совпадают.");
    }

    @Test
    void getHistoryInEmptyList() {
        List<Task> history = historyManager.getHistory();

        assertNotNull(history, "Задачи на возвращаются.");
        assertEquals(0, history.size(), "Список не пустой");

    }

}