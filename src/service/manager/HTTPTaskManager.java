package service.manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import service.kvs.KVTaskClient;
import service.task.*;
import service.typeAdapter.*;


import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class HTTPTaskManager extends FileBackedTasksManager {

    private KVTaskClient kvTaskClient;
    private URL url;
    private static String keyTask;
    private static String keySubTask;
    private static String keyEpic;
    private String keyHistory;

    public HTTPTaskManager(URL url) {
        this.url = url;
        kvTaskClient = new KVTaskClient(url);
        keyTask = "TASK";
        keySubTask = "SUBTASK";
        keyEpic = "EPIC";
        keyHistory = "HISTORY";
        try {
            kvTaskClient.registration();
        } catch (IOException | InterruptedException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public HTTPTaskManager() {

    }

    public static HTTPTaskManager loadFromServer(URL url) throws IOException, InterruptedException, URISyntaxException {
        HTTPTaskManager httpTaskManager = new HTTPTaskManager(url);
        httpTaskManager.kvTaskClient.registration();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();

        String jsonTask = gson.fromJson(httpTaskManager.kvTaskClient.load(keyTask), String.class);
        System.out.println(jsonTask);
        ArrayList<Task> tasks = gson.fromJson(jsonTask, new TypeToken<ArrayList<Task>>() {
        }.getType());
        if (tasks.size() > 0) {
            for (Task task : tasks) {
                httpTaskManager.addLoadTask(task);
            }
        }
        String jsonEpic = gson.fromJson(httpTaskManager.kvTaskClient.load(keyEpic), String.class);
        ArrayList<Epic> epics = gson.fromJson(jsonEpic, new TypeToken<ArrayList<Epic>>() {
        }.getType());
        for (Epic epic : epics) {
            httpTaskManager.addLoadTask(epic);
        }
        String jsonSub = gson.fromJson(httpTaskManager.kvTaskClient.load(keySubTask), String.class);
        ArrayList<SubTask> subs = gson.fromJson(jsonSub, new TypeToken<ArrayList<SubTask>>() {
        }.getType());
        for (SubTask subTask : subs) {
            httpTaskManager.addLoadTask(subTask);
        }
        String jsonHistory = gson.fromJson(httpTaskManager.kvTaskClient.load(httpTaskManager.keyHistory), String.class);
        ArrayList<Task> history = gson.fromJson(jsonHistory, new TypeToken<ArrayList<Task>>() {
        }.getType());
        for (Task task : history) {
            if (httpTaskManager.getEpicWithOutSave(task.getId()) != null) {
                httpTaskManager.historyManager.add(httpTaskManager.getEpicWithOutSave(task.getId()));
            }
            if (httpTaskManager.getSubWithOutSave(task.getId()) != null) {
                httpTaskManager.historyManager.add(httpTaskManager.getSubWithOutSave(task.getId()));
            }
            if (httpTaskManager.getTaskWithOutSave(task.getId()) != null) {
                httpTaskManager.historyManager.add(httpTaskManager.getTaskWithOutSave(task.getId()));
            }
        }
        return httpTaskManager;
    }

    public void save() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .registerTypeAdapter(Epic.class, new EpicAdapter())
                .registerTypeAdapter(SubTask.class, new SubTaskAdapter())
                .registerTypeAdapter(Task.class, new TaskAdapter())
                .create();
        try {
            kvTaskClient.put(keyEpic, gson.toJson(getEpics()));
            kvTaskClient.put(keyTask, gson.toJson(getTasks()));
            kvTaskClient.put(keySubTask, gson.toJson(this.getSubs()));
            kvTaskClient.put(keyHistory, gson.toJson(Managers.getDefaultHistory().getHistory()));
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}

