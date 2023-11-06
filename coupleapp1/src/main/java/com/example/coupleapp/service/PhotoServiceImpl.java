package com.example.coupleapp.service;

import com.example.coupleapp.dto.PhotoDTO;
import com.example.coupleapp.dto.PhotoResponseDTO;
import com.example.coupleapp.entity.MemberEntity;
import com.example.coupleapp.entity.PhotoEntity;
import com.example.coupleapp.exception.domian.PhotoErrorCode;
import com.example.coupleapp.exception.domian.PhotoException;
import com.example.coupleapp.repository.MemberRepository;
import com.example.coupleapp.repository.PhotoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class PhotoServiceImpl implements PhotoService {

    private final PhotoRepository photoRepository;
    private final S3ImageService s3ImageService;
    private final MemberRepository memberRepository;

    public PhotoServiceImpl(PhotoRepository photoRepository, S3ImageService s3ImageService, MemberRepository memberRepository) {
        this.photoRepository = photoRepository;
        this.s3ImageService = s3ImageService;
        this.memberRepository = memberRepository;
    }

    @Override
    public PhotoResponseDTO uploadPhoto(MultipartFile file, Long memberId) {
        // Amazon S3에 이미지 업로드 및 URL 받아오는 로직 (s3ImageService를 사용)
        String imageUrl = s3ImageService.uploadImageFile(file);
        // 데이터베이스에 사진 메타데이터 저장
        MemberEntity member = memberRepository.findMemberById(memberId);


        PhotoEntity photo = new PhotoEntity();
        photo.setImgUrl(imageUrl);
        photo.setMy_phone_number(member.getMy_phone_number());
        photo.setYour_phone_number(member.getYour_phone_number());
        photo.setMember(member);
        photo.setCreated_at(LocalDateTime.now());
        // 다른 필드 설정
        PhotoEntity savedPhoto = photoRepository.save(photo);
        PhotoResponseDTO responseDTO = new PhotoResponseDTO();
        responseDTO.setPhotoId(savedPhoto.getId());
        responseDTO.setImgUrl(savedPhoto.getImgUrl());

        return responseDTO;
    }




    @Override
    public PhotoEntity updatePhoto(Long photoId, MultipartFile file) {
        PhotoEntity existingPhoto = photoRepository.findById(photoId)
                .orElseThrow(()-> new PhotoException(PhotoErrorCode.FAIL_UPDATE));
        existingPhoto.setImgUrl(existingPhoto.getImgUrl());
        // Save the updated memoEntity in the repository
        return photoRepository.save(existingPhoto);
    }

    @Override
    public void deletePhoto(Long photoId) {
        // 특정 ID에 해당하는 사진 삭제하는 로직 (photoRepository 사용)
        photoRepository.deleteById(photoId);
    }

    @Override
    public List<String> getPhotoById(Long memberId) {
        // member의 내번호와 맞는 데이터 + 내번호에 상대방번호가 넣어진 데이터들 불러오기
        MemberEntity member = memberRepository.findMemberById(memberId);
        // 내 번호
        String myPhoneNum = member.getMy_phone_number();
        // 상대방 번호
        String yourPhoneNum = member.getYour_phone_number();
        return photoRepository.findimglist(myPhoneNum,yourPhoneNum);
    }

    private PhotoResponseDTO convertToDTO(PhotoEntity photo) {
        // Photo 엔티티를 PhotoDTO로 변환하는 로직
        PhotoResponseDTO photoDTO = new PhotoResponseDTO();
        photoDTO.setPhotoId(photo.getId());
        photoDTO.setImgUrl(photo.getImgUrl());
//        photoDTO.setMyPhoneNumber(photo.getMyPhoneNumber());
//        photoDTO.setYourPhoneNumber(photo.getYourPhoneNumber());
        // 다른 필드 설정
        return photoDTO;
    }
}
