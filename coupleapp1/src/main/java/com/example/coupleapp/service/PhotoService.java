package com.example.coupleapp.service;

import com.example.coupleapp.dto.PhotoDTO;
import com.example.coupleapp.dto.PhotoResponseDTO;
import com.example.coupleapp.entity.PhotoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface PhotoService {
//    PhotoResponseDTO uploadPhoto(MultipartFile file, Long memberId);

    PhotoEntity updatePhoto(Long photoId, MultipartFile file);

    void deletePhoto(Long photoId);

    Page<Map<String, String>> getPhotoById(Long memberId, Pageable pageable);

    String uploadPhotoList(List<MultipartFile> photoFiles, Long memberId);
}
