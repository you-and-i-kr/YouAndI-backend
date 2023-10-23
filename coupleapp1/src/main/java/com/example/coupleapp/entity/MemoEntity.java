package com.example.coupleapp.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
@Data
@Entity
public class MemoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memoId;

    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer memoContent;

    @Column
    private String comment;

    @Column
    private Timestamp createdAt;

 }
