package com.example.coupleapp.entity;


import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
@Data
@Entity
public class AlbumContentEntity {
    @Id
    private String contentId;

    @ManyToOne
    @JoinColumn(name = "album_id")
    private AlbumEntity album;

    @Column
    private String mediaType;

    @Column
    private String contentData;

    @Column
    private Timestamp createdAt;


}
