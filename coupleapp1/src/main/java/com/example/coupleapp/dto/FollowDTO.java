package com.example.coupleapp.dto;

import lombok.Data;

@Data
public class FollowDTO {
    private Long followId;
    private Long followerId;
    private Long followingId;

    // Getters and setters (You can use Lombok for this)
}
