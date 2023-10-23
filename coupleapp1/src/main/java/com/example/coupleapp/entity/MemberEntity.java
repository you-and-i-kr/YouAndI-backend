package com.example.coupleapp.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import lombok.Data;

@Entity
@Data
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long member_id;

    private String email;
    private String password;
    private String my_phone_number;
    private String your_phone_number;
    private Timestamp start_date;
    private String note_name;
    private Timestamp created_at;
    private Timestamp updated_at;
    private byte[] my_profile_image;
    private byte[] your_profile_image;


    public Long getMemberId() {
        return member_id;
    }

    public void setMemberId(Long memberId) {
        this.member_id = member_id;
    }
}
