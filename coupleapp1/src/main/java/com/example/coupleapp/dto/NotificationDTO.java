package com.example.coupleapp.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class NotificationDTO {
    private Long id;
    private Long partnerId;
    private Timestamp createdAt;
    private Long targetId;
    private Boolean isRead;

    // Getters and setters (You can use Lombok for this)
}
