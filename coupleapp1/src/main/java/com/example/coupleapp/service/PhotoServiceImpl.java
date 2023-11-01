package com.example.coupleapp.service;

import com.example.coupleapp.dto.PhotoDTO;
import com.example.coupleapp.entity.PhotoEntity;
import com.example.coupleapp.repository.PhotoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PhotoServiceImpl implements PhotoService {

    private final PhotoRepository photoRepository;
    private final S3ImageService s3ImageService;

    public PhotoServiceImpl(PhotoRepository photoRepository, S3ImageService s3ImageService) {
        this.photoRepository = photoRepository;
        this.s3ImageService = s3ImageService;
    }

    @Override
    public PhotoDTO uploadPhoto(MultipartFile file, String myPhoneNumber, String yourPhoneNumber) {
        // Amazon S3에 이미지 업로드 및 URL 받아오는 로직 (s3ImageService를 사용)
        String imageUrl = s3ImageService.uploadImageFile(file);

        // 데이터베이스에 사진 메타데이터 저장
        PhotoEntity photo = new PhotoEntity();
        photo.setImgUrl(imageUrl);
        photo.setMyPhoneNumber(myPhoneNumber);
        photo.setYourPhoneNumber(yourPhoneNumber);
        // 다른 필드 설정
        PhotoEntity savedPhoto = photoRepository.save(photo);

        return convertToDTO(savedPhoto);
    }

    @Override
    public List<PhotoDTO> getAllPhotos() {
        // 데이터베이스에서 모든 사진 목록 가져오는 로직 (photoRepository 사용)
        List<PhotoEntity> photos = photoRepository.findAll();
        return photos.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public PhotoDTO getPhotoById(Long photoId) {
        // 특정 ID에 해당하는 사진을 데이터베이스에서 가져오는 로직 (photoRepository 사용)
        Optional<PhotoEntity> optionalPhoto = photoRepository.findById(photoId);
        if (optionalPhoto.isPresent()) {
            PhotoEntity photo = optionalPhoto.get();
            return convertToDTO(photo);
        } else {
            // 적절한 예외 처리
            return null;
        }
    }

    @Override
    public PhotoDTO updatePhoto(Long photoId, PhotoDTO updatedPhotoDTO) {
        // 특정 ID에 해당하는 사진을 업데이트하는 로직 (photoRepository 사용)
        Optional<PhotoEntity> optionalPhoto = photoRepository.findById(photoId);
        if (optionalPhoto.isPresent()) {
            PhotoEntity existingPhoto = optionalPhoto.get();
            // 업데이트할 필드 설정
            // 예: existingPhoto.setYourPhoneNumber(updatedPhotoDTO.getYourPhoneNumber());
            PhotoEntity updatedPhoto = photoRepository.save(existingPhoto);
            return convertToDTO(updatedPhoto);
        } else {
            // 적절한 예외 처리
            return null;
        }
    }

    @Override
    public void deletePhoto(Long photoId) {
        // 특정 ID에 해당하는 사진 삭제하는 로직 (photoRepository 사용)
        photoRepository.deleteById(photoId);
    }

    private PhotoDTO convertToDTO(PhotoEntity photo) {
        // Photo 엔티티를 PhotoDTO로 변환하는 로직
        PhotoDTO photoDTO = new PhotoDTO();
        photoDTO.setPhotoId(photo.getPhotoId());
        photoDTO.setImgUrl(photo.getImgUrl());
        photoDTO.setMyPhoneNumber(photo.getMyPhoneNumber());
        photoDTO.setYourPhoneNumber(photo.getYourPhoneNumber());
        // 다른 필드 설정
        return photoDTO;
    }
}
