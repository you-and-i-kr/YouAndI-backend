package com.example.coupleapp.entity;


import javax.persistence.*;

import com.sun.istack.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "photo")
public class PhotoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private MemberEntity member;

    @Column(name = "img_url")
    private String imgUrl;

    @Column(name = "my_phone_number")
    private String my_phone_number;

    @Column(name = "your_phone_number")
    private String your_phone_number;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime created_at;

    // Getters and setters
}
