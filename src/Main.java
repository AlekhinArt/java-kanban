public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");
       Manager manager = new Manager();

        Task task1 = new Task("Помыть полы","не забыть использовать средство",
                manager.createId(),"NEW");
        Task task2 = new Task("Помыть окна","не упасть из окна",
                manager.createId(),"IN_PROGRESS");

        EpicTask epicTask = new EpicTask("Перезд", "Собрать всё и уехать",
                manager.createId(), "NEW");
        SubTask subTask = new SubTask("Собрать чемодан ", "нужен паспорт и трусы",
                manager.createId(), "NEW", epicTask.getTaskId());
        SubTask subTask2 = new SubTask("Обменять валюту", "лучше в сбере",
                manager.createId(), "DONE", epicTask.getTaskId());

        EpicTask epicTask3 = new EpicTask("Встреча с друзьями",
                "Найти место и забронировать стол", manager.createId(), "NEW");
        SubTask subTask3 = new SubTask("Позвонить в ресторан ", "стол на 6 человек",
                manager.createId(), "DONE", epicTask3.getTaskId());


        manager.saveAndCreateTask(task1);
        manager.saveAndCreateTask(task2);

        manager.saveAndCreateEpic(epicTask);
        manager.saveAndCreateEpic(epicTask3);

        manager.saveAndCreateSubTask(subTask);
        manager.saveAndCreateSubTask(subTask2);
        manager.saveAndCreateSubTask(subTask3);

        manager.print();
        manager.getAllEpic();
        manager.removeOneEpic(5);
        manager.getAllEpic();





    }
}
