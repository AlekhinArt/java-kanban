package service.manager;


import service.task.*;
import service.history.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


public class FileBackedTasksManager extends InMemoryTaskManager {
    private final File file;

    public FileBackedTasksManager(File file) {
        this.file = file;
    }

    public FileBackedTasksManager() {
        file = null;
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

    public Task getTaskWithOutSave(int id) {
        Task task = super.getTask(id);
        return task;
    }

    public Epic getEpicWithOutSave(int id) {
        Epic epic = super.getEpic(id);
        return epic;
    }

    public SubTask getSubWithOutSave(int id) {
        SubTask subTask = super.getSub(id);
        return subTask;
    }

    public static FileBackedTasksManager loadFromFile(File file) {

        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(file);
        String value;
        try {
            value = Files.readString(Path.of(String.valueOf(file)));
            if (value.isEmpty()) return fileBackedTasksManager;
        } catch (IOException e) {
            throw new ManagerSaveException(e.getMessage());
        }
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


    public String makeSign(Task task) {
        return task.getId() + "," + task.getType() + ","
                + task.getName() + "," + task.getStatus() + ","
                + task.getDescription() + ",";
    }

    public String makeTimeSign(Task task) {
        String sign = "";
        if (task.getStartTime() != null) {
            sign = task.toStringTime(task.getStartTime()) + ","
                    + task.getDuration() + "," + task.toStringTime(task.getEndTime()) + ",";
        }
        return sign;
    }

    public void save() {
        try (FileWriter writer = new FileWriter(file)) {
            writer.write("id,type,name,status,description,start time,duration,end time,epic\n");

            for (Task task : this.getEpics()) {
                writer.write(makeSign(task) + makeTimeSign(task) + "\n");
            }
            for (Task task : this.getTasks()) {
                writer.write(makeSign(task) + makeTimeSign(task) + "\n");
            }
            for (SubTask task : this.getSubs()) {
                writer.write(makeSign(task) + makeTimeSign(task) + task.getEpicId() + "\n");
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
        String startTime = "";
        int duration = 0;
        String endTime = " ";
        boolean time = false;

        String[] line = value.split(",");
        name = line[2];
        description = line[4];
        id = Integer.parseInt(line[0]);
        status = Status.valueOf(line[3]);
        type = Type.valueOf(line[1]);

        if (line.length > 5) {
            if (line.length > 6) {
                duration = Integer.parseInt(line[6]);
                startTime = line[5];
                endTime = line[7];
                time = true;

            }

        }

        switch (type) {
            case EPIC:
                Epic epic = new Epic(name, description, status);
                epic.setId(id);
                if (time) {
                    epic.setStartTime(epic.toFormatTime(startTime));
                    epic.setDuration(duration);
                    epic.setEndTime(epic.toFormatTime(endTime));
                }
                return epic;
            case SUBTASK:
                SubTask subTask = new SubTask(name, description, status, Integer.parseInt(line[line.length - 1]));
                subTask.setId(id);
                if (time) {
                    subTask.setStartTime(subTask.toFormatTime(startTime));
                    subTask.setDuration(duration);
                }
                return subTask;
            case TASK:
                Task task = new Task(name, description, status);
                task.setId(id);
                if (time) {
                    task.setStartTime(task.toFormatTime(startTime));
                    task.setDuration(duration);
                }
                return task;
        }
        return null;
    }

}
