package com.example.coupleapp.controller;

import com.example.coupleapp.dto.PhotoDTO;
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
    public ResponseEntity<PhotoDTO> uploadPhoto(
            @ApiParam(value = "사진 파일", required = true) @RequestParam("file") MultipartFile file,
            @ApiParam(value = "전화번호", required = true) @RequestParam("myPhoneNumber") String myPhoneNumber,
            @ApiParam(value = "전화번호", required = true) @RequestParam("yourPhoneNumber") String yourPhoneNumber) {
        // PhotoService에서 사진 업로드 및 저장 로직 구현
        PhotoDTO uploadedPhoto = photoService.uploadPhoto(file, myPhoneNumber, yourPhoneNumber);
        return ResponseEntity.status(HttpStatus.CREATED).body(uploadedPhoto);
    }

    // 모든 사진 목록 가져오기
    @GetMapping
    @ApiOperation(value = "모든 사진 목록 가져오기")
    public ResponseEntity<List<PhotoDTO>> getAllPhotos() {
        List<PhotoDTO> photos = photoService.getAllPhotos();
        return ResponseEntity.ok(photos);
    }

    // 특정 ID에 해당하는 사진 가져오기
    @GetMapping("/{photoId}")
    @ApiOperation(value = "특정 ID에 해당하는 사진 가져오기")
    public ResponseEntity<PhotoDTO> getPhoto(
            @ApiParam(value = "사진 ID", required = true) @PathVariable Long photoId) {
        PhotoDTO photo = photoService.getPhotoById(photoId);
        return ResponseEntity.ok(photo);
    }

    // 사진 메타데이터 업데이트
    @PutMapping("/{photoId}")
    @ApiOperation(value = "사진 메타데이터 업데이트")
    public ResponseEntity<PhotoDTO> updatePhoto(
            @ApiParam(value = "사진 ID", required = true) @PathVariable Long photoId,
            @ApiParam(value = "업데이트된 사진 정보", required = true) @RequestBody PhotoDTO updatedPhotoDTO) {
        PhotoDTO updatedPhoto = photoService.updatePhoto(photoId, updatedPhotoDTO);
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
