package service.manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import service.kvs.KVTaskClient;
import service.task.*;
import service.typeAdapter.*;


import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class HTTPTaskManager extends FileBackedTasksManager {

    private static KVTaskClient kvTaskClient;
    private static String keyTask;
    private static String keySubTask;
    private static String keyEpic;
    private String keyHistory;

    public HTTPTaskManager(URL url) {
        kvTaskClient = new KVTaskClient(url);
        keyTask = "TASK";
        keySubTask = "SUBTASK";
        keyEpic = "EPIC";
        keyHistory = "HISTORY";

    }

    public HTTPTaskManager() {

    }

    @Override
    public List<Epic> getEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public ArrayList<SubTask> getEpicSubtasks(int id) {
        Epic epic = epics.get(id);
        List<Integer> subsId = new ArrayList<>(epic.getSubsId());
        ArrayList<SubTask> subsInEpic = new ArrayList<>();
        for (Integer subId : subsId) {
            subsInEpic.add(subs.get(subId));
        }
        return subsInEpic;
    }

    @Override
    public List<SubTask> getSubs() {
        return new ArrayList<>(subs.values());
    }

    @Override
    public List<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }


    public static HTTPTaskManager loadFromServer(URL url) throws IOException, InterruptedException {
        HTTPTaskManager httpTaskManager = new HTTPTaskManager(url);

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();
        ArrayList<Task> tasks = gson.fromJson(kvTaskClient.load(keyTask), new TypeToken<ArrayList<Task>>() {
        }.getType());
        if (tasks.size() > 0) {
            for (Task task : tasks) {
                httpTaskManager.addLoadTask(task);
            }
        }
        ArrayList<Epic> epics = gson.fromJson(kvTaskClient.load(keyEpic), new TypeToken<ArrayList<Epic>>() {
        }.getType());
        for (Epic epic : epics) {
            httpTaskManager.addLoadTask(epic);
        }
        ArrayList<SubTask> subs = gson.fromJson(kvTaskClient.load(keySubTask), new TypeToken<ArrayList<SubTask>>() {
        }.getType());
        for (SubTask subTask : subs) {
            httpTaskManager.addLoadTask(subTask);
        }
        ArrayList<Task> history = gson.fromJson(kvTaskClient.load(httpTaskManager.keyHistory), new TypeToken<ArrayList<Task>>() {
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
            kvTaskClient.put(keySubTask, gson.toJson(this.getSubs()));
            kvTaskClient.put(keyHistory, gson.toJson(Managers.getDefaultHistory().getHistory()));
            kvTaskClient.put(keyTask, gson.toJson(getTasks()));
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

