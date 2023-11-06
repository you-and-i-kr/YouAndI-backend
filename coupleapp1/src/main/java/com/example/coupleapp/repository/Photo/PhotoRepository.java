package com.example.coupleapp.repository.Photo;


import com.example.coupleapp.entity.PhotoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PhotoRepository extends JpaRepository<PhotoEntity, Long> , PhotoRepositoryCustom{
//    @Query("SELECT p.id,p.imgUrl FROM PhotoEntity p WHERE p.my_phone_number = :myPhoneNum OR p.my_phone_number = :yourPhoneNum")
//    List<String> findimglist(@Param("myPhoneNum") String myPhoneNum, @Param("yourPhoneNum") String yourPhoneNum);

}
