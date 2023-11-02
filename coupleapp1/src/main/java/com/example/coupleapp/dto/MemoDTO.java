package com.example.coupleapp.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class MemoDTO {
    private Long memoId;
    private Long memberId;
    private String memoContent;
//    private String myPhoneNumber;
//    private String yourPhoneNumber;


    // Getters and setters (You can use Lombok for this)
}
