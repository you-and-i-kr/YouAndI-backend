package com.example.coupleapp.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class AlbumDTO {
    private Long albumId;
    private Long memberId;
    private Timestamp createdAt;

}
