package service.manager;


import service.task.*;
import service.history.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
//Добрый вечер!))
//Вроде эта штука заработала, но местами не совсем нравится реализация.
//


public class FileBackedTasksManager extends InMemoryTaskManager {

    public static FileBackedTasksManager fileBackOne = new FileBackedTasksManager();
    public static File file = new File("src\\service\\data\\tasks.CSV");
    public static FileBackedTasksManager fileBackTwo = new FileBackedTasksManager();


    public static void main(String[] args) throws IOException {
        var managerFile = Managers.getFileBacked();
        managerFile.addEpic(new Epic("Перезд", "Собрать всё и уехать", Status.NEW));
        managerFile.addTask(new Task("Помыть полы",
                "не забыть использовать средство", Status.NEW));
        managerFile.addTask(new Task("Помыть окна",
                "не упасть из окна", Status.IN_PROGRESS));
        managerFile.addSub(new SubTask("Собрать чемодан ", "нужен паспорт и трусы",
                Status.NEW, 0));
        managerFile.addSub(new SubTask("Обменять валюту",
                "лучше в сбере", Status.DONE, 0));
        managerFile.addSub(new SubTask("Напиться в баре",
                "лучше не пить", Status.IN_PROGRESS,
                0));

        loadFromFile(file);
        System.out.println(fileBackOne.getEpic(0));
        // System.out.println(fileBackOne.getSub(3));
        //System.out.println(sad.getEpics());
        System.out.println(fileBackTwo.getTask(1));
        fileBackTwo.deleteEpic(0);
        Managers.getFileBacked().deleteEpic(0);

    }

    @Override
    public void addEpic(Epic epic) {
        super.addEpic(epic);
        try {
            save();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void addSub(SubTask subTask) {
        super.addSub(subTask);
        try {
            save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addTask(Task task) {
        super.addTask(task);
        try {
            save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateEpic(Epic epic, int id) {
        super.updateEpic(epic, id);
        try {
            save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateSub(SubTask subTask, int id) {
        super.updateSub(subTask, id);
        try {
            save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateTask(Task task, int id) {
        super.updateTask(task, id);
        try {
            save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAllEpics() {
        super.deleteAllEpics();
        try {
            save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAllSubTasks() {
        super.deleteAllSubTasks();
        try {
            save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAllTasks() {
        super.deleteAllTasks();
        try {
            save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Epic> getEpics() {
        return super.getEpics();

    }

    @Override
    public ArrayList<Task> getEpicSubtasks(int id) {
        return super.getEpicSubtasks(id);
    }

    @Override
    public ArrayList<SubTask> getSubs() {
        return super.getSubs();
    }

    @Override
    public ArrayList<Task> getTasks() {
        return super.getTasks();
    }

    @Override
    public void deleteEpic(int id) {
        super.deleteEpic(id);
        try {
            save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteSub(int id) {
        super.deleteSub(id);
        try {
            save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteTask(int id) {
        super.deleteTask(id);
        try {
            save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Epic getEpic(int id) {
        Managers.getDefaultHistory().add(super.getEpic(id));
        try {
            save();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return super.getEpic(id);
    }

    @Override
    public SubTask getSub(int id) {
        Managers.getDefaultHistory().add(super.getSub(id));
        try {
            save();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return super.getSub(id);
    }

    @Override
    public Task getTask(int id) {
        Managers.getDefaultHistory().add(super.getTask(id));
        try {
            save();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return super.getTask(id);
    }

    public static FileBackedTasksManager loadFromFile(File file) {
        try {
            fileBackOne.fromString(Files.readString(Path.of(String.valueOf(file))));
            fileBackTwo.fromString(Files.readString(Path.of(String.valueOf(file))));
        } catch (NoSuchFileException e) {

            System.out.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public String makeSign(Task task) {
        return task.getId() + "," + task.getType() + ","
                + task.getName() + "," + task.getStatus() + ","
                + task.getDescription() + ",";
    }

    public void save() throws IOException {
        try (FileWriter writer = new FileWriter("src\\service\\data\\tasks.CSV")) {
            writer.write("id,type,name,status,description,epic\n");

            for (Task task : this.getEpics()) {
                writer.write(makeSign(task) + "\n");
            }
            for (Task task : this.getTasks()) {
                writer.write(makeSign(task) + "\n");
            }
            for (Task task : this.getSubs()) {
                writer.write(makeSign(task) + task.getEpicId() + "\n");
            }

            writer.write("\n");
            writer.write(toString(Managers.getDefaultHistory()));
        } catch (IOException e) {

            throw new RuntimeException(e);
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
        Status status = Status.NEW;
        int id;
        int i = 1;
        String[] lines = value.split("\n");
        try {
            while (!lines[i].isEmpty()) {
                String[] line = lines[i].split(",");
                i++;
                name = line[2];
                description = line[4];
                id = Integer.parseInt(line[0]);
                switch (line[3]) {
                    case "IN_PROGRESS":
                        status = Status.IN_PROGRESS;
                        break;
                    case "NEW":
                        status = Status.NEW;
                        break;
                    case "DONE":
                        status = Status.DONE;
                        break;
                }
                switch (line[1]) {
                    case "EPIC":
                        Epic epic = new Epic(name, description, status);
                        super.addEpic(epic);
                        epic.setId(id);
                        break;
                    case "SUBTASK":
                        SubTask subTask = new SubTask(name, description, status, Integer.parseInt(line[5]));
                        super.addSub(subTask);
                        subTask.setId(id);
                        break;
                    case "TASK":
                        Task task = new Task(name, description, status);
                        super.addTask(task);
                        task.setId(id);
                        break;
                }
            }
            List<Integer> history = fromStringHistory(lines[lines.length - 1]);
            for (Integer idHistory : history) {
                if (getEpic(idHistory) != null) {
                    Managers.getDefaultHistory().add(getEpic(idHistory));
                }
                if (getSub(idHistory) != null) {
                    Managers.getDefaultHistory().add(getSub(idHistory));
                }
                if (getTask(idHistory) != null) {
                    Managers.getDefaultHistory().add(getTask(idHistory));
                }
            }
        } catch (IndexOutOfBoundsException e) {
            //throw new IndexOutOfBoundsException();

        }


        return null;
    }


}
