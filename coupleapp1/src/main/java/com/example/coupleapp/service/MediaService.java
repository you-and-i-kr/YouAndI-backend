package com.example.coupleapp.service;

import com.example.coupleapp.common.FIleVo;
import com.example.coupleapp.dto.MediaDTO;
import com.example.coupleapp.entity.MediaEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface MediaService {
    MediaDTO uploadMedia(MultipartFile file, Long memberId);
    //    List<MediaDTO> getAllMedia();
    List<Map<String,String>> getMediaList(Long mediaId);
    MediaEntity updateMedia(Long mediaId, MultipartFile file);

    void deleteMedia(Long mediaId);

    String uploadMediaList(List<MultipartFile> mediaFiles, Long memberId) throws IOException;
}