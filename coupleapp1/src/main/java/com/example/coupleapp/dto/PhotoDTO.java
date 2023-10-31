package com.example.coupleapp.dto;

import lombok.*;

@Data
public class PhotoDTO {
    private Long photoId;
    private Long memberId;
    private String imgUrl;
    private String myPhoneNumber;
    private String yourPhoneNumber;


}
