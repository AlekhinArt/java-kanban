package service.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.manager.InMemoryTaskManager;
import service.task.Epic;
import service.task.Status;
import service.task.SubTask;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest extends TaskManagerTest {

    InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
    Epic epic;
    SubTask subTask;
    SubTask subTask2;

    @BeforeEach
    public void beforeEach() {
        epic = new Epic("Test epicStatus", "Test epicStatus description", Status.NEW);
        subTask = new SubTask("Test epicStatus",
                "Test epicStatus description", Status.NEW, epic.getId());
        subTask2 = new SubTask("Test epicStatus",
                "Test epicStatus description", Status.NEW, epic.getId());
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


}