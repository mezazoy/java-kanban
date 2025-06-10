package ru.yandex.taskTraker.server;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ru.yandex.taskTraker.model.Task;
import ru.yandex.taskTraker.service.TaskManager;

import java.io.IOException;
import java.util.List;

public class HistoryHandler extends BaseHttpHandler implements HttpHandler {
    private final TaskManager taskManager;
    private final Gson gson = HttpTaskServer.getGson();

    public HistoryHandler(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        try {
            if(method.equals("GET")){
                handleGet(exchange);
            } else {
                exchange.sendResponseHeaders(400, -1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            exchange.sendResponseHeaders(500, -1);
            exchange.close();
        }
    }

    private void handleGet(HttpExchange exchange) throws IOException {
        List<Task> tasks = taskManager.getHistory();
        String json = gson.toJson(tasks);
        sendText(exchange, json);
    }
}
