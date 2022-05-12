/* Привет)
    Почему-то у меня каждый раз новый ревьеюер, думаю это даже хорошо)
    Вроде всё проверено и работает, но не покидает ощущение, что что-то не так))
    Разложил по папочкам, проверь пожалуйста корректность заодно)

 */
package service;

import service.manager.*;
import service.task.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");
        TaskManager inMemoryTaskManager = new InMemoryTaskManager();


        Task task1 = new Task("Помыть полы","не забыть использовать средство", Status.NEW);
        Task task2 = new Task("Помыть окна","не упасть из окна", Status.IN_PROGRESS);

        Epic epic = new Epic("Перезд", "Собрать всё и уехать", Status.NEW);
        inMemoryTaskManager.addEpic(epic);
        SubTask subTask = new SubTask("Собрать чемодан ", "нужен паспорт и трусы",
                Status.NEW, epic.getId());
        SubTask subTask2 = new SubTask("Обменять валюту", "лучше в сбере", Status.DONE, epic.getId());

        Epic epic3 = new Epic("Встреча с друзьями",
                "Найти место и забронировать стол", Status.NEW);
        inMemoryTaskManager.addEpic(epic3);
        SubTask subTask3 = new SubTask("Позвонить в ресторан ", "стол на 6 человек",
                Status.NEW, epic3.getId());


        inMemoryTaskManager.addTask(task1);
        inMemoryTaskManager.addTask(task2);

        inMemoryTaskManager.addSub(subTask);
        inMemoryTaskManager.addSub(subTask2);
        inMemoryTaskManager.addSub(subTask3);


        //для проверки
        System.out.println(" нулевой эпик " + inMemoryTaskManager.getEpicSubtasks(0));
        inMemoryTaskManager.print();
        inMemoryTaskManager.getEpics();
        inMemoryTaskManager.getEpics();
        inMemoryTaskManager.getEpic(0);
        inMemoryTaskManager.getEpic(0);
        inMemoryTaskManager.getEpic(0);
        System.out.println(Managers.getDefaultHistory());
        inMemoryTaskManager.getEpic(0);
        inMemoryTaskManager.getEpic(0);
        inMemoryTaskManager.getEpic(0);
        System.out.println(Managers.getDefaultHistory());
        inMemoryTaskManager.getEpic(0);
        inMemoryTaskManager.getEpic(0);
        inMemoryTaskManager.getEpic(0);
        inMemoryTaskManager.getEpic(0);
        inMemoryTaskManager.getEpic(epic3.getId());
        System.out.println((Managers.getDefaultHistory()));
        inMemoryTaskManager.getEpic(epic3.getId());
        System.out.println((Managers.getDefaultHistory()));


    }
}
