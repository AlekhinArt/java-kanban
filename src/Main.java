/*0.
Привет, с первых комментариев понял, что попал к классному ревьюеру( почему-то у меня каждый раз новый)
Спасибо, за уделенное время и внимательность проявленную к моему коду.

Вроде всё поправил, но не покидает ощущение недоделанности.
Вроде все методы рабочие, но при создании саба может выскочить ошибка,
если создавать эпик так  - manager.addEpic(new Epic("Перезд", "Собрать всё и уехать", "NEW"));

Если создать сначала саб, вылетает ошибка (т.к. эпика еще нет).
по поводу 2ого комментария, думаю на данном этапе нет смысла.
И вообще планировалось сделать статусы через enum, но не все понятно с ними(где объявлять,
        как изменять и как использовать)

Проверь пожалуйста еще раз, я думаю, я оставил недоработки либо то, что можно улучшить)        */
public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");
       Manager manager = new Manager();

        Task task1 = new Task("Помыть полы","не забыть использовать средство", "NEW");
        Task task2 = new Task("Помыть окна","не упасть из окна", "IN_PROGRESS");

        Epic epic = new Epic("Перезд", "Собрать всё и уехать", "NEW");
        manager.addEpic(epic);
        Sub sub = new Sub("Собрать чемодан ", "нужен паспорт и трусы", "NEW", epic.getId());
        Sub sub2 = new Sub("Обменять валюту", "лучше в сбере", "DONE", epic.getId());

        Epic epic3 = new Epic("Встреча с друзьями",
                "Найти место и забронировать стол", "NEW");
        manager.addEpic(epic3);
        Sub sub3 = new Sub("Позвонить в ресторан ", "стол на 6 человек", "DONE", epic3.getId());


        manager.addTask(task1);
        manager.addTask(task2);

        manager.addSub(sub);
        manager.addSub(sub2);
        manager.addSub(sub3);
        //для проверки
        System.out.println(" нулевой эпик " +manager.getAllSubOneEpic(0));
        manager.print();
        manager.getEpics();
        manager.deleteEpic(1);
        manager.getEpics();

    }
}
