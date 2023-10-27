package com.example.coupleapp.entity;

import javax.persistence.*;

import lombok.Data;


@Entity
@Data
@Table(name="home")
public class HomeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long home_id;

    @ManyToOne
    private MemberEntity member;

    private int d_day;
    private String notification;
    private int count_heart;
}
