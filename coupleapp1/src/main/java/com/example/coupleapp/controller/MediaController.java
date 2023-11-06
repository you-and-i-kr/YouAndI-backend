package com.example.coupleapp.controller;


import com.example.coupleapp.dto.MediaDTO;
import com.example.coupleapp.entity.MediaEntity;
import com.example.coupleapp.security.AuthHolder;
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

import java.util.List;


@RestController
@RequestMapping("/api/media")
@Api(tags = "미디어 API", value = "미디어 작업")
@RequiredArgsConstructor
public class MediaController {

    private final MediaService mediaService;
    private final S3mediaService s3mediaService;
    // 새로운 미디어를 Amazon S3에 업로드하고 데이터베이스에 메타데이터를 저장
    @PostMapping
    @ApiOperation(value = "새로운 미디어를 Amazon S3에 업로드하고 메타데이터 저장 / 10MB 이하만 전송 가능 ")
    public ResponseEntity<MediaDTO> uploadMedia(
            @ApiParam(value = "미디어 파일", required = true) @RequestParam("file") MultipartFile file)
    {
        // MediaService에서 미디어 업로드 및 저장 로직 구현
        Long memberId = AuthHolder.getMemberId();
        MediaDTO uploadMedia = mediaService.uploadMedia(file,memberId);
        return ResponseEntity.status(HttpStatus.CREATED).body(uploadMedia);
    }



    // 특정 ID에 해당하는 미디어 가져오기
    @GetMapping()
    @ApiOperation(value = "저장한 영상들 불러오기")
    public ResponseEntity<List<String>> getMedias(){
        Long memberId = AuthHolder.getMemberId();
        return ResponseEntity.ok(mediaService.getMedias(memberId));
    }

    // 미디어 메타데이터 업데이트
    @PutMapping("/{mediaId}")
    @ApiOperation(value = "미디어 메타데이터 업데이트")
    public ResponseEntity<MediaEntity> updateMedia(
            @ApiParam(value = "미디어 ID", required = true) @PathVariable Long mediaId,
            @ApiParam(value = "미디어 파일", required = true) @RequestParam("file") MultipartFile file) {
        MediaEntity updatedMedia = mediaService.updateMedia(mediaId, file);
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
