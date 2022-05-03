/*0.
Блин, действительно все проблемы из-за спешки
      Самое интересное, что сегодня наставник показал способ выполнения через интерфейс
      в принципе это то решение которое я и предполагал
      всё разложено по папочкам и по полочкам)
      Пока слишком мало опыта, что бы дойти до этого)
      */
/*4, 6 , 7
Я вполне адекватно отношусь к исправлениям и к критике)
Вариантов избежать полно, там просто словить NullPoint и в принципе все)
Но это не правильно, я считаю, можно сделать, что бы не стреляла эта ошибка)*/


public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");
        Manager manager = new Manager();

        Task task1 = new Task("Помыть полы","не забыть использовать средство", "NEW");
        Task task2 = new Task("Помыть окна","не упасть из окна", "IN_PROGRESS");

        Epic epic = new Epic("Перезд", "Собрать всё и уехать", "NEW");
        manager.addEpic(epic);
        SubTask subTask = new SubTask("Собрать чемодан ", "нужен паспорт и трусы", "NEW", epic.getId());
        SubTask subTask2 = new SubTask("Обменять валюту", "лучше в сбере", "DONE", epic.getId());

        Epic epic3 = new Epic("Встреча с друзьями",
                "Найти место и забронировать стол", "NEW");
        manager.addEpic(epic3);
        SubTask subTask3 = new SubTask("Позвонить в ресторан ", "стол на 6 человек", "DONE", epic3.getId());


        manager.addTask(task1);
        manager.addTask(task2);

        manager.addSub(subTask);
        manager.addSub(subTask2);
        manager.addSub(subTask3);
        //для проверки
        System.out.println(" нулевой эпик " +manager.getEpicSubtasks(0));
        manager.print();
        manager.getEpics();
        manager.deleteEpic(1);
        manager.getEpics();

    }
}
