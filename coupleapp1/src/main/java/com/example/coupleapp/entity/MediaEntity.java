package com.example.coupleapp.entity;


import javax.persistence.*;

import lombok.*;

@Data
@Entity
@Table(name = "media")
public class MediaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mediaId;

    @Column(name = "img_url")
    private String imgUrl;

    @Column(name = "my_phone_number")
    private String myPhoneNumber;

    @Column(name = "your_phone_number")
    private String yourPhoneNumber;

}
