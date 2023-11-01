package com.example.coupleapp.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.coupleapp.config.S3Config;
import com.example.coupleapp.exception.domian.CommonErrorCode;
import com.example.coupleapp.exception.domian.CommonException;
import com.example.coupleapp.repository.MediaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3mediaService {

    private final AmazonS3Client amazonS3Client;
    private S3Config s3Config;
    private final MediaRepository mediaRepository;

    @Value("${S3_BUCKET_NAME}")
    private String bucket;

    public String uploadMediaFile(MultipartFile mediaFile) {
        String fileName = createFileName(mediaFile.getOriginalFilename());
        try {
            if (mediaFile.isEmpty()) {
                throw new CommonException(CommonErrorCode.NOT_FOUND_MEDIA_FILES);
            }
            return memoryUpload(mediaFile, fileName);
        } catch (IOException e) {
            throw new CommonException(CommonErrorCode.FAIL_TO_SAVE);
        }
    }

    public List<String> uploadMediaFileList(List<MultipartFile> mediaList) throws IOException {
        // TODO 미디어 null 시 예외처리
        if (mediaList == null) throw new CommonException(CommonErrorCode.NOT_FOUND_MEDIA_FILES);

        List<String> urlList = new ArrayList<>();
        mediaList.forEach(multipartFile -> {
            String fileName = createFileName(multipartFile.getOriginalFilename());
            try {
                String url = memoryUpload(multipartFile, fileName);
                urlList.add(url);
            } catch (IOException e) {
                throw new CommonException(CommonErrorCode.FAIL_TO_SAVE);
            }

        });
        return urlList;
    }

    public String memoryUpload(MultipartFile uploadFile, String fileName) throws IOException {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(uploadFile.getSize());
        objectMetadata.setContentType(uploadFile.getContentType());

        try (InputStream inputStream = uploadFile.getInputStream()) {
            amazonS3Client.putObject(
                    new PutObjectRequest(bucket, fileName, inputStream, objectMetadata)
                            .withCannedAcl(CannedAccessControlList.PublicRead)
            );
            return amazonS3Client.getUrl(bucket, fileName).toString();
        }
    }

    public String createFileName(String fileName) {
        return UUID.randomUUID().toString();
    }

    // 나머지 코드 (미디어를 S3에 저장하고 데이터베이스에 업로드하는 부분)

    public String saveMediaToS3AndDatabase(MultipartFile uploadFile) throws IOException {
        String fileName = createFileName(uploadFile.getOriginalFilename());
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(uploadFile.getSize());
        objectMetadata.setContentType(uploadFile.getContentType());

        try (InputStream inputStream = uploadFile.getInputStream()) {
            amazonS3Client.putObject(
                    new PutObjectRequest(bucket, fileName, inputStream, objectMetadata)
                            .withCannedAcl(CannedAccessControlList.PublicRead)
            );

            // 미디어 정보를 데이터베이스에 저장
            // MediaEntity 등을 사용하여 데이터베이스에 저장하는 코드 추가

            return amazonS3Client.getUrl(bucket, fileName).toString();
        } catch (IOException e) {
            throw new CommonException(CommonErrorCode.FAIL_TO_SAVE);
        }
    }
}
