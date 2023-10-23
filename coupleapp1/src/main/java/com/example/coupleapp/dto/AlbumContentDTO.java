package com.example.coupleapp.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class AlbumContentDTO {
    private String contentId;
    private Long albumId;
    private String mediaType;
    private String contentData;
    private Timestamp createdAt;

    // Getter and Setter methods
}

