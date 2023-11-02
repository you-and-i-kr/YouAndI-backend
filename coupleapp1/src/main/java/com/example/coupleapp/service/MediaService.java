package com.example.coupleapp.service;

import com.example.coupleapp.dto.MediaDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MediaService {
    MediaDTO uploadMedia(MultipartFile file,long memberId);

    MediaDTO uploadMedia(MultipartFile file, Long memberId);

    //    List<MediaDTO> getAllMedia();
    MediaDTO getMediaById(Long mediaId);
    MediaDTO updateMedia(Long mediaId, MediaDTO updatedMediaDTO);


    void deleteMedia(Long mediaId);
}