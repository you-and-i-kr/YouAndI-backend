package com.example.coupleapp.entity;

import lombok.Data;

import javax.persistence.*;
@Data
@Entity
@Table(name="follow")
public class FollowEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long followId;

    @ManyToOne
    @JoinColumn(name = "follower_id")
    private MemberEntity follower;

    @ManyToOne
    @JoinColumn(name = "following_id")
    private MemberEntity following;

    // Getters and setters (You can use Lombok for this)
}
