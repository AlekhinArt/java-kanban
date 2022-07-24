package service.test;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.http.HttpTaskServer;
import service.kvs.KVServer;
import service.manager.HTTPTaskManager;
import service.manager.Managers;
import service.task.Status;
import service.task.Task;

import java.io.IOException;
import java.net.URI;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class HTTPTaskServerTest2 {
    private KVServer kvServer;
    private HttpTaskServer httpTaskServer;
    private HttpClient taskClient;

    @BeforeEach
    public void startServers() throws IOException {
        kvServer = new KVServer();
        kvServer.start();
        httpTaskServer = new HttpTaskServer();
        httpTaskServer.start();
        taskClient = HttpClient.newHttpClient();
    }

    @AfterEach
    public void stopServer() {
        kvServer.stop();
        httpTaskServer.stop();
    }

    @Test
    void sum(){
        int a = 5;
        assertEquals(5, a,  "В истории более одной задачи");
    }

    @Test
    void testGet() throws IOException, InterruptedException {
        HTTPTaskManager asd = new HTTPTaskManager(new URL("http://localhost"));
        asd.addTask(new Task("qwe1","qwe", Status.NEW));
        Managers.getDefault().addTask(new Task("qwe","qwe", Status.NEW));
        URI uri = URI.create("http://localhost:8080/tasks/task/");
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder();
        HttpRequest request = requestBuilder
                .GET()
                .uri(uri)
                .version(HttpClient.Version.HTTP_1_1)
                .header("Content-Type", "application/json")
                .build();
        taskClient = HttpClient.newHttpClient();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = taskClient.send(request, handler);
        System.out.println(response.body());
        System.out.println(response.request());




    }


}
