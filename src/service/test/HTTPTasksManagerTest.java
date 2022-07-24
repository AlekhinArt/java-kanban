package service.test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.kvs.KVServer;
import service.manager.FileBackedTasksManager;
import service.manager.HTTPTaskManager;
import service.task.Epic;
import service.task.Status;
import service.task.SubTask;
import service.task.Task;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class HTTPTasksManagerTest extends TaskManagerTest<HTTPTaskManager> {

    HTTPTaskManager httpTaskManagerTestOne = new HTTPTaskManager(new URL("http://localhost"));
    HTTPTaskManager httpTaskManagerTestTwo = new HTTPTaskManager(new URL("http://localhost"));
    URL url = new URL("http://localhost");
    KVServer kvServer;
    Epic testEpic;
    SubTask testSubtask1;
    SubTask testSubtask2;
    Task testTask1;
    Task testTask2;


    public HTTPTasksManagerTest() throws MalformedURLException {
        httpTaskManagerTestOne = new HTTPTaskManager(url);
        httpTaskManagerTestTwo = new HTTPTaskManager(url);
        testEpic = new Epic("Test EPIC", "Test addNew description", Status.DONE);
        testSubtask1 = new SubTask("Test SUBTASK 1", "Test addNew description",
                Status.NEW, testEpic.getId());
        testSubtask2 = new SubTask("Test SUBTASK 2", "Test addNew description",
                Status.DONE, testEpic.getId());
        testTask1 = new Task("Test TASK 1", "Test addNew description", Status.NEW);
        testTask2 = new Task("Test TASK 1", "Test addNew description", Status.NEW);

    }

    @BeforeEach
    void beforeEach() throws IOException {
        kvServer.start();
        httpTaskManagerTestOne = new HTTPTaskManager(url);
        httpTaskManagerTestTwo = new HTTPTaskManager(url);
        testEpic = new Epic("Test EPIC", "Test addNew description", Status.DONE);
        testSubtask1 = new SubTask("Test SUBTASK 1", "Test addNew description",
                Status.NEW, testEpic.getId());
        testSubtask2 = new SubTask("Test SUBTASK 2", "Test addNew description",
                Status.DONE, testEpic.getId());
        testTask1 = new Task("Test TASK 1", "Test addNew description", Status.NEW);
        testTask2 = new Task("Test TASK 1", "Test addNew description", Status.NEW);

    }

    @AfterEach
    void AfterEach() {
        kvServer.stop();
    }

    @Test
    void sendTaskToServer() {




    }




}
