package com.example.coupleapp.dto;

import lombok.Data;

import java.sql.Timestamp;
@Data
public class CalendarDTO {
    private Long calendarId;
    private Long memberId;
    private String title;
    private String memo;
    private Timestamp startDate;
    private Timestamp endDate;

    // Getters and setters (You can use Lombok for this)
}
