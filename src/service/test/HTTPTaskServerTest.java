package service.test;

import com.google.gson.Gson;
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
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HTTPTaskServerTest {
    HTTPTaskManager httpTaskManagerTestOne;//= new HTTPTaskManager(new URL("http://localhost"));
    HTTPTaskManager httpTaskManagerTestTwo;// = new HTTPTaskManager(new URL("http://localhost"));
    URL url = new URL("http://localhost");
    KVServer kvServer;
    Epic testEpic;
    SubTask testSubtask1;
    SubTask testSubtask2;
    Task testTask1;
    Task testTask2;
    HttpTaskServer testHttpTaskServer;


    public HTTPTaskServerTest() throws IOException {
        kvServer = new KVServer();
        testHttpTaskServer = new HttpTaskServer();
        //  httpTaskManagerTestOne = new HTTPTaskManager(url);
        // httpTaskManagerTestTwo = new HTTPTaskManager(url);
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
        testHttpTaskServer.start();
//    testHttpTaskServer = new HttpTaskServer();
        httpTaskManagerTestOne = new HTTPTaskManager(url);
        //httpTaskManagerTestTwo = new HTTPTaskManager(url);


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
    void check() {
        httpTaskManagerTestOne.addTask(testTask1);
        HttpResponse<String> response = null;
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder();
        String uri = url + ":8080" + "/tasks/task";
        HttpRequest request = requestBuilder
                .GET()    // указываем HTTP-метод запроса
                .uri(URI.create(uri)) // указываем адрес ресурса
                .version(HttpClient.Version.HTTP_1_1) // указываем версию протокола HTTP
                .header("Content-Type", "application/json") // указываем заголовок Accept
                .build(); // заканчиваем настройку и создаём ("строим") HTTP-запрос
        HttpClient client = HttpClient.newHttpClient();

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // kvServer.stop();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(testTask1.toString(), response.body().toString());

        Gson gson = new Gson();
        //Task task = gson.fromJson(response, Task.class);

    }


}
