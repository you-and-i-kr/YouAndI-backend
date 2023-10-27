package com.example.coupleapp.repository;

import com.example.coupleapp.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    List<MemberEntity> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByName(String noteName);

    MemberEntity findUserByEmail(String email);

    // Custom queries can be defined here if needed
}
