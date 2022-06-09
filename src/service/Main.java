package service;

import service.task.*;
/*Привет!))
* Оказывается было некоторое количество ошибок с прошлого спринта, вроде подправил всё)
* Сомнительное конечно задание, если честно я пока не до конца понял как ЭТО работает)
* Приятного просмотра и спасибо за уделенное время)
*
*/


public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");

        Task task1 = new Task("Помыть полы", "не забыть использовать средство", Status.NEW);
        Task task2 = new Task("Помыть окна", "не упасть из окна", Status.IN_PROGRESS);

        Epic epic = new Epic("Перезд", "Собрать всё и уехать", Status.NEW);
        Managers.getDefault().addEpic(epic);
        SubTask subTask = new SubTask("Собрать чемодан ", "нужен паспорт и трусы",
                Status.NEW, epic.getId());
        SubTask subTask2 = new SubTask("Обменять валюту", "лучше в сбере", Status.DONE, epic.getId());
        SubTask subTask3 = new SubTask("Напиться в баре", "лучше не пить", Status.IN_PROGRESS,
                epic.getId());
        Epic epic2 = new Epic("Встреча с друзьями",
                "Найти место и забронировать стол", Status.NEW);
        Managers.getDefault().addEpic(epic2);
        Managers.getDefault().addTask(task1);
        Managers.getDefault().addTask(task2);
        Managers.getDefault().addSub(subTask);
        Managers.getDefault().addSub(subTask2);
        Managers.getDefault().addSub(subTask3);

        //для проверки
        Managers.getDefault().getEpic(epic.getId());
        Managers.getDefault().getEpic(epic2.getId());
        Managers.getDefault().getSub(subTask.getId());
        Managers.getDefault().getSub(subTask2.getId());
        Managers.getDefault().getSub(subTask3.getId());
        Managers.getDefault().getTask(task1.getId());
        Managers.getDefault().getTask(task2.getId());
        System.out.println("Добавляем  " + Managers.getDefaultHistory().getHistory());
        Managers.getDefault().getEpic(epic.getId());
        System.out.println("проверяем что она на последнем месте  " + Managers.getDefaultHistory().getHistory());
        // Managers.getDefault().deleteSub(subTask.getId());
        //Managers.getDefault().getSub(subTask.getId());
        Managers.getDefault().deleteEpic(epic.getId());

        System.out.println("проверяем эпик в чате ? " + Managers.getDefaultHistory().getHistory());


    }
}
