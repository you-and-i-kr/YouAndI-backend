package com.example.coupleapp.dto;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class MemberDTO {
    private Long member_id;
    private String email;
    private String my_phone_number;
    private String your_phone_number;
    private Timestamp start_date;
    private String note_name;
    private Timestamp created_at;
    private Timestamp updated_at;
    private byte[] my_profile_image;
    private byte[] your_profile_image;
}
