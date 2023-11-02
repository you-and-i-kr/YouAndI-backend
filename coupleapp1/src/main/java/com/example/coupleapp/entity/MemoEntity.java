package com.example.coupleapp.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
@Data
@Entity
@Table(name = "memo")
public class MemoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "memo_id")
    private Long memoId;

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "memo_content")
    private String memoContent;

    @Column(name = "my_phone_number")
    private String myPhoneNumber;

    @Column(name = "your_phone_number")
    private String yourPhoneNumber;


}


