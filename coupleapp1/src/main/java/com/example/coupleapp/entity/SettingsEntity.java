package com.example.coupleapp.entity;

import lombok.Data;

import javax.persistence.*;
@Data
@Entity
@Table(name = "setting")
public class SettingsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "member_id")
    private MemberEntity member;

    @Column
    private String note_name;

    @Column
    private Long partnerId;

    // Getters and setters (You can use Lombok for this)
}
