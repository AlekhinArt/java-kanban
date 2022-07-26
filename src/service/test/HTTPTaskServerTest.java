package service.test;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.http.HttpTaskServer;
import service.kvs.KVServer;
import service.manager.HTTPTaskManager;
import service.task.Epic;
import service.task.Status;
import service.task.SubTask;
import service.task.Task;

import java.io.IOException;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class HTTPTaskServerTest {
    URL url = new URL("http://localhost:8078");
    private KVServer kvServer;
    private HttpTaskServer httpTaskServer;
    HTTPTaskManager httpTaskManager;
    HTTPTaskManager testHttpTaskManager;
    Epic testEpic;
    SubTask testSubtask1;
    SubTask testSubtask2;
    Task testTask1;
    Task testTask2;

    public HTTPTaskServerTest() throws MalformedURLException {
    }

    @BeforeEach
    public void startServers() throws IOException, InterruptedException {
        kvServer = new KVServer();
        kvServer.start();
        httpTaskServer = new HttpTaskServer();
        httpTaskServer.start();
        httpTaskManager = new HTTPTaskManager(url);
        testHttpTaskManager = new HTTPTaskManager(url);
        httpTaskManager = HTTPTaskManager.loadFromServer(url);
        testEpic = new Epic("Test EPIC", "Test addNew description", Status.DONE);
        testSubtask1 = new SubTask("Test SUBTASK 1", "Test addNew description",
                Status.NEW, 1);
        testSubtask2 = new SubTask("Test SUBTASK 2", "Test addNew description",
                Status.DONE, 1);
        testTask1 = new Task("Test TASK 1", "Test addNew description", Status.NEW);
        testTask2 = new Task("Test TASK 1", "Test addNew description", Status.NEW);

    }

    @AfterEach
    public void stopServer() {
        kvServer.stop();
        httpTaskServer.stop();
    }

    @Test
    void loadFromServerSubTasks() throws IOException, InterruptedException {
        httpTaskManager.addEpic(testEpic);
        httpTaskManager.addSub(testSubtask1);
        httpTaskManager.addSub(testSubtask2);
        httpTaskManager.addTask(testTask1);
        List<SubTask> subTasks = httpTaskManager.getSubs();
        assertNotNull(subTasks, "Список задач пустой");

        testHttpTaskManager = HTTPTaskManager.loadFromServer(url);
        List<SubTask> testSubTasks = testHttpTaskManager.getSubs();

        assertEquals(2, testSubTasks.size(), "Не корректно загружен список ");
        assertEquals(subTasks, testSubTasks, "Не корректно загружен список ");
    }

    @Test
    void loadFromServerOneTask() throws IOException, InterruptedException {
        httpTaskManager.addTask(testTask1);
        List<Task> tasks = httpTaskManager.getTasks();
        assertEquals(1, tasks.size(), "Список задач пустой, задача не загружена");

        testHttpTaskManager = HTTPTaskManager.loadFromServer(url);
        List<Task> testTasks = testHttpTaskManager.getTasks();
        assertEquals(1, testTasks.size(), "Список задач пустой, задача не выгружена");

        Task testTask2 = testTasks.get(0);
        assertEquals(testTask1, testTask2, "Задача не корректно сохранена/выгружена");

    }

    @Test
    void loadFromEmptyServer() throws IOException, InterruptedException {
        testHttpTaskManager = HTTPTaskManager.loadFromServer(url);
        assertEquals(0, testHttpTaskManager.getTasks().size(), "Выгрузка с пустого сервера не корректна");
        assertEquals(0, testHttpTaskManager.getSubs().size(), "Выгрузка с пустого сервера не корректна");
        assertEquals(0, testHttpTaskManager.getEpics().size(), "Выгрузка с пустого сервера не корректна");
        assertEquals(0, testHttpTaskManager.getHistory().size(), "Выгрузка с пустого сервера не корректна");
    }

    @Test
    void history() throws IOException, InterruptedException {
        httpTaskManager.addTask(testTask1);
        httpTaskManager.addEpic(testEpic);
        httpTaskManager.getTask(testTask1.getId());
        httpTaskManager.getEpic(testEpic.getId());

        List<Task> history = httpTaskManager.getHistory();
        assertEquals(2, history.size(), "история записана не верно");

        testHttpTaskManager = HTTPTaskManager.loadFromServer(url);
        List<Task> historyTest = testHttpTaskManager.getHistory();
        assertEquals(2, historyTest.size(), "история выгружена не корректно");
        assertEquals(historyTest, history, "Истории не отличаются");

    }


}
