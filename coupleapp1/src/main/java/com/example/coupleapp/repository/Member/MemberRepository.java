package com.example.coupleapp.repository.Member;

import com.example.coupleapp.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long> , MemberRepositoryCustom {
    List<MemberEntity> findByEmail(String email);

    boolean existsByEmail(String email);

    MemberEntity existsByName(String note_name);
    MemberEntity findUserByEmail(String email);

     void deleteById(Long memberId);


}
