package com.example.coupleapp.service;

import com.example.coupleapp.dto.PhotoResponseDTO;
import com.example.coupleapp.entity.MemberEntity;
import com.example.coupleapp.entity.PhotoEntity;
import com.example.coupleapp.exception.domian.*;
import com.example.coupleapp.repository.Member.MemberRepository;
import com.example.coupleapp.repository.Photo.PhotoRepository;
import com.querydsl.core.Tuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.*;


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

//    @Override
//    public PhotoResponseDTO uploadPhoto(MultipartFile file, Long memberId) {
//        // Amazon S3에 이미지 업로드 및 URL 받아오는 로직 (s3ImageService를 사용)
//        String imageUrl = s3ImageService.uploadImageFile(file);
//        // 데이터베이스에 사진 메타데이터 저장
//        MemberEntity member = memberRepository.findMemberById(memberId);
//
//
//        PhotoEntity photo = new PhotoEntity();
//        photo.setImgUrl(imageUrl);
//        photo.setMy_phone_number(member.getMy_phone_number());
//        photo.setYour_phone_number(member.getYour_phone_number());
//        photo.setMember(member);
//        photo.setCreated_at(LocalDateTime.now());
//        // 다른 필드 설정
//        PhotoEntity savedPhoto = photoRepository.save(photo);
//        PhotoResponseDTO responseDTO = new PhotoResponseDTO();
//        responseDTO.setPhotoId(savedPhoto.getId());
//        responseDTO.setImgUrl(savedPhoto.getImgUrl());
//
//        return responseDTO;
//    }




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
    public Page<Map<String, String>> getPhotoById(Long memberId, Pageable pageable) {

        MemberEntity member = memberRepository.findMemberById(memberId);
        String myPhoneNum = member.getMy_phone_number();
        String yourPhoneNum = member.getYour_phone_number();

        List<Tuple> getPhotoList = photoRepository.findimglist(myPhoneNum,yourPhoneNum,pageable);

        if(getPhotoList.size() == 0) throw new PhotoException(PhotoErrorCode.NOT_FOUND_PHOTO);

        List<Map<String, String>> resultList = new ArrayList<>();
        for(Tuple tuple : getPhotoList){
            Map<String,String> photoMap = new HashMap<>();
            photoMap.put("photo_id", String.valueOf(tuple.get(0,Long.class)));
            photoMap.put("imgUrl",tuple.get(1, String.class));
            resultList.add(photoMap);
        }

        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());

        return new PageImpl<>(resultList, pageRequest, resultList.size());

//        List<Map<String,String>> resultList = new ArrayList<>();
//        for (String memo : getPhotoList) {
//            String[] parts = memo.split(",");
//            Map<String, String> photoMap = new HashMap<>();
//            photoMap.put("photo_id", parts[0]);
//            photoMap.put("url", parts[1]);
//            resultList.add(photoMap);
//        }
//        return resultList;
    }

    @Override
    public String uploadPhotoList(List<MultipartFile> photoFiles, Long memberId) {
        if(photoFiles.size() == 0) throw new PhotoException(PhotoErrorCode.NOT_FOUND_FILE);

        MemberEntity member = memberRepository.findMemberById(memberId);

        for(MultipartFile file : photoFiles) {
            String photoUrl = s3ImageService.uploadImageFile(file);
            PhotoEntity photo = new PhotoEntity();
            photo.setImgUrl(photoUrl);
            photo.setMy_phone_number(member.getMy_phone_number());
            photo.setYour_phone_number(member.getYour_phone_number());
            photo.setCreated_at(LocalDateTime.now());
            photo.setMember(member);
            photoRepository.save(photo);
        }

        return "업로드 완료";
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
