package com.example.coupleapp.repository.Media;

import com.example.coupleapp.entity.MediaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface MediaRepository extends JpaRepository<MediaEntity, Long> ,MediaRepositoryCustom {
//    @Query("SELECT m.id,m.media_url FROM MediaEntity m WHERE m.my_phone_number = :myPhoneNum OR m.my_phone_number = :yourPhoneNum")
//    List<String> findMedialist(@Param("myPhoneNum") String myPhoneNum, @Param("yourPhoneNum") String yourPhoneNum);

//    @Query("SELECT m FROM MediaEntity m WHERE m.id = :memberId")
//    Page<MediaEntity> findAllByMemberId(Long memberId, Pageable pageRequest);

}
