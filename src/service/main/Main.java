package service.main;


import service.http.HttpTaskServer;
import service.kvs.*;
import service.manager.HTTPTaskManager;
import service.manager.Managers;
import service.manager.TaskManager;
import service.task.Epic;
import service.task.Status;
import service.task.Task;

import java.io.IOException;
import java.net.URI;
import java.net.URL;

public class Main {

    public static void main(String[] args) throws IOException {

        KVServer kvServer = new KVServer();

        HttpTaskServer asas = new HttpTaskServer();
        kvServer.start();
        asas.start();
        Managers.getDefault().addTask(new Task("Test TASK 1", "Test addNew description", Status.NEW));


    }

}
