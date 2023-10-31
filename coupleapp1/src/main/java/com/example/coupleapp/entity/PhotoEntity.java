package com.example.coupleapp.entity;


import javax.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "photo")
public class PhotoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long photoId;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private MemberEntity member;

    @Column(name = "img_url")
    private String imgUrl;

    @Column(name = "my_phone_number")
    private String myPhoneNumber;

    @Column(name = "your_phone_number")
    private String yourPhoneNumber;

    // Getters and setters
}
