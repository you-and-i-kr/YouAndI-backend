package com.example.coupleapp.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class MemoDTO {
    private Long memoId;
    private Integer memoContent;
    private String comment;
    private Timestamp createdAt;

    // Getters and setters (You can use Lombok for this)
}
