package com.example.coupleapp.exception;

public class SettingsNotFoundException extends RuntimeException {

    public SettingsNotFoundException(String message) {
        super(message);
    }

    public SettingsNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
