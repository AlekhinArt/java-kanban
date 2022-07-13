package service.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.manager.InMemoryTaskManager;
import service.manager.TaskManager;
import service.task.Epic;
import service.task.Status;
import service.task.SubTask;
import service.task.Task;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskManagerTest <T extends  TaskManager> {

    TaskManager taskManager = new InMemoryTaskManager();
    Epic testEpicOne;
    Epic testEpicTwo;
    int epicId;
    SubTask testSubTaskOne;
    int subId;
    SubTask testSubTaskTwo;
    int testSubId;
    Task testTaskOne;
    int taskId;
    Task testTaskTwo;


    @BeforeEach
    public void adllAll() {
        testEpicOne = new Epic("Test addNewEpic", "Test addNewEpic description", Status.NEW);
        epicId = testEpicOne.getId();
        testSubTaskOne = new SubTask("Test addNewSubTask", "Test addNewSubTask description",
                Status.NEW, testEpicOne.getId());
        subId = testSubTaskOne.getId();
        testSubTaskTwo = new SubTask("Test addNewSubTask", "Test addNewSubTask description",
                Status.DONE, testEpicOne.getId());
        testSubId = testSubTaskOne.getId();
        testTaskOne = new Task("Test addNewTask", "Test addNewTask description", Status.NEW);
        taskId = testTaskOne.getId();
        testEpicTwo = new Epic("Test addNewEpic", "Test addNewEpic description", Status.DONE);
        testTaskTwo = new Task("Test addNew", "Test addNew description", Status.DONE);
    }

    @Test
    void addEpic() {
        taskManager.addEpic(testEpicOne);

        final Epic savedTask = taskManager.getEpic(epicId);

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(testEpicOne, savedTask, "Задачи не совпадают.");

        final List<Epic> tasks = taskManager.getEpics();

        assertNotNull(tasks, "Задачи на возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(testEpicOne, tasks.get(0), "Задачи не совпадают.");
    }

    @Test
    void addEpicWithEmptyTaskList() {
        final List<Epic> tasks = taskManager.getEpics();

        assertNotNull(tasks, "Задачи на возвращаются.");
        assertEquals(0, tasks.size(), "Неверное количество задач.");
    }

    @Test
    void addEpicWithNullTask() {
        testEpicOne = null;
        taskManager.addEpic(testEpicOne);
        final List<Epic> tasks = taskManager.getEpics();

        assertNotNull(tasks, "Задачи на возвращаются.");
        assertEquals(0, tasks.size(), "Неверное количество задач.");
    }

    @Test
    void addSub() {
        taskManager.addEpic(testEpicOne);
        taskManager.addSub(testSubTaskOne);
        final int subId = testSubTaskOne.getId();

        final SubTask savedTask = taskManager.getSub(subId);

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(testSubTaskOne, savedTask, "Задачи не совпадают.");

        final List<SubTask> tasks = taskManager.getSubs();

        assertNotNull(tasks, "Задачи на возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(testSubTaskOne, tasks.get(0), "Задачи не совпадают.");
    }

    @Test
    void addSubWithEmptyTaskList() {
        final List<SubTask> subs = taskManager.getSubs();

        assertNotNull(subs, "Задачи на возвращаются.");
        assertEquals(0, subs.size(), "Неверное количество задач.");
    }

    @Test
    void addSubTaskWithNull() {
        testSubTaskOne = null;
        taskManager.addSub(testSubTaskOne);
        final List<SubTask> subs = taskManager.getSubs();

        assertNotNull(subs, "Задачи на возвращаются.");
        assertEquals(0, subs.size(), "Неверное количество задач.");
    }

    @Test
    void addTask() {
        taskManager.addTask(testTaskOne);
        final int taskId = testTaskOne.getId();

        final Task savedTask = taskManager.getTask(taskId);

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(testTaskOne, savedTask, "Задачи не совпадают.");

        final List<Task> tasks = taskManager.getTasks();

        assertNotNull(tasks, "Задачи на возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(testTaskOne, tasks.get(0), "Задачи не совпадают.");
    }

    @Test
    void addTaskWithEmptyTaskList() {
        final List<Task> tasks = taskManager.getTasks();

        assertNotNull(tasks, "Задачи на возвращаются.");
        assertEquals(0, tasks.size(), "Неверное количество задач.");
    }

    @Test
    void addTaskWithNull() {
        testTaskOne = null;
        taskManager.addTask(testTaskOne);
        final List<Task> tasks = taskManager.getTasks();

        assertNotNull(tasks, "Задачи на возвращаются.");
        assertEquals(0, tasks.size(), "Неверное количество задач.");
    }

    @Test
    void updateEpic() {
        taskManager.addEpic(testEpicOne);
        final int epicId = testEpicOne.getId();
        final Epic savedEpicAfter = taskManager.getEpic(epicId);

        taskManager.updateEpic(testEpicTwo, epicId);
        final Epic savedEpicBefore = taskManager.getEpic(epicId);

        assertNotNull(savedEpicBefore, "Задача не найдена.");
        assertNotEquals(savedEpicAfter, savedEpicBefore, "Задачи не изменены.");
    }

    @Test
    void updateEpicWithNull() {
        taskManager.addEpic(testEpicOne);
        final int epicId = testEpicOne.getId();
        final Epic savedEpicAfter = taskManager.getEpic(epicId);

        taskManager.updateEpic(null, epicId);
        final Epic savedEpicBefore = taskManager.getEpic(epicId);

        assertEquals(savedEpicAfter, savedEpicBefore, "Задачи не изменены.");
    }

    @Test
    void updateSub() {
        taskManager.addEpic(testEpicOne);
        taskManager.addSub(testSubTaskOne);
        final int subId = testSubTaskOne.getId();
        final SubTask savedTaskBefore = taskManager.getSub(subId);

        taskManager.updateSub(testSubTaskTwo, subId);
        final SubTask savedTaskAfter = taskManager.getSub(subId);

        assertNotNull(savedTaskAfter, "Задача не найдена.");
        assertNotEquals(savedTaskBefore, savedTaskAfter, "Задачи не изменены.");
    }

    @Test
    void updateSubWithNull() {
        taskManager.addEpic(testEpicOne);
        taskManager.addSub(testSubTaskOne);
        final int subId = testSubTaskOne.getId();
        final SubTask savedTaskBefore = taskManager.getSub(subId);

        taskManager.updateSub(null, subId);
        final SubTask savedTaskAfter = taskManager.getSub(subId);

        assertEquals(savedTaskBefore, savedTaskAfter, "Задачи не изменены.");
    }

    @Test
    void updateTask() {
        taskManager.addTask(testTaskOne);
        final int taskId = testTaskOne.getId();
        final Task savedTaskBefore = taskManager.getTask(taskId);

        taskManager.updateTask(testTaskTwo, taskId);
        final Task savedTaskAfter = taskManager.getTask(taskId);

        assertNotNull(savedTaskAfter, "Задача не найдена.");
        assertNotEquals(savedTaskBefore, savedTaskAfter, "Задачи не изменены.");
    }

    @Test
    void updateTaskWithNull() {
        taskManager.addTask(testTaskOne);
        final int taskId = testTaskOne.getId();
        final Task savedTaskBefore = taskManager.getTask(taskId);

        taskManager.updateTask(null, taskId);
        final Task savedTaskAfter = taskManager.getTask(taskId);

        assertEquals(savedTaskBefore, savedTaskAfter, "Задачи не изменены.");
    }

    @Test
    void deleteAllSubTasks() {
        taskManager.addEpic(testEpicOne);
        taskManager.addSub(testSubTaskOne);
        final int subId = testSubTaskOne.getId();
        taskManager.addSub(testSubTaskTwo);
        final int subId2 = testSubTaskTwo.getId();

        assertNotNull(testSubTaskOne, "Задача не добавлена.");
        assertNotNull(testSubTaskTwo, "Задача не добавлена.");

        taskManager.deleteAllSubTasks();

        testSubTaskOne = taskManager.getSub(subId);
        testSubTaskTwo = taskManager.getSub(subId2);

        assertNull(testSubTaskOne, "Задача не удалена");
        assertNull(testSubTaskTwo, "Задача не удалена");
    }

    @Test
    void deleteAllSubTasksWithEmptyTaskList() {
        taskManager.deleteAllSubTasks();
        List<SubTask> subTasks = new ArrayList<>();
        assertEquals(taskManager.getSubs(), subTasks, "Пустой список");
    }

    @Test
    void deleteAllEpics() {
        taskManager.addEpic(testEpicOne);
        final int epic1Id = testEpicOne.getId();
        taskManager.addEpic(testEpicTwo);
        final int epic2Id = testEpicTwo.getId();

        assertNotNull(testEpicOne, "Задача не добавлена.");
        assertNotNull(testEpicTwo, "Задача не добавлена.");

        taskManager.deleteAllEpics();
        testEpicOne = taskManager.getEpic(epic1Id);
        testEpicTwo = taskManager.getEpic(epic2Id);

        assertNull(testEpicOne, "Задача не удалена");
        assertNull(testEpicTwo, "Задача не удалена");
    }

    @Test
    void deleteAllEpicsWithEmptyTaskList() {
        taskManager.deleteAllEpics();
        List<Epic> epics = new ArrayList<>();
        assertEquals(taskManager.getEpics(), epics, "Пустой список");
    }

    @Test
    void deleteAllTasks() {
        taskManager.addTask(testTaskOne);
        final int task1Id = testTaskOne.getId();
        taskManager.addTask(testTaskTwo);
        final int task2Id = testTaskTwo.getId();

        assertNotNull(testTaskOne, "Задача не добавлена.");
        assertNotNull(testTaskTwo, "Задача не добавлена.");

        taskManager.deleteAllTasks();
        testTaskOne = taskManager.getTask(task1Id);
        testTaskTwo = taskManager.getTask(task2Id);

        assertNull(testTaskOne, "Задача не удалена");
        assertNull(testTaskTwo, "Задача не удалена");
    }
    @Test
    void deleteAllTasksWithEmptyTaskList() {
        taskManager.deleteAllTasks();
        List<Task> tasks = new ArrayList<>();
        assertEquals(taskManager.getSubs(), tasks, "Пустой список");
    }

    @Test
    void getEpics() {

        taskManager.addEpic(testEpicOne);
        taskManager.addEpic(testEpicTwo);
        List<Epic> testList = new ArrayList<>();
        testList.add(testEpicOne);
        testList.add(testEpicTwo);

        List<Epic> testList2 = taskManager.getEpics();

        assertEquals(testList, testList2, "Список задач не верен");
    }

    @Test
    void getEpicSubtasks() {
        taskManager.addEpic(testEpicOne);
        final int epicId = testEpicOne.getId();

        taskManager.addSub(testSubTaskOne);
        taskManager.addSub(testSubTaskTwo);
        List<SubTask> testList = new ArrayList<>();
        testList.add(testSubTaskOne);
        testList.add(testSubTaskTwo);

        ArrayList<SubTask> testList2 = taskManager.getEpicSubtasks(epicId);

        assertEquals(testList, testList2, "Список задач не верен");
    }

    @Test
    void getTasks() {
        taskManager.addTask(testTaskOne);
        taskManager.addTask(testTaskTwo);
        List<Task> testList = new ArrayList<>();
        testList.add(testTaskOne);
        testList.add(testTaskTwo);

        List<Task> testList2 = taskManager.getTasks();

        assertEquals(testList, testList2, "Список задач не верен");
    }

    @Test
    void getSubs() {
        taskManager.addEpic(testEpicOne);
        taskManager.addSub(testSubTaskOne);
        taskManager.addSub(testSubTaskTwo);
        List<SubTask> testList = new ArrayList<>();
        testList.add(testSubTaskOne);
        testList.add(testSubTaskTwo);

        List<SubTask> testList2 = taskManager.getSubs();

        assertEquals(testList, testList2, "Список задач не верен");
    }

    @Test
    void deleteEpic() {
        taskManager.addEpic(testEpicOne);
        final int epic1Id = testEpicOne.getId();

        assertNotNull(testEpicOne, "Задача не добавлена.");

        taskManager.deleteEpic(epic1Id);
        testEpicOne = taskManager.getEpic(epic1Id);

        assertNull(testEpicOne, "Задача не удалена");
    }

    @Test
    void deleteEpicWithIncorrect() {
        final int epic1Id = testEpicOne.getId();

        taskManager.deleteEpic(-1);
        testEpicOne = taskManager.getEpic(epic1Id);

        assertNull(testEpicOne, "Удаление с не корректным id увело в ошибку");
    }

    @Test
    void deleteSub() {
        taskManager.addEpic(testEpicOne);
        taskManager.addSub(testSubTaskOne);
        final int subId = testSubTaskOne.getId();

        assertNotNull(testSubTaskOne, "Задача не добавлена.");

        taskManager.deleteSub(subId);
        testSubTaskOne = taskManager.getSub(subId);

        assertNull(testSubTaskOne, "Задача не удалена");
    }

    @Test
    void deleteSubWithIncorrect() {
        final int subId = testSubTaskOne.getId();

        taskManager.deleteSub(-1);
        testSubTaskOne = taskManager.getSub(subId);

        assertNull(testSubTaskOne, "Удаление с не корректным id увело в ошибку");
    }

    @Test
    void deleteTask() {
        taskManager.addTask(testTaskOne);
        final int task1Id = testTaskOne.getId();

        assertNotNull(testTaskOne, "Задача не добавлена.");
        taskManager.deleteTask(task1Id);
        testTaskOne = taskManager.getTask(task1Id);

        assertNull(testTaskOne, "Задача не удалена");
    }

    @Test
    void deleteTaskWithIncorrect() {
        final int task1Id = testTaskOne.getId();

        taskManager.deleteTask(task1Id);
        testTaskOne = taskManager.getTask(task1Id);

        assertNull(testTaskOne, "Удаление с не корректным id увело в ошибку");
    }

}