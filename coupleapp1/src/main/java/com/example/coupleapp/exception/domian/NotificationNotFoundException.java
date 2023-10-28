package com.example.coupleapp.exception.domian;

public class NotificationNotFoundException extends RuntimeException {
    public NotificationNotFoundException(Long message) {
        super(String.valueOf(message));
    }
}
