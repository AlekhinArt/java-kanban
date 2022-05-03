import java.util.Objects;

/*1.
Да, с такими наименованиями проще читать)*/
public class Task {
   private String task;
   private String description;
   private int id;
   private String status;

    public Task(String task, String description, String status) {
        this.task = task;
        this.description = description;
        this.status = status;
    }

    public Task(String task, String description, int id) {
        this.task = task;
        this.description = description;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTask() {
        return task;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskName='" + task + '\'' +
                ", taskDescription='" + description + '\'' +
                ", Id=" + id +
                ", taskStatus='" + status + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task1 = (Task) o;
        return id == task1.id && Objects.equals(task, task1.task) && Objects.equals(description, task1.description) && Objects.equals(status, task1.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(task, description, id, status);
    }
}
