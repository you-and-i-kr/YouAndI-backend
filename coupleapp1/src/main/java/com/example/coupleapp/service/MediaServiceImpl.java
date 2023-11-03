package com.example.coupleapp.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.example.coupleapp.dto.MediaDTO;
import com.example.coupleapp.entity.MediaEntity;
import com.example.coupleapp.entity.MemberEntity;
import com.example.coupleapp.exception.domian.MediaErrorCode;
import com.example.coupleapp.exception.domian.MediaException;
import com.example.coupleapp.repository.MediaRepository;
import com.example.coupleapp.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.print.attribute.standard.Media;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

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
    public List<String> getMedias(Long memberId) {
        // member의 내번호와 맞는 데이터 + 내번호에 상대방번호가 넣어진 데이터들 불러오기
        MemberEntity member = memberRepository.findMemberByMemberId(memberId);
        // 내 번호
        String myPhoneNum = member.getMy_phone_number();
        // 상대방 번호
        String yourPhoneNum = member.getYour_phone_number();
        return mediaRepository.findMedialist(myPhoneNum,yourPhoneNum);
    }

    @Override
    public MediaDTO updateMedia(Long mediaId, MediaDTO updatedMediaDTO) {
        return null;
    }

    @Override
    public MediaEntity updateMedia(Long mediaId, MultipartFile file) {
        // 특정 ID에 해당하는 미디어를 업데이트하는 로직 (mediaRepository 사용)
        MediaEntity existingMedia = mediaRepository.findById(mediaId)
                .orElseThrow(()-> new MediaException(MediaErrorCode.FAIL_UPDATE));
        existingMedia.setMediaUrl(existingMedia.getMediaUrl());
        // Save the updated memoEntity in the repository
        return mediaRepository.save(existingMedia);
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
