package com.example.coupleapp.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "member")
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="member_id")
    private Long member_id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "my_phone_number")
    private String my_phone_number;

    @Column(name = "your_phone_number")
    private String your_phone_number;

    @Column(name = "start_date")
    private String start_date;

    @Column(name = "note_name")
    private String  name;

    @Column(name = "created_at")
    private Timestamp created_at;

    @Column(name = "updated_at")
    private Timestamp updated_at;

    @Column(name = "my_profile_image")
    private String my_profile_image;

    @Column(name = "your_profile_image")
    private String your_profile_image;

}
