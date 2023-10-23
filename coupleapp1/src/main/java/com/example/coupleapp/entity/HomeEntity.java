package com.example.coupleapp.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import lombok.Data;


@Entity
@Data
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
