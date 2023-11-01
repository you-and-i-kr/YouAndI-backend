package com.example.coupleapp.controller;

import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.coupleapp.dto.MediaDTO;
import com.example.coupleapp.exception.domian.CommonErrorCode;
import com.example.coupleapp.exception.domian.CommonException;
import com.example.coupleapp.service.MediaService;
import com.example.coupleapp.service.S3mediaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/media")
@Api(tags = "미디어 API", value = "미디어 작업")
@RequiredArgsConstructor
public class MediaController {

    private final MediaService mediaService;
    private final S3mediaService s3mediaService;
    // 새로운 미디어를 Amazon S3에 업로드하고 데이터베이스에 메타데이터를 저장
    @PostMapping
    @ApiOperation(value = "새로운 미디어를 Amazon S3에 업로드하고 메타데이터 저장")
    public ResponseEntity<MediaDTO> uploadMedia(
            @ApiParam(value = "미디어 파일", required = true) @RequestParam("file") MultipartFile file,
            @ApiParam(value = "전화번호", required = true) @RequestParam("myPhoneNumber") String myPhoneNumber,
            @ApiParam(value = "전화번호", required = true) @RequestParam("yourPhoneNumber") String yourPhoneNumber) {
        // MediaService에서 미디어 업로드 및 저장 로직 구현
        MediaDTO uploadedMedia = mediaService.uploadMedia(file, myPhoneNumber, yourPhoneNumber);
        return ResponseEntity.status(HttpStatus.CREATED).body(uploadedMedia);
    }

    // 모든 미디어 목록 가져오기
    @GetMapping
    @ApiOperation(value = "모든 미디어 목록 가져오기")
    public ResponseEntity<List<MediaDTO>>getAllMedia() {
        List<MediaDTO> mediaList = mediaService.getAllMedia();
        return ResponseEntity.ok(mediaList);
    }

    // 특정 ID에 해당하는 미디어 가져오기
    @GetMapping("/{mediaId}")
    @ApiOperation(value = "특정 ID에 해당하는 미디어 가져오기")
    public ResponseEntity<MediaDTO> getMedia(
            @ApiParam(value = "미디어 ID", required = true) @PathVariable Long mediaId) {
        MediaDTO media = mediaService.getMediaById(mediaId);
        return ResponseEntity.ok(media);
    }

    // 미디어 메타데이터 업데이트
    @PutMapping("/{mediaId}")
    @ApiOperation(value = "미디어 메타데이터 업데이트")
    public ResponseEntity<MediaDTO> updateMedia(
            @ApiParam(value = "미디어 ID", required = true) @PathVariable Long mediaId,
            @ApiParam(value = "업데이트된 미디어 정보", required = true) @RequestBody MediaDTO updatedMediaDTO) {
        MediaDTO updatedMedia = mediaService.updateMedia(mediaId, updatedMediaDTO);
        return ResponseEntity.ok(updatedMedia);
    }

    // 특정 ID에 해당하는 미디어 삭제
    @DeleteMapping("/{mediaId}")
    @ApiOperation(value = "특정 ID에 해당하는 미디어 삭제")
    public ResponseEntity<Void> deleteMedia(
            @ApiParam(value = "미디어 ID", required = true) @PathVariable Long mediaId) {
        mediaService.deleteMedia(mediaId);
        return ResponseEntity.noContent().build();
    }

}
