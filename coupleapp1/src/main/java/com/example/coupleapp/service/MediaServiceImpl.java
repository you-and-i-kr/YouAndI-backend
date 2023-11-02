package com.example.coupleapp.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.example.coupleapp.dto.MediaDTO;
import com.example.coupleapp.entity.MediaEntity;
import com.example.coupleapp.entity.MemberEntity;
import com.example.coupleapp.repository.MediaRepository;
import com.example.coupleapp.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class MediaServiceImpl implements MediaService {

    private final MediaRepository mediaRepository;
    private final AmazonS3 amazonS3;
    private final String bucket; // Amazon S3 버킷 이름
    private final S3mediaService s3mediaService;
    private final MemberRepository memberRepository;

    @Autowired
    public MediaServiceImpl(MediaRepository mediaRepository, AmazonS3 amazonS3, @Value("${S3_BUCKET_NAME}") String bucket, S3mediaService s3mediaService, MemberRepository memberRepository) {
        this.mediaRepository = mediaRepository;
        this.amazonS3 = amazonS3;
        this.bucket = bucket;
        this.s3mediaService = s3mediaService;
        this.memberRepository = memberRepository;
    }

    @Override
    public MediaDTO uploadMedia(MultipartFile file, long memberId) {
        return null;
    }

    @Override
    public MediaDTO uploadMedia(MultipartFile file, Long memberId) {
        // MultipartFile을 File로 변환
       String mediaUrl= s3mediaService.uploadMediaFile(file);
       MemberEntity member = memberRepository.findMemberByMemberId(memberId);


        // 데이터베이스에 미디어 메타데이터 저장
        MediaEntity media = new MediaEntity();
        media.setMediaUrl(mediaUrl);
        media.setMyPhoneNumber(member.getMy_phone_number());
        media.setYourPhoneNumber(member.getYour_phone_number());
        MediaEntity savedMedia = mediaRepository.save(media);

        return convertEntityToDTO(savedMedia);
    }

//    @Override
//    public List<MediaDTO> getAllMedia() {
//        // 데이터베이스에서 모든 미디어 목록 가져오는 로직 (mediaRepository 사용)
//        List<MediaEntity> mediaEntities = mediaRepository.findAll();
//        return mediaEntities.stream().map(this::convertEntityToDTO).collect(Collectors.toList());
//    }

    @Override
    public MediaDTO getMediaById(Long mediaId) {
        // 특정 ID에 해당하는 미디어를 데이터베이스에서 가져오는 로직 (mediaRepository 사용)
        MediaEntity mediaEntity = mediaRepository.findById(mediaId).orElse(null);
        if (mediaEntity != null) {
            return convertEntityToDTO(mediaEntity);
        }
        return null;
    }

    @Override
    public MediaDTO updateMedia(Long mediaId, MediaDTO updatedMediaDTO) {
        // 특정 ID에 해당하는 미디어를 업데이트하는 로직 (mediaRepository 사용)
        MediaEntity mediaEntity = mediaRepository.findById(mediaId).orElse(null);
        if (mediaEntity != null) {
            // 업데이트할 필드 설정
//            mediaEntity.setMyPhoneNumber(updatedMediaDTO.getMyPhoneNumber());
//            mediaEntity.setYourPhoneNumber(updatedMediaDTO.getYourPhoneNumber());

            // 데이터베이스에 업데이트된 엔티티 저장
            MediaEntity updatedMedia = mediaRepository.save(mediaEntity);

            return convertEntityToDTO(updatedMedia);
        }
        return null;
    }

    @Override
    public void deleteMedia(Long mediaId) {
        // 특정 ID에 해당하는 미디어 삭제하는 로직 (mediaRepository 사용)
        mediaRepository.deleteById(mediaId);
    }

    private MediaDTO convertEntityToDTO(MediaEntity mediaEntity) {
        MediaDTO mediaDTO = new MediaDTO();
        mediaDTO.setMediaId(mediaEntity.getMediaId());
        mediaDTO.setMediaUrl(mediaEntity.getMediaUrl());
//        mediaDTO.setMyPhoneNumber(mediaEntity.getMyPhoneNumber());
//        mediaDTO.setYourPhoneNumber(mediaEntity.getYourPhoneNumber());
//        // 다른 필드 설정
        return mediaDTO;
    }

    private File convertMultipartFileToFile(MultipartFile file) {
        File convertedFile = new File(file.getOriginalFilename());
        try {
            file.transferTo(convertedFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return convertedFile;
    }

    private String uploadFileToS3(File file) {
        // Amazon S3에 파일 업로드
        String key = "media/" + file.getName(); // Amazon S3에 저장할 경로 및 파일 이름 설정
        PutObjectResult result = amazonS3.putObject(new PutObjectRequest(bucket, key, file));
        return amazonS3.getUrl(bucket, key).toString();
    }
}
