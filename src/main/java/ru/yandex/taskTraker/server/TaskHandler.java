package ru.yandex.taskTraker.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ru.yandex.taskTraker.model.Task;
import ru.yandex.taskTraker.service.TaskIntersectionException;
import ru.yandex.taskTraker.service.TaskManager;
import ru.yandex.taskTraker.service.TaskNotFoundException;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class TaskHandler extends BaseHttpHandler implements HttpHandler {
    private final TaskManager taskManager;
    private final Gson gson = HttpTaskServer.getGson();

    public TaskHandler(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();
        String[] splitPath = path.split("/");

        try {
            switch (method) {
                case "GET":
                    if (splitPath.length > 2) {
                        handleGetTaskById(exchange);
                    } else {
                        handleGet(exchange);
                    }
                    break;
                case "POST":
                    handlePost(exchange);
                    break;
                case "DELETE":
                    handleDelete(exchange);
                    break;
                default:
                    sendNotFound(exchange);
            }
        } catch (Exception e) {
            e.printStackTrace();
            exchange.sendResponseHeaders(500, -1);
            exchange.close();
        }
    }

    private void handleGet(HttpExchange exchange) throws IOException {
        List<Task> tasks = taskManager.getTasks();
        String json = gson.toJson(tasks);
        sendText(exchange, json);
    }

    private void handleGetTaskById(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        String[] splitPath = path.split("/");

        if (splitPath.length < 3) {
            sendNotFound(exchange);
            return;
        }
        String taskId = splitPath[2];

        try {
            int id = Integer.parseInt(taskId);
            Task task = taskManager.getTaskByIdentifier(id);
            String json = gson.toJson(task);
            sendText(exchange, json);
        } catch (NumberFormatException | TaskNotFoundException e) {
            sendNotFound(exchange);
        } catch (Exception e) {
            exchange.sendResponseHeaders(500,-1);
            exchange.close();
        }
    }

    private void handlePost(HttpExchange exchange) throws IOException {
        InputStreamReader reader = new InputStreamReader(exchange.getRequestBody(), "utf-8");
        Task task = gson.fromJson(reader, Task.class);

        try {
            taskManager.addTask(task);
            exchange.sendResponseHeaders(201, -1);
            exchange.close();
        } catch (TaskIntersectionException e) {
            sendHasIntersections(exchange);
        } catch (Exception e) {
            exchange.sendResponseHeaders(500, -1);
            exchange.close();
        }
    }

    private void handleDelete(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        String[] split = path.split("/");

        if (split.length < 3) {
            sendNotFound(exchange);
            return;
        }
        String taskId = split[2];

        try {
            int id = Integer.parseInt(taskId);
            taskManager.deleteTaskByIdentifier(id);
            exchange.sendResponseHeaders(201, -1);
            exchange.close();
        } catch (NumberFormatException | TaskNotFoundException e) {
            sendNotFound(exchange);
        } catch (Exception e) {
            exchange.sendResponseHeaders(500, -1);
            exchange.close();
        }
    }
}
