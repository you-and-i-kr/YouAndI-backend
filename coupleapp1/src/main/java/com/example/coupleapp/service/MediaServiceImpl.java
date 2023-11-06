package com.example.coupleapp.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.example.coupleapp.common.FIleVo;
import com.example.coupleapp.dto.MediaDTO;
import com.example.coupleapp.entity.MediaEntity;
import com.example.coupleapp.entity.MemberEntity;
import com.example.coupleapp.exception.domian.CommonErrorCode;
import com.example.coupleapp.exception.domian.CommonException;
import com.example.coupleapp.exception.domian.MediaErrorCode;
import com.example.coupleapp.exception.domian.MediaException;
import com.example.coupleapp.repository.MediaRepository;
import com.example.coupleapp.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public MediaDTO uploadMedia(MultipartFile file, Long memberId) {
        // MultipartFile을 File로 변환
       String mediaUrl= s3mediaService.uploadMediaFile(file);
       MemberEntity member = memberRepository.findMemberById(memberId);


        // 데이터베이스에 미디어 메타데이터 저장
        MediaEntity media = new MediaEntity();
        media.setMedia_url(mediaUrl);
        media.setMy_phone_number(member.getMy_phone_number());
        media.setYour_phone_number(member.getYour_phone_number());
        media.setCreated_at(LocalDateTime.now());
        MediaEntity savedMedia = mediaRepository.save(media);

        return convertEntityToDTO(savedMedia);
    }



    @Override
    public String uploadMediaList(List<MultipartFile> mediaFiles, Long memberId)  {
        if(mediaFiles.size() == 0) throw new MediaException(MediaErrorCode.NOT_REGISST_FILE);

        MemberEntity member = memberRepository.findMemberById(memberId);

        for(MultipartFile file : mediaFiles) {
            String mediaUrl = s3mediaService.uploadMediaFile(file);
            MediaEntity media = new MediaEntity();
            media.setMedia_url(mediaUrl);
            media.setMy_phone_number(member.getMy_phone_number());
            media.setYour_phone_number(member.getYour_phone_number());
            media.setCreated_at(LocalDateTime.now());
            media.setMember(member);
            mediaRepository.save(media);
        }

        return "업로드 완료";
    }

    @Override
    public List<Map<String,String>> getMediaList(Long memberId) {

        MemberEntity member = memberRepository.findMemberById(memberId);
        String myPhoneNum = member.getMy_phone_number();
        String yourPhoneNum = member.getYour_phone_number();
        List<String> getMediaList = mediaRepository.findMedialist(myPhoneNum,yourPhoneNum);

        if(getMediaList.size() == 0) throw new MediaException(MediaErrorCode.NOT_FOUND_FILE);

        List<Map<String,String>> resultList = new ArrayList<>();
        for (String media : getMediaList) {
            String[] parts = media.split(",");
            Map<String, String> mediaMap = new HashMap<>();
            mediaMap.put("media_id", parts[0]);
            mediaMap.put("url", parts[1]);
            resultList.add(mediaMap);
        }
        return resultList;
    }

    @Override
    public MediaEntity updateMedia(Long mediaId, MultipartFile file) {
        // 특정 ID에 해당하는 미디어를 업데이트하는 로직 (mediaRepository 사용)
        MediaEntity existingMedia = mediaRepository.findById(mediaId)
                .orElseThrow(()-> new MediaException(MediaErrorCode.FAIL_UPDATE));
        existingMedia.setMedia_url(existingMedia.getMedia_url());
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
        mediaDTO.setMediaId(mediaEntity.getId());
        mediaDTO.setMediaUrl(mediaEntity.getMedia_url());
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
