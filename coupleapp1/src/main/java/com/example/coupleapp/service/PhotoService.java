package com.example.coupleapp.service;

import com.example.coupleapp.dto.PhotoDTO;
import com.example.coupleapp.entity.PhotoEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
public interface PhotoService {
    PhotoDTO uploadPhoto(MultipartFile file,Long memberId);
//    List<PhotoDTO> getAllPhotos();
    PhotoDTO getPhotoById(Long photoId);
    PhotoDTO updatePhoto(Long photoId, PhotoDTO updatedPhotoDTO);

    PhotoEntity updatePhoto(Long photoId, MultipartFile file);

    void deletePhoto(Long photoId);
}
