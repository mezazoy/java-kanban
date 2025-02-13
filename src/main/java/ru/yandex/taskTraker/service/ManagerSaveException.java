package ru.yandex.taskTraker.service;

import java.io.IOException;

public class ManagerSaveException extends RuntimeException {
    public ManagerSaveException(String message, IOException ex) {
        super(message);
    }

}
