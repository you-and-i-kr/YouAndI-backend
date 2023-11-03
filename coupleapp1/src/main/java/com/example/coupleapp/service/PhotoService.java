package com.example.coupleapp.service;

import com.example.coupleapp.dto.PhotoDTO;
import com.example.coupleapp.dto.PhotoResponseDTO;
import com.example.coupleapp.entity.PhotoEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PhotoService {
    PhotoResponseDTO uploadPhoto(MultipartFile file, Long memberId);

 
    PhotoEntity updatePhoto(Long photoId, MultipartFile file);

    void deletePhoto(Long photoId);

    List<String> getPhotoById(Long memberId);
}
