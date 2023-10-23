package com.example.coupleapp.repository;

import com.example.coupleapp.entity.AlbumContentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumContentRepository extends JpaRepository<AlbumContentEntity, String> {
}
