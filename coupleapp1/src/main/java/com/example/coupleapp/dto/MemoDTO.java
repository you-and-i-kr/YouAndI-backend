package com.example.coupleapp.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class MemoDTO {
//    private Long memoId; // 메모 생성시 자동으로 생성됨
//    private Long memberId; // 이건 AuthotHolder로 memberId 값 불러오면 됨
    private String memoContent;
//    private String myPhoneNumber;
//    private String yourPhoneNumber;


    // Getters and setters (You can use Lombok for this)
}
