package ru.yandex.taskTraker.server;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.Duration;

public class DurationTypeAdapter extends TypeAdapter<Duration> {
    @Override
    public void write(JsonWriter out, Duration value) throws IOException {
        out.value(value.toString()); // например, "PT5M" для 5 минут
    }

    @Override
    public Duration read(JsonReader in) throws IOException {
        String durationStr = in.nextString();
        return Duration.parse(durationStr);
    }
}
