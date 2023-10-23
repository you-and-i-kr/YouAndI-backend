package com.example.coupleapp.exception;

public class NotificationNotFoundException extends RuntimeException {
    public NotificationNotFoundException(Long message) {
        super(String.valueOf(message));
    }
}
