package service.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.manager.InMemoryTaskManager;
import service.task.Epic;
import service.task.Status;
import service.task.SubTask;
import service.task.Task;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest extends TaskManagerTest {

    InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
    Epic epic;
    SubTask subTask;
    SubTask subTask2;
    SubTask subTask3;

    @BeforeEach
    public void beforeEach() {
        epic = new Epic("Test epicStatus", "Test epicStatus description", Status.NEW);
        subTask = new SubTask("Test 1 ", "Test epicStatus description",
                Status.NEW, LocalDateTime.now(), 8, epic.getId());
        subTask2 = new SubTask("Test 2 ", "Test epicStatus description",
                Status.NEW, LocalDateTime.now().plusHours(1), 8, epic.getId());
        subTask3 = new SubTask("Test 3", "Test epicStatus description",
                Status.NEW, LocalDateTime.now().plusHours(2), 8, epic.getId());

    }

    @Test
    void epicStatusWithEmptyListOfSubtasks() {
        inMemoryTaskManager.addEpic(epic);

        assertEquals(Status.NEW, epic.getStatus(), "Статус не верный");
    }

    @Test
    void epicStatusWithAllSubtasksStatusNew() {
        inMemoryTaskManager.addEpic(epic);
        inMemoryTaskManager.addSub(subTask);
        inMemoryTaskManager.addSub(subTask2);
        assertEquals(Status.NEW, epic.getStatus(), "Статус не верный");
    }

    @Test
    void epicStatusWithAllSubtasksStatusDone() {
        subTask.setStatus(Status.DONE);
        subTask2.setStatus(Status.DONE);
        inMemoryTaskManager.addEpic(epic);
        inMemoryTaskManager.addSub(subTask);
        inMemoryTaskManager.addSub(subTask2);

        assertEquals(Status.DONE, epic.getStatus(), "Статус не верный");
    }

    @Test
    void epicStatusWithSubtasksStatusDoneAndNew() {
        subTask.setStatus(Status.DONE);
        subTask2.setStatus(Status.NEW);
        inMemoryTaskManager.addEpic(epic);
        inMemoryTaskManager.addSub(subTask);
        inMemoryTaskManager.addSub(subTask2);

        assertEquals(Status.IN_PROGRESS, epic.getStatus(), "Статус не верный");
    }

    @Test
    void epicStatusWithAllSubtasksStatusInProgress() {
        subTask.setStatus(Status.IN_PROGRESS);
        subTask2.setStatus(Status.IN_PROGRESS);
        inMemoryTaskManager.addEpic(epic);
        inMemoryTaskManager.addSub(subTask);
        inMemoryTaskManager.addSub(subTask2);

        assertEquals(Status.IN_PROGRESS, epic.getStatus(), "Статус не верный");
    }

    @Test
    void getPrioritizedTasks() {
        inMemoryTaskManager.addEpic(epic);
        inMemoryTaskManager.addSub(subTask);
        inMemoryTaskManager.addSub(subTask3);
        inMemoryTaskManager.addSub(subTask2);

        List<Task> testList = new LinkedList<>();
        testList.add(0, subTask);
        testList.add(1, subTask2);
        testList.add(2, subTask3);

        List<Task> prioritizedList = inMemoryTaskManager.getPrioritizedTasks();
        assertEquals(3, prioritizedList.size(), " Добавлены не все задачи");
        assertEquals(testList.get(0), prioritizedList.get(0), "Задачи отсортированы не правильно");
        assertEquals(testList.get(1), prioritizedList.get(1), "Задачи отсортированы не правильно");
        assertEquals(testList.get(2), prioritizedList.get(2), "Задачи отсортированы не правильно");
    }


}