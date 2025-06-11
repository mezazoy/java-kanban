package ru.yandexTest.taskTraker.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import ru.yandex.taskTraker.service.InMemoryHistoryManager;
import static org.junit.jupiter.api.Assertions.*;
import ru.yandex.taskTraker.model.Task;
import java.util.ArrayList;
import ru.yandex.taskTraker.service.Status;


class InMemoryHistoryManagerTest {
    private InMemoryHistoryManager historyManager;

    @BeforeEach
    public void setUp() {
        historyManager = new InMemoryHistoryManager();
    }

    @Test
    public void addTask() {
        Task task = new Task("Testname", "testDeskription", Status.NEW, "30", "15.10.25 14:50");
        historyManager.add(task);
        ArrayList<Task> history = historyManager.getHistory();

        assertNotNull(history, "Задачи не добавлены!");
        assertEquals(task, history.get(0), "Задачи не совпадают!");
    }

    @Test
    public void addMultipleTasks() {
        Task task1 = new Task("Testname", "testDeskription", Status.NEW, "30", "15.10.25 14:50");
        Task task2 = new Task("Testname", "testDeskription", Status.NEW, "30", "16.10.25 14:50");
        Task task3 = new Task("Testname", "testDeskription", Status.NEW, "30", "17.10.25 14:50");
        task1.setId(1);
        task2.setId(2);
        task3.setId(3);
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task3);
        ArrayList<Task> history = historyManager.getHistory();

        assertNotNull(history, "Задачи не добавлены!");
        assertEquals(3, history.size(), "Не все задачи добавлены!");
        assertEquals(task1, history.get(0), "Задачи не совпадают!");
        assertEquals(task2, history.get(1), "Задачи не совпадают!");
        assertEquals(task3, history.get(2), "Задачи не совпадают!");
    }

    @Test
    public void addDuplicate() {
        Task task1 = new Task("Testname", "testDeskription", Status.NEW, "30", "15.10.25 14:50");
        Task task2 = new Task("Testname", "testDeskription", Status.NEW, "30", "17.10.25 14:50");
        task1.setId(1);
        task2.setId(2);
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task1);
        ArrayList<Task> history = historyManager.getHistory();

        assertEquals(2, history.size(), "Не верное количество задач!");
        assertEquals(task2, history.get(0), "Задачи не совпадают!");
        assertEquals(task1, history.get(1), "Задачи не совпадают!");
    }

    @Test
    public void removeExistingTask() {
        Task task1 = new Task("Testname", "testDeskription", Status.NEW, "30", "15.10.25 14:50");
        Task task2 = new Task("Testname", "testDeskription", Status.NEW, "30", "20.10.25 14:50");
        Task task3 = new Task("Testname", "testDeskription", Status.NEW, "30", "21.10.25 14:50");
        Task task4 = new Task("Testname", "testDeskription", Status.NEW, "30", "22.10.25 14:50");
        Task task5 = new Task("Testname", "testDeskription", Status.NEW, "30", "23.10.25 14:50");
        Task task6 = new Task("Testname", "testDeskription", Status.NEW, "30", "24.10.25 14:50");
        task1.setId(1);
        task2.setId(2);
        task3.setId(3);
        task4.setId(4);
        task5.setId(5);
        task6.setId(6);
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task3);
        historyManager.add(task4);
        historyManager.add(task5);
        historyManager.add(task6);
        historyManager.remove(1);
        historyManager.remove(4);
        historyManager.remove(6);
        ArrayList<Task> currentHistory = historyManager.getHistory();

        assertEquals(3, currentHistory.size(), "Не верное количество задач!");
        assertFalse(currentHistory.contains(task1), "Задача не удалена!");
        assertFalse(currentHistory.contains(task4), "Задача не удалена!");
        assertFalse(currentHistory.contains(task6), "Задача не удалена!");
        assertEquals(task2, currentHistory.get(0), "Задачи не совпадают! Порядок задач изменился");
        assertEquals(task3, currentHistory.get(1), "Задачи не совпадают! Порядок задач изменился");
        assertEquals(task5, currentHistory.get(2), "Задачи не совпадают! Порядок задач изменился");
    }

    @Test
    public void removeNonExistingTask() {
        Task existingTask = new Task("Testname", "testDeskription", Status.NEW, "30", "17.10.25 14:50");
        existingTask.setId(1);
        historyManager.add(existingTask);
        historyManager.remove(999);
        ArrayList<Task> currentHistory = historyManager.getHistory();

        assertEquals(1, currentHistory.size(), "Не верное количесвто задач!");
        assertTrue(currentHistory.contains(existingTask), "Задача не найдена!");
    }
}