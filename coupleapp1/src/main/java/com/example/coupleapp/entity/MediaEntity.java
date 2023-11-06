package com.example.coupleapp.entity;


import javax.persistence.*;

import com.sun.istack.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "media")
public class MediaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private MemberEntity member;

    @Column(name = "media_url")
    private String media_url;

    @Column(name = "my_phone_number")
    private String my_phone_number;

    @Column(name = "your_phone_number")
    private String your_phone_number;


    @Column(name = "created_at")
    private LocalDateTime created_at;

}
