package com.example.coupleapp.entity;


import javax.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "image")
public class ImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "file_name")
    private String fileName;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "image_data")
    private byte[] imageData;

    // 다른 필드 및 getter/setter 메서드
}
