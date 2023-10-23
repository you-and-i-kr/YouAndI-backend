package com.example.coupleapp.service;


import com.example.coupleapp.dto.AlbumDTO;
import com.example.coupleapp.entity.AlbumEntity;
import com.example.coupleapp.repository.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlbumService {
    private final AlbumRepository albumRepository;

    @Autowired
    public AlbumService(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    public AlbumDTO getAlbumById(Long albumId) {
        AlbumEntity albumEntity = albumRepository.findById(albumId).orElse(null);
        return convertToDTO(albumEntity);
    }

    public AlbumDTO createAlbum(AlbumDTO albumDTO) {
        AlbumEntity albumEntity = convertToEntity(albumDTO);
        albumEntity = albumRepository.save(albumEntity);
        return convertToDTO(albumEntity);
    }

    public AlbumDTO updateAlbum(Long albumId, AlbumDTO albumDTO) {
        AlbumEntity existingAlbumEntity = albumRepository.findById(albumId).orElse(null);
        if (existingAlbumEntity != null) {
            updateEntityFromDTO(existingAlbumEntity, albumDTO);
            albumRepository.save(existingAlbumEntity);
            return convertToDTO(existingAlbumEntity);
        } else {
            return null; // 업데이트할 항목을 찾지 못한 경우
        }
    }

    public void deleteAlbum(Long albumId) {
        albumRepository.deleteById(albumId);
    }

    private AlbumDTO convertToDTO(AlbumEntity albumEntity) {
        if (albumEntity == null) {
            return null;
        }
        AlbumDTO albumDTO = new AlbumDTO();
        albumDTO.setAlbumId(albumEntity.getAlbumId());
        albumDTO.setMemberId(albumEntity.getMember().getMemberId());
        albumDTO.setCreatedAt(albumEntity.getCreatedAt());
        return albumDTO;
    }

    private AlbumEntity convertToEntity(AlbumDTO albumDTO) {
        AlbumEntity albumEntity = new AlbumEntity();
        albumEntity.setAlbumId(albumDTO.getAlbumId());
        // MemberEntity를 얻는 방법은 필요에 따라 변경 가능
        // 예: albumDTO에서 memberId를 이용하여 MemberEntity 조회
        albumEntity.setCreatedAt(albumDTO.getCreatedAt());
        return albumEntity;
    }

    private void updateEntityFromDTO(AlbumEntity existingEntity, AlbumDTO albumDTO) {
        existingEntity.setCreatedAt(albumDTO.getCreatedAt());
        // 다른 필드도 필요한 경우 업데이트
    }
}
