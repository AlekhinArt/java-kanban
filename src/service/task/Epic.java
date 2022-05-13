package service.task;

import java.util.ArrayList;

public class Epic extends Task {

    private ArrayList<SubTask> subTasks = new ArrayList<>();
    /*
    Переделать не составит никакого труда, но подскажи пожалуйста, почему так лучше?)
    когда я писал это, я  руководствовался тем, что со списком подзадач проще работать, чем с их id
    т.е. если допустить, что у нас бесконечное множество подзадач, то что бы найти нужные нам,
    придется перебрать весь список этих подзадач, имея же ссылки на них, мы сможем намного быстрее к ним обратиться
    Так же при необходимости мы всегда можем получить список этих задач, не перебирая все данные,
    а если будет необходиомть изменить подзадачу выдернув её id, легко перейти и на неё саму.

    */

    public Epic(String name, String description, Status status) {
        super(name, description, status);
    }

    public ArrayList<SubTask> getSubs() {
        return subTasks;
    }

    public void setSubs(ArrayList<SubTask> subTasks) {
        this.subTasks = subTasks;
    }


}







