/*2.
        Я переопределил метод, но тогда вопрос
        В условиях данной задачи, я сравниваю только status,
        зачем перегружать код, если я на данном этапе не планирую сравнивать?)
         */

import java.util.Objects;

public class SubTask extends Task {
    private int epicId;

    public SubTask(String name, String description, String status, int epicId) {
        super(name, description, status);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskName='" + getName() + '\'' +
                ", taskDescription='" + getDescription() + '\'' +
                ", taskId=" + getId() +
                ", taskStatus='" + getStatus() + '\'' +
                "epicId=" + epicId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SubTask subTask = (SubTask) o;
        return epicId == subTask.epicId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), epicId);
    }
}

