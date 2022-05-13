
package service;

import service.task.*;

public class Main {
    /*
    Спасибо за уделенное время)

    поправил всё кроме 2ух моментов, вопрос по Epic и по истории запросов
    */

    public static void main(String[] args) {
        System.out.println("Поехали!");

        Task task1 = new Task("Помыть полы", "не забыть использовать средство", Status.NEW);
        Task task2 = new Task("Помыть окна", "не упасть из окна", Status.IN_PROGRESS);

        Epic epic = new Epic("Перезд", "Собрать всё и уехать", Status.NEW);
        Managers.getDefault().addEpic(epic);
        SubTask subTask = new SubTask("Собрать чемодан ", "нужен паспорт и трусы",
                Status.NEW, epic.getId());
        SubTask subTask2 = new SubTask("Обменять валюту", "лучше в сбере", Status.DONE, epic.getId());
        Epic epic3 = new Epic("Встреча с друзьями",
                "Найти место и забронировать стол", Status.NEW);
        Managers.getDefault().addEpic(epic3);
        SubTask subTask3 = new SubTask("Позвонить в ресторан ", "стол на 6 человек",
                Status.NEW, epic3.getId());

        Managers.getDefault().addTask(task1);
        Managers.getDefault().addTask(task2);
        Managers.getDefault().addSub(subTask);
        Managers.getDefault().addSub(subTask2);
        Managers.getDefault().addSub(subTask3);

        //для проверки
        System.out.println(" нулевой эпик " + Managers.getDefault().getEpicSubtasks(0));
        Managers.getDefault().getEpics();
        Managers.getDefault().getEpics();
        Managers.getDefault().getEpic(0);
        Managers.getDefault().getEpic(0);
        Managers.getDefault().getEpic(0);
        System.out.println(Managers.getDefaultHistory());
        Managers.getDefault().getEpic(0);
        Managers.getDefault().getEpic(0);
        Managers.getDefault().getEpic(0);
        System.out.println(Managers.getDefaultHistory());
        Managers.getDefault().getEpic(0);
        Managers.getDefault().getEpic(0);
        Managers.getDefault().getEpic(0);
        Managers.getDefault().getEpic(0);
        Managers.getDefault().getEpic(epic3.getId());
        System.out.println((Managers.getDefaultHistory()));
        Managers.getDefault().getEpic(epic3.getId());
        System.out.println((Managers.getDefaultHistory()));

    }
}
