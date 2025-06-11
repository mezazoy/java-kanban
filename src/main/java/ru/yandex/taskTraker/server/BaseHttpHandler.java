package ru.yandex.taskTraker.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import ru.yandex.taskTraker.adapters.DurationTypeAdapter;
import ru.yandex.taskTraker.adapters.LocalDateTimeAdapter;
import ru.yandex.taskTraker.service.Managers;
import ru.yandex.taskTraker.service.TaskManager;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;

public class BaseHttpHandler {
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    protected static final Gson gson = new GsonBuilder().registerTypeAdapter(Duration.class, new DurationTypeAdapter())
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();
    protected static final TaskManager taskManager = Managers.getDefaultFile();


    protected void sendText(HttpExchange h, String text) throws IOException {
        byte[] resp = text.getBytes(DEFAULT_CHARSET);
        h.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        h.sendResponseHeaders(200, resp.length);
        h.getResponseBody().write(resp);
        h.close();
    }

    protected void sendNotFound(HttpExchange h) throws IOException {
        String response = "Not Found";
        h.getResponseHeaders().add("Content-Type", "text/plain;charset=utf-8");
        h.sendResponseHeaders(404, response.length());
        h.getResponseBody().write(response.getBytes(DEFAULT_CHARSET));
        h.close();
    }

    protected void sendHasIntersections(HttpExchange h) throws IOException {
        String response = "Not Acceptable";
        h.getResponseHeaders().add("Content-Type", "text/plain;charset=utf-8");
        h.sendResponseHeaders(406, response.length());
        h.getResponseBody().write(response.getBytes(DEFAULT_CHARSET));
        h.close();
    }
}
