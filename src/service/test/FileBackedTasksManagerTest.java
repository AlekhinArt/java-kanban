package service.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.manager.FileBackedTasksManager;
import service.task.Epic;
import service.task.Status;
import service.task.SubTask;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileBackedTasksManagerTest extends TaskManagerTest<FileBackedTasksManager> {
    FileBackedTasksManager fileBackedTasksManager;
    FileBackedTasksManager testFileBackedTasksManager;
    Epic testEpic;
    SubTask testSubtask;
    SubTask testSubtask2;
    File file;

    @BeforeEach
    void beforeEach() {
        file = new File("bin\\test.CSV");
        fileBackedTasksManager = new FileBackedTasksManager(file);

        testEpic = new Epic("Test addNew", "Test addNew description", Status.DONE);
        testSubtask = new SubTask("Test addNew", "Test addNew description",
                Status.NEW, testEpic.getId());
        testSubtask2 = new SubTask("Test addNew", "Test addNew description",
                Status.DONE, testEpic.getId());


    }

    @Test
    void saveAddLoadTasks() {
        fileBackedTasksManager.addEpic(testEpic);
        fileBackedTasksManager.addSub(testSubtask);
        fileBackedTasksManager.addSub(testSubtask2);
        ArrayList<SubTask> subTasks = fileBackedTasksManager.getSubs();

        assertNotNull(subTasks, "Список задач пустой");
        testFileBackedTasksManager = FileBackedTasksManager.loadFromFile(file);
        ArrayList<SubTask> testSubTasks = fileBackedTasksManager.getSubs();
        assertEquals(2, testSubTasks.size(), "Не корректно загружен список из файла");
        assertEquals(subTasks, testSubTasks, "Не корректно загружен список из файла");
    }

    @Test
    void saveAddLoadTasksWithEmptyFile() {
        ArrayList<SubTask> subTasks = fileBackedTasksManager.getSubs();

        assertEquals(0, subTasks.size(), "Список не пустой");
        testFileBackedTasksManager = FileBackedTasksManager.loadFromFile(file);
        ArrayList<SubTask> testSubTasks = fileBackedTasksManager.getSubs();
        assertEquals(0, testSubTasks.size(), "Список не пустой");
        assertEquals(subTasks, testSubTasks, "Не корректно загружен список из файла");
    }

    @Test
    void saveAddLoadEpic() {
        fileBackedTasksManager.addEpic(testEpic);

        List<Epic> epics = fileBackedTasksManager.getEpics();

        assertEquals(1, epics.size(), "Список пустой");
        testFileBackedTasksManager = FileBackedTasksManager.loadFromFile(file);
        List<Epic> testEpics = fileBackedTasksManager.getEpics();
        assertEquals(1, testEpics.size(), "Список  пустой");
        assertEquals(epics, testEpics, "Не корректно загружен список из файла");
    }


}