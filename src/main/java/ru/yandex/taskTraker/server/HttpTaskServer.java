package ru.yandex.taskTraker.server;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpServer;
import ru.yandex.taskTraker.service.Managers;
import ru.yandex.taskTraker.service.TaskManager;

import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpTaskServer {
    private static final int PORT = 8080;
    private HttpServer server;
    private final TaskManager taskManager;
    private static final Gson gson = new Gson();

    public HttpTaskServer(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    public static void main(String[] args) throws IOException {
        TaskManager manager = Managers.getDefaultFile();
        HttpTaskServer server = new HttpTaskServer(manager);
        server.start();

        Runtime.getRuntime().addShutdownHook(new Thread(server::stop));
    }

    public static Gson getGson() {
        return gson;
    }

    public void start() throws IOException {
        server = HttpServer.create(new InetSocketAddress(PORT), 0);
        // Регистрация обработчиков
        server.createContext("/tasks", new TaskHandler(taskManager));
        server.createContext("/subtasks", new SubtaskHandler(taskManager));
        server.createContext("/epics", new EpicHandler(taskManager));
        server.createContext("/history", new HistoryHandler(taskManager));
        server.createContext("/prioritized", new PrioritizedHandler(taskManager));

        server.start();
        System.out.println("Server started on port " + PORT);
    }

    public void stop() {
        if (server != null) {
            server.stop(0);
            System.out.println("Server stopped");
        }
    }
}
