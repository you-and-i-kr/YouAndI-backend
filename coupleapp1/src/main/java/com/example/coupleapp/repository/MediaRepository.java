package com.example.coupleapp.repository;

import com.example.coupleapp.entity.MediaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MediaRepository extends JpaRepository<MediaEntity, Long> {
    @Query("SELECT m.mediaUrl FROM MediaEntity m WHERE m.myPhoneNumber = :myPhoneNum OR m.myPhoneNumber = :yourPhoneNum")
    List<String> findMedialist(@Param("myPhoneNum") String myPhoneNum, @Param("yourPhoneNum") String yourPhoneNum);

}
