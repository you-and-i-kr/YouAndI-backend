package com.example.coupleapp.repository;

import com.example.coupleapp.entity.MemoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemoRepository extends JpaRepository<MemoEntity, Long> {

    @Query("select m.id,m.memoContent from MemoEntity m where m.myPhoneNumber = :myPhoneNumber or m.myPhoneNumber = :yourPhoneNumber")
    List<String> findMemoListByPhoneNumber(@Param("myPhoneNumber")String myPhoneNumber,@Param("yourPhoneNumber") String yourPhoneNumber);

//    MemoEntity findByMemberAndMemoId(Long memberId, Long memoId);
}
