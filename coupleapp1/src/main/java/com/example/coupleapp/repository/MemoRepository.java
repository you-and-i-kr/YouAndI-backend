package com.example.coupleapp.repository;

import com.example.coupleapp.entity.MemoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemoRepository extends JpaRepository<MemoEntity, Long> {

    MemoEntity findByMemberIdAndMemoId(Long memberId, Long memoId);

    MemoEntity findByMemberId(Long memoId);

    MemoEntity findByMemoId(Long memoId);
}
