package com.example.coupleapp.controller;

import com.example.coupleapp.dto.PhotoDTO;
import com.example.coupleapp.dto.PhotoResponseDTO;
import com.example.coupleapp.entity.MemberEntity;
import com.example.coupleapp.entity.PhotoEntity;
import com.example.coupleapp.security.AuthHolder;
import com.example.coupleapp.service.PhotoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/photos")
@Api(tags = "사진 API", value = "사진 작업")
@RequiredArgsConstructor
public class PhotoController {

    private final PhotoService photoService;

    // 새로운 사진을 Amazon S3에 업로드하고 데이터베이스에 메타데이터를 저장
    @PostMapping
    @ApiOperation(value = "새로운 사진을 Amazon S3에 업로드하고 메타데이터 저장")
    public ResponseEntity<PhotoResponseDTO> uploadPhoto(
            @ApiParam(value = "사진 파일", required = true) @RequestParam("file") MultipartFile file) {
        // PhotoService에서 사진 업로드 및 저장 로직 구현
        Long memberId = AuthHolder.getMemberId();
        PhotoResponseDTO uploadedPhoto = photoService.uploadPhoto(file,memberId);
        return ResponseEntity.status(HttpStatus.CREATED).body(uploadedPhoto);
    }


    // 멤버 ID에 해당하는 사진 가져오기
    @GetMapping
    @ApiOperation(value = "특정 ID에 해당하는 사진 가져오기")
    public ResponseEntity<List<String>> getPhoto() {
        Long memberId = AuthHolder.getMemberId();
        List<String> photolist = photoService.getPhotoById(memberId);
        return ResponseEntity.ok(photolist);
    }

    // 사진 메타데이터 업데이트
    @PutMapping("/{photoId}")
    @ApiOperation(value = "사진 메타데이터 업데이트")
    public ResponseEntity<PhotoEntity> updatePhoto(
            @ApiParam(value = "사진 ID", required = true) @PathVariable Long photoId,
            @ApiParam(value = "사진 파일", required = true) @RequestParam("file") MultipartFile file) {
        PhotoEntity updatedPhoto = photoService.updatePhoto(photoId, file);
        return ResponseEntity.ok(updatedPhoto);
    }

    // 특정 ID에 해당하는 사진 삭제
    @DeleteMapping("/{photoId}")
    @ApiOperation(value = "특정 ID에 해당하는 사진 삭제")
    public ResponseEntity<Void> deletePhoto(
            @ApiParam(value = "사진 ID", required = true) @PathVariable Long photoId) {
        photoService.deletePhoto(photoId);
        return ResponseEntity.noContent().build();
    }
}
