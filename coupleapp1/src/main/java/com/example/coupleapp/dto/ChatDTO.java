package com.example.coupleapp.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ChatDTO {
    private Long chatId;
    private Long memberId;
    private Long partnerId;
    private Timestamp timestamp;
    private String message;


}