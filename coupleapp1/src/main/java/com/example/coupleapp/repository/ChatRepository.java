package com.example.coupleapp.repository;

import com.example.coupleapp.entity.ChatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends JpaRepository<ChatEntity, Long> {
    // 필요한 추가적인 쿼리 메서드 정의 가능
}
