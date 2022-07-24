package service.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import service.kvs.KVServer;
import service.manager.Managers;
import service.manager.TaskManager;
import service.task.Epic;
import service.task.Status;
import service.task.SubTask;
import service.task.Task;
import service.typeAdapter.EpicAdapter;
import service.typeAdapter.LocalDateTimeAdapter;
import service.typeAdapter.SubTaskAdapter;
import service.typeAdapter.TaskAdapter;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.concurrent.Executor;


import static java.nio.charset.StandardCharsets.UTF_8;


public class HttpTaskServer {

    private static final int PORT = 8080;
    static TaskManager managers = Managers.getDefault();
    private static final Gson gson = new Gson();
    HttpServer taskServer;

    public HttpTaskServer() throws IOException {
        taskServer = HttpServer.create();
        taskServer.bind(new InetSocketAddress(PORT), 0);
        taskServer.createContext("/task", new HelloHandler());

//        taskServer= HttpServer.create(new InetSocketAddress("localhost", PORT), 0);

    }


    public static void main(String[] args) throws IOException {
//    HttpTaskServer as = new HttpTaskServer();
//    as.start();
//        KVServer kvServer = new KVServer();
//        kvServer.start();
//        HttpTaskServer asas = new HttpTaskServer();
//        asas.start();

//
        managers.addTask(new Task("Test TASK 1", "Test addNew description", Status.NEW));


    }

    public void start() throws IOException {
        taskServer.start();
    }

    public void stop() {
        taskServer.stop(0);
    }


    public static class HelloHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            InputStream inputStream = exchange.getRequestBody();
            String method = exchange.getRequestMethod();
            String path = exchange.getRequestURI().getPath();
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                    .registerTypeAdapter(Epic.class, new EpicAdapter())
                    .registerTypeAdapter(SubTask.class, new SubTaskAdapter())
                    .registerTypeAdapter(Task.class, new TaskAdapter())
                    .create();
            int id = -1;
            URI requestURI = exchange.getRequestURI();
            if (requestURI.getQuery() != null) {
                id = Integer.parseInt(requestURI.getQuery().split("=")[1]);
            }
            String response;
            String body = new String(inputStream.readAllBytes());

            switch (path) {
                case ("/tasks/task/"):
                    switch (method) {
                        case ("GET"):
                            if (id >= 0) {

                                response = gson.toJson(managers.getTask(id));

                            } else {
                                response = gson.toJson(managers.getTasks());

                            }


                            sendText(exchange, response);
                            break;
                        case ("POST"):
                            managers.addTask(gson.fromJson(body, Task.class));
                            exchange.sendResponseHeaders(201, 0);
                            exchange.close();
                            break;
                        case ("DELETE"):
                            if (id >= 0) {
                                managers.deleteTask(id);
                            } else {
                                managers.deleteAllTasks();
                            }
                            exchange.sendResponseHeaders(200, 0);
                            exchange.close();
                            break;
                    }
                    break;
                case ("/tasks/subtask/"):
                    switch (method) {
                        case ("GET"):
                            if (id >= 0) {
                                response = gson.toJson(managers.getSub(id));
                            } else {
                                response = gson.toJson(managers.getSubs());
                            }
                            sendText(exchange, response);
                            break;
                        case ("POST"):
                            managers.addSub(gson.fromJson(body, SubTask.class));
                            exchange.sendResponseHeaders(201, 0);
                            exchange.close();
                            break;
                        case ("DELETE"):
                            if (id >= 0) {
                                managers.deleteSub(id);
                            } else {
                                managers.deleteAllSubTasks();
                            }
                            exchange.sendResponseHeaders(200, 0);
                            break;
                    }
                    break;
                case ("/tasks/epic/"):
                    switch (method) {
                        case ("GET"):
                            if (id >= 0) {
                                response = gson.toJson(managers.getEpic(id));
                            } else {
                                response = gson.toJson(managers.getEpics());
                            }
                            sendText(exchange, response);
                            break;
                        case ("POST"):
                            managers.addEpic(gson.fromJson(body, Epic.class));
                            exchange.sendResponseHeaders(201, 0);
                            exchange.close();
                            break;
                        case ("DELETE"):
                            if (id >= 0) {
                                managers.deleteEpic(id);
                            } else {
                                managers.deleteAllEpics();
                            }
                            exchange.sendResponseHeaders(200, 0);
                            break;
                    }
                    break;
                case ("/tasks/history"):
                    response = gson.toJson(managers.getHistory());
                    sendText(exchange, response);

                    break;
                case ("/tasks/subtask/epic/"):
                    response = gson.toJson(managers.getEpicSubtasks(id));
                    sendText(exchange, response);
                    break;
                case ("/tasks/"):
                    response = gson.toJson(managers.getPrioritizedTasks());
                    sendText(exchange, response);
                    break;
            }
        }

        protected void sendText(HttpExchange h, String text) throws IOException {
            byte[] resp = text.getBytes(UTF_8);
            h.getResponseHeaders().add("Content-Type", "application/json");
            h.sendResponseHeaders(200, resp.length);
            h.getResponseBody().write(resp);
        }
    }

}
