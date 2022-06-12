package service;

import service.manager.Managers;
import service.task.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");
        var manager = Managers.getDefault();
        Task task1 = new Task("Помыть полы", "не забыть использовать средство", Status.NEW);
        Task task2 = new Task("Помыть окна", "не упасть из окна", Status.IN_PROGRESS);

        Epic epic = new Epic("Перезд", "Собрать всё и уехать", Status.NEW);
        manager.addEpic(epic);
        SubTask subTask = new SubTask("Собрать чемодан ", "нужен паспорт и трусы",
                Status.NEW, epic.getId());
        SubTask subTask2 = new SubTask("Обменять валюту", "лучше в сбере", Status.DONE, epic.getId());
        SubTask subTask3 = new SubTask("Напиться в баре", "лучше не пить", Status.IN_PROGRESS,
                epic.getId());
        Epic epic2 = new Epic("Встреча с друзьями",
                "Найти место и забронировать стол", Status.NEW);
        manager.addEpic(epic2);
        manager.addTask(task1);
        manager.addTask(task2);
        manager.addSub(subTask);
        manager.addSub(subTask2);
        manager.addSub(subTask3);

        //для проверки
        manager.getEpic(epic.getId());
        manager.getEpic(epic2.getId());
        manager.getSub(subTask.getId());
        manager.getSub(subTask2.getId());
        manager.getSub(subTask3.getId());
        manager.getTask(task1.getId());
        manager.getTask(task2.getId());
        System.out.println("Добавляем  " + manager.getHistory());
        manager.getEpic(epic.getId());
        manager.getTask(task1.getId());
        System.out.println("проверяем что она на последнем месте  " + manager.getHistory());
        // Managers.getDefault().deleteSub(subTask.getId());
        //Managers.getDefault().getSub(subTask.getId());
        manager.deleteEpic(epic.getId());

        System.out.println("проверяем эпик в чате ? " + manager.getHistory());
        /*manager.getTask(task2.getId());
        manager.getTask(task1.getId());*/
        manager.deleteEpic(epic2.getId());
        System.out.println("Добавляем  " + manager.getHistory());


    }
}
