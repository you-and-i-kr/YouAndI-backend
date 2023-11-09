package com.example.coupleapp.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;

@Data
@Entity
@RequiredArgsConstructor
@Table(name = "calendar")
public class CalendarEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private MemberEntity member;

    @Column(name = "title")
    private String title;

    @Column(name = "memo")
    private String memo;

    @Column(name = "create_Date")
    private LocalDate createDate;

    @Column(name = "created_At")
    private LocalDate created_At;

    @Column(name = "updated_At")
    private LocalDate updated_At;

    @Column(name = "my_phone_number")
    private String my_phone_number;

    @Column(name = "your_phone_number")
    private String your_phone_number;
    public CalendarEntity(MemberEntity member, String title, String memo, LocalDate created_At , String my_phone_number,String  your_phone_number) {
        this.member = member;
        this.title = title;
        this.memo = memo;
        this.created_At = created_At == null ? LocalDate.now() : created_At;
        this.my_phone_number = my_phone_number;
        this.your_phone_number = your_phone_number;
    }
}
