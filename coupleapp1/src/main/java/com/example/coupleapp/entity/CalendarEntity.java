package com.example.coupleapp.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
public class CalendarEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long calendarId;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private MemberEntity member;

    @Column
    private String title;

    @Column
    private String memo;

    @Column
    private Timestamp startDate;

    @Column
    private Timestamp endDate;

    // Getters and setters (You can use Lombok for this)
}
