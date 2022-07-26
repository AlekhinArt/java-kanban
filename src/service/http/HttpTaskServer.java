package service.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import service.manager.Managers;
import service.manager.TaskManager;
import service.task.Epic;
import service.task.SubTask;
import service.task.Task;
import service.typeAdapter.EpicAdapter;
import service.typeAdapter.LocalDateTimeAdapter;
import service.typeAdapter.SubTaskAdapter;
import service.typeAdapter.TaskAdapter;


import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.time.LocalDateTime;



import static java.nio.charset.StandardCharsets.UTF_8;


public class HttpTaskServer {

    private static final int PORT = 8080;
    private final HttpServer taskServer;
    private static final TaskManager taskManager = Managers.getDefault();
    public HttpTaskServer() throws IOException {
        taskServer = HttpServer.create();
        taskServer.bind(new InetSocketAddress(PORT), 0);
        taskServer.createContext("/task", new HelloHandler());

    }


    public static void main(String[] args) {

    }

    public void start() {
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
                                response = gson.toJson(taskManager.getTask(id));
                            } else {
                                response = gson.toJson(taskManager.getTasks());
                            }
                            sendText(exchange, response);
                            break;
                        case ("POST"):
                            taskManager.addTask(gson.fromJson(body, Task.class));
                            exchange.sendResponseHeaders(201, 0);
                            exchange.close();
                            break;
                        case ("DELETE"):
                            if (id >= 0) {
                                taskManager.deleteTask(id);
                            } else {
                                taskManager.deleteAllTasks();
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
                                response = gson.toJson(taskManager.getSub(id));
                            } else {
                                response = gson.toJson(taskManager.getSubs());
                            }
                            sendText(exchange, response);
                            break;
                        case ("POST"):
                            taskManager.addSub(gson.fromJson(body, SubTask.class));
                            exchange.sendResponseHeaders(201, 0);
                            exchange.close();
                            break;
                        case ("DELETE"):
                            if (id >= 0) {
                                taskManager.deleteSub(id);
                            } else {
                                taskManager.deleteAllSubTasks();
                            }
                            exchange.sendResponseHeaders(200, 0);
                            break;
                    }
                    break;
                case ("/tasks/epic/"):
                    switch (method) {
                        case ("GET"):
                            if (id >= 0) {
                                response = gson.toJson(taskManager.getEpic(id));
                            } else {
                                response = gson.toJson(taskManager.getEpics());
                            }
                            sendText(exchange, response);
                            break;
                        case ("POST"):
                            taskManager.addEpic(gson.fromJson(body, Epic.class));
                            exchange.sendResponseHeaders(201, 0);
                            exchange.close();
                            break;
                        case ("DELETE"):
                            if (id >= 0) {
                                taskManager.deleteEpic(id);
                            } else {
                                taskManager.deleteAllEpics();
                            }
                            exchange.sendResponseHeaders(200, 0);
                            break;
                    }
                    break;
                case ("/tasks/history"):
                    response = gson.toJson(taskManager.getHistory());
                    sendText(exchange, response);

                    break;
                case ("/tasks/subtask/epic/"):
                    response = gson.toJson(taskManager.getEpicSubtasks(id));
                    sendText(exchange, response);
                    break;
                case ("/tasks/"):
                    response = gson.toJson(taskManager.getPrioritizedTasks());
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
