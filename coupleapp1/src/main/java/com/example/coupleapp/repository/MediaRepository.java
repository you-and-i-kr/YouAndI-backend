package com.example.coupleapp.repository;

import com.example.coupleapp.entity.MediaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MediaRepository extends JpaRepository<MediaEntity, Long> {
    @Query("SELECT m.media_url FROM MediaEntity m WHERE m.my_phone_number = :myPhoneNum OR m.my_phone_number = :yourPhoneNum")
    List<String> findMedialist(@Param("myPhoneNum") String myPhoneNum, @Param("yourPhoneNum") String yourPhoneNum);

}
