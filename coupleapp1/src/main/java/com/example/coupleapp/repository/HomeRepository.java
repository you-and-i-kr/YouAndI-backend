package com.example.coupleapp.repository;
import com.example.coupleapp.entity.HomeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HomeRepository extends JpaRepository<HomeEntity, Long> {
    // 사용자 정의 쿼리가 필요한 경우 여기에 추가
}
