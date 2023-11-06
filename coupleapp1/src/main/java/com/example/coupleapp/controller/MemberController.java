package com.example.coupleapp.controller;

import com.example.coupleapp.entity.ImageEntity;
import com.example.coupleapp.repository.ImageRepository;
import com.example.coupleapp.security.AuthHolder;
import com.example.coupleapp.security.JwtUtil;
import com.example.coupleapp.dto.LoginRequestDTO;
import com.example.coupleapp.dto.MemberDTO;
import com.example.coupleapp.dto.TokenDTO;
import com.example.coupleapp.service.S3ImageService;
import com.example.coupleapp.service.MemberService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Api(tags = "회원 관련 API")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v2/api/members")
public class MemberController {

    private final MemberService memberService;
    private final S3ImageService s3ImageService;
    private final ImageRepository imageRepository;

    @ApiOperation(value = "회원가입")
    @PostMapping("/create")
    public ResponseEntity<String> createMember(
            @RequestBody MemberDTO memberDTO) {
        return ResponseEntity.ok(memberService.createMember(memberDTO));
    }
    @ApiOperation(value = "로그인")
    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(
            @RequestBody LoginRequestDTO loginRequestDTO, HttpServletResponse httpServletResponse){
        TokenDTO tokenDTO = memberService.login(loginRequestDTO);
        httpServletResponse.setHeader(JwtUtil.ACCESS_TOKEN,tokenDTO.getAccessToken());
        httpServletResponse.setHeader(JwtUtil.ACCESS_TOKEN,tokenDTO.getAccessToken());
        return ResponseEntity.ok().body(tokenDTO);
    }

//    @ApiOperation(value = "이미지리스트 업로드 테스트")
//    @PostMapping(value = "/images",consumes = "multipart/form-data")
//    public ResponseEntity<?> imagListTest(
//            @RequestPart(value = "imageFiles",required = false) List<MultipartFile> imageFiles) throws IOException {
//        return ResponseEntity.ok().body(s3ImageService.uploadImageFileList(imageFiles));
//    }


//    @ApiOperation(value = "이미지 업로드 테스트")
//    @PostMapping(value = "/image")
//    public ResponseEntity<?> imgTest(
//            @RequestPart(value = "imageFile", required = false) MultipartFile imageFile) throws IOException {
//        String uploadedImageUrl = s3ImageService.saveImageToS3AndDatabase(imageFile);
//        return ResponseEntity.ok().body(uploadedImageUrl);
//    }

//    @ApiOperation(value = "이미지 가져오기", notes = "데이터베이스에 저장된 이미지를 가져옵니다.")
//    @GetMapping("/images/{imageId}")
//    public ResponseEntity<byte[]> getImage(
//            @ApiParam(value = "이미지 ID", required = true) @PathVariable Long imageId) {
//        Optional<ImageEntity> imageOptional = imageRepository.findById(imageId);
//        if (imageOptional.isPresent()) {
//            ImageEntity image = imageOptional.get();
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.IMAGE_JPEG); // Modify based on the image type
//
//            return new ResponseEntity<>(image.getImageData(), headers, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }



//    @ApiOperation(value = "이미지 업로드 테스트")
//    @PostMapping(value = "/image")
//    public ResponseEntity<?> imgTest(
//            @RequestPart(value = "imageFile",required = false) MultipartFile imageFile) throws IOException {
//        return ResponseEntity.ok().body(s3ImageService.uploadImageFile(imageFile));
//    }



//    @GetMapping
//    public ResponseEntity<?> test() {
//        return ResponseEntity.ok().body("로그인한 유저의 식별값입니다.  = " + AuthHolder.getMemberId());
//    }
}
