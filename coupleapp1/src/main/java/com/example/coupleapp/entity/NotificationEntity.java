package com.example.coupleapp.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
@Data
@Entity
public class NotificationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "partner_id")
    private MemberEntity partner;

    @Column
    private Timestamp created_at;

    @Column
    private Long target_id;

    @Column
    private Boolean is_read;

    // Getters and setters (You can use Lombok for this)
}
