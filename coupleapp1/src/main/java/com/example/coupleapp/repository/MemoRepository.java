package com.example.coupleapp.repository;

import com.example.coupleapp.entity.MemoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemoRepository extends JpaRepository<MemoEntity, Long> {
    List<MemoEntity> findByMemberId(Long memberId);
    List<MemoEntity> findByMyPhoneNumber(String myPhoneNumber);
    List<MemoEntity> findByYourPhoneNumber(String yourPhoneNumber);
}
