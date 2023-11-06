package com.example.coupleapp.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "memo")
public class MemoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn (name = "member_id")
    private MemberEntity member;

    @Column(name = "memo_content")
    private String memoContent;

    @Column(name = "my_phone_number")
    private String myPhoneNumber;

    @Column(name = "your_phone_number")
    private String yourPhoneNumber;

    @Column(name = "created_at")
    private LocalDateTime created_at;


}


