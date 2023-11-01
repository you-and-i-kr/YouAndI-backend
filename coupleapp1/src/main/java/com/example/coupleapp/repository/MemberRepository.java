package com.example.coupleapp.repository;

import com.example.coupleapp.entity.MemberEntity;
import com.example.coupleapp.entity.SettingsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long> , MemberRepositoryCustom {
    List<MemberEntity> findByEmail(String email);

    boolean existsByEmail(String email);

    MemberEntity existsByName(String note_name);


    MemberEntity findUserByEmail(String email);
//    List<MemberEntity> findByMyPhoneNumber(String myPhoneNumber);
//    List<MemberEntity> findByYourPhoneNumber(String yourPhoneNumber);
     void deleteById(Long memberId);


}
