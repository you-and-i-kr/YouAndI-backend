package com.example.coupleapp.dto;
import lombok.*;

@Data
public class HomeDTO {
    private Long home_id;
    private Long member_id; // 혹시 member_id만 필요하다면 DTO에 추가
    private int d_day;
    private String notification;
    private int count_heart;

}
