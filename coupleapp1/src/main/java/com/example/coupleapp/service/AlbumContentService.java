package com.example.coupleapp.service;

import com.example.coupleapp.dto.AlbumContentDTO;
import com.example.coupleapp.entity.AlbumContentEntity;
import com.example.coupleapp.repository.AlbumContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlbumContentService {
    private final AlbumContentRepository albumContentRepository;

    @Autowired
    public AlbumContentService(AlbumContentRepository albumContentRepository) {
        this.albumContentRepository = albumContentRepository;
    }

    public AlbumContentDTO getAlbumContentById(String contentId) {
        AlbumContentEntity albumContentEntity = albumContentRepository.findById(contentId).orElse(null);
        return convertToDTO(albumContentEntity);
    }

    public AlbumContentDTO createAlbumContent(AlbumContentDTO albumContentDTO) {
        AlbumContentEntity albumContentEntity = convertToEntity(albumContentDTO);
        albumContentEntity = albumContentRepository.save(albumContentEntity);
        return convertToDTO(albumContentEntity);
    }

    public AlbumContentDTO updateAlbumContent(String contentId, AlbumContentDTO albumContentDTO) {
        AlbumContentEntity existingAlbumContentEntity = albumContentRepository.findById(contentId).orElse(null);
        if (existingAlbumContentEntity != null) {
            updateEntityFromDTO(existingAlbumContentEntity, albumContentDTO);
            albumContentRepository.save(existingAlbumContentEntity);
            return convertToDTO(existingAlbumContentEntity);
        } else {
            return null; // 업데이트할 항목을 찾지 못한 경우
        }
    }

    public void deleteAlbumContent(String contentId) {
        albumContentRepository.deleteById(contentId);
    }

    private AlbumContentDTO convertToDTO(AlbumContentEntity albumContentEntity) {
        if (albumContentEntity == null) {
            return null;
        }
        AlbumContentDTO albumContentDTO = new AlbumContentDTO();
        albumContentDTO.setContentId(albumContentEntity.getContentId());
        albumContentDTO.setAlbumId(albumContentEntity.getAlbum().getAlbumId());
        albumContentDTO.setMediaType(albumContentEntity.getMediaType());
        albumContentDTO.setContentData(albumContentEntity.getContentData());
        albumContentDTO.setCreatedAt(albumContentEntity.getCreatedAt());
        return albumContentDTO;
    }

    private AlbumContentEntity convertToEntity(AlbumContentDTO albumContentDTO) {
        AlbumContentEntity albumContentEntity = new AlbumContentEntity();
        albumContentEntity.setContentId(albumContentDTO.getContentId());
        // AlbumEntity를 얻는 방법은 필요에 따라 변경 가능
        // 예: albumContentDTO에서 albumId를 이용하여 AlbumEntity 조회
        albumContentEntity.setMediaType(albumContentDTO.getMediaType());
        albumContentEntity.setContentData(albumContentDTO.getContentData());
        albumContentEntity.setCreatedAt(albumContentDTO.getCreatedAt());
        return albumContentEntity;
    }

    private void updateEntityFromDTO(AlbumContentEntity existingEntity, AlbumContentDTO albumContentDTO) {
        existingEntity.setMediaType(albumContentDTO.getMediaType());
        existingEntity.setContentData(albumContentDTO.getContentData());
        existingEntity.setCreatedAt(albumContentDTO.getCreatedAt());
        // 다른 필드도 필요한 경우 업데이트
    }
}
