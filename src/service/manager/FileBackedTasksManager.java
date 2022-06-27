package service.manager;


import service.task.*;
import service.history.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
// Доброго времени суток вроде поправил всё)
//Признаться если, благодоря твоим комментариям, я намного лучше понял, что требуется сделать
//Спасибо!)

public class FileBackedTasksManager extends InMemoryTaskManager {
    private final File file;

    public FileBackedTasksManager(File file) {
        this.file = file;
    }

    public static void main(String[] args) throws IOException {
        File fileForExample = new File("bin\\tasks.CSV");
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(fileForExample);
        fileBackedTasksManager.addEpic(new Epic("Перезд", "Собрать всё и уехать", Status.NEW));
        fileBackedTasksManager.addEpic(new Epic("Перезд", "Собрать всё и уехать", Status.NEW));
        fileBackedTasksManager.addTask(new Task("Помыть полы",
                "не забыть использовать средство", Status.NEW));
        fileBackedTasksManager.addTask(new Task("Помыть окна",
                "не упасть из окна", Status.IN_PROGRESS));
        fileBackedTasksManager.addSub(new SubTask("Собрать чемодан ", "нужен паспорт и трусы",
                Status.NEW, 0));
        fileBackedTasksManager.addSub(new SubTask("Обменять валюту",
                "лучше в сбере", Status.DONE, 0));
        fileBackedTasksManager.addSub(new SubTask("Напиться в баре",
                "лучше не пить", Status.IN_PROGRESS,
                0));
        System.out.println(fileBackedTasksManager.getEpic(0));
        fileBackedTasksManager.getSub(4);


        File fileForExmaple2 = new File("bin\\tasks2.CSV");
        FileBackedTasksManager fileBackedTasksManager2 = loadFromFile(fileForExmaple2);
        //System.out.println(fileBackedTasksManager2.getTask(3));
        fileBackedTasksManager.getSub(5);
    }

    @Override
    public void addEpic(Epic epic) {
        super.addEpic(epic);
        save();
    }

    @Override
    public void addSub(SubTask subTask) {
        super.addSub(subTask);
        save();
    }

    @Override
    public void addTask(Task task) {
        super.addTask(task);
        save();
    }

    @Override
    public void updateEpic(Epic epic, int id) {
        super.updateEpic(epic, id);
        save();
    }

    @Override
    public void updateSub(SubTask subTask, int id) {
        super.updateSub(subTask, id);
        save();
    }

    @Override
    public void updateTask(Task task, int id) {
        super.updateTask(task, id);
        save();
    }

    @Override
    public void deleteAllEpics() {
        super.deleteAllEpics();
        save();
    }

    @Override
    public void deleteAllSubTasks() {
        super.deleteAllSubTasks();
        save();
    }

    @Override
    public void deleteAllTasks() {
        super.deleteAllTasks();
        save();
    }

    @Override
    public void deleteEpic(int id) {
        super.deleteEpic(id);
        save();
    }

    @Override
    public void deleteSub(int id) {
        super.deleteSub(id);
        save();
    }

    @Override
    public void deleteTask(int id) {
        super.deleteTask(id);
        save();
    }

    @Override
    public Epic getEpic(int id) {
        Epic task = super.getEpic(id);
        save();
        return task;
    }

    @Override
    public SubTask getSub(int id) {
        SubTask task = super.getSub(id);
        save();
        return task;
    }

    @Override
    public Task getTask(int id) {
        Task task = super.getTask(id);
        save();
        return task;
    }

    // Добавил дополнительный метод, думаю так можно избежать повторного сохранения
    public void addLoadTask(Task task) {
        switch (task.getType()) {
            case EPIC:
                super.addEpic((Epic) task);
                break;
            case SUBTASK:
                super.addSub((SubTask) task);
                break;
            case TASK:
                super.addTask(task);
                break;
        }
    }

    public static FileBackedTasksManager loadFromFile(File file) throws IOException {
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(file);
        String value = Files.readString(Path.of(String.valueOf(file)));
        if (value.isEmpty()) return fileBackedTasksManager;
        String[] lines = value.split("\n");
        int i = 1;
        while (!lines[i].isEmpty()) {
            Task task = fileBackedTasksManager.fromString(lines[i]);
            ++i;
            fileBackedTasksManager.addLoadTask(task);
            if (lines.length == i) break;
        }
        if (i == lines.length) {
            return fileBackedTasksManager;
        } else {
            List<Integer> history = fromStringHistory(lines[lines.length - 1]);
            for (Integer idHistory : history) {
                if (fileBackedTasksManager.getEpic(idHistory) != null) {
                    fileBackedTasksManager.historyManager.add(fileBackedTasksManager.getEpic(idHistory));
                }
                if (fileBackedTasksManager.getSub(idHistory) != null) {
                    fileBackedTasksManager.historyManager.add(fileBackedTasksManager.getSub(idHistory));
                }
                if (fileBackedTasksManager.getTask(idHistory) != null) {
                    fileBackedTasksManager.historyManager.add(fileBackedTasksManager.getTask(idHistory));
                }
            }
        }
        return fileBackedTasksManager;
    }

    //метод сохранения задачи в строку
    public String makeSign(Task task) {
        return task.getId() + "," + task.getType() + ","
                + task.getName() + "," + task.getStatus() + ","
                + task.getDescription() + ",";
    }

    //сохранение в файл
    public void save() {
        try (FileWriter writer = new FileWriter(file)) {
            writer.write("id,type,name,status,description,epic\n");

            for (Task task : this.getEpics()) {
                writer.write(makeSign(task) + "\n");
            }
            for (Task task : this.getTasks()) {
                writer.write(makeSign(task) + "\n");
            }
            for (SubTask task : this.getSubs()) {
                writer.write(makeSign(task) + task.getEpicId() + "\n");
            }

            writer.write("\n");
            writer.write(toString(historyManager));
        } catch (IOException e) {
            throw new ManagerSaveException(e.getMessage());
        }
    }

    public static String toString(HistoryManager manager) {
        List<String> historyId = new ArrayList<>();
        for (Task task : manager.getHistory()) {
            historyId.add(String.valueOf(task.getId()));
        }
        return String.join(",", historyId);
    }

    public static List<Integer> fromStringHistory(String value) {
        String[] line = value.split(",");
        List<Integer> history = new ArrayList<>();
        for (String s : line) {
            history.add(Integer.parseInt(s));
        }
        return history;
    }

    public Task fromString(String value) {
        String name;
        String description;
        Status status;
        Type type;
        int id;

        String[] line = value.split(",");
        name = line[2];
        description = line[4];
        id = Integer.parseInt(line[0]);
        status = Status.valueOf(line[3]);
        type = Type.valueOf(line[1]);
        switch (type) {
            case EPIC:
                Epic epic = new Epic(name, description, status);
                epic.setId(id);
                return epic;
            case SUBTASK:
                SubTask subTask = new SubTask(name, description, status, Integer.parseInt(line[5]));
                subTask.setId(id);
                return subTask;
            case TASK:
                Task task = new Task(name, description, status);
                task.setId(id);
                return task;
        }
        return null;
    }

}
