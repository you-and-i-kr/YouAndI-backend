package com.example.coupleapp.service;

import com.example.coupleapp.dto.SettingsDTO;
import com.example.coupleapp.entity.SettingsEntity;
import com.example.coupleapp.repository.SettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SettingsService {
    private final SettingsRepository settingsRepository;

    @Autowired
    public SettingsService(SettingsRepository settingsRepository) {
        this.settingsRepository = settingsRepository;
    }

    public SettingsDTO getSettingsById(Long settingId) {
        SettingsEntity settingsEntity = settingsRepository.findById(settingId).orElse(null);
        return convertToDTO(settingsEntity);
    }

    public SettingsDTO createSettings(SettingsDTO settingsDTO) {
        SettingsEntity settingsEntity = convertToEntity(settingsDTO);
        settingsEntity = settingsRepository.save(settingsEntity);
        return convertToDTO(settingsEntity);
    }

    public SettingsDTO updateSettings(Long settingId, SettingsDTO settingsDTO) {
        SettingsEntity existingSettingsEntity = settingsRepository.findById(settingId).orElse(null);
        if (existingSettingsEntity != null) {
            updateEntityFromDTO(existingSettingsEntity, settingsDTO);
            settingsRepository.save(existingSettingsEntity);
            return convertToDTO(existingSettingsEntity);
        } else {
            return null; // 업데이트할 항목을 찾지 못한 경우
        }
    }

    public void deleteSettings(Long settingId) {
        settingsRepository.deleteById(settingId);
    }

    private SettingsDTO convertToDTO(SettingsEntity settingsEntity) {
        if (settingsEntity == null) {
            return null;
        }
        SettingsDTO settingsDTO = new SettingsDTO();
        settingsDTO.setSettingId(settingsEntity.getSettingId());
        settingsDTO.setMemberId(settingsEntity.getMember().getMemberId());
        settingsDTO.setPartnerId(settingsEntity.getPartnerId());
        return settingsDTO;
    }

    private SettingsEntity convertToEntity(SettingsDTO settingsDTO) {
        SettingsEntity settingsEntity = new SettingsEntity();
        settingsEntity.setSettingId(settingsDTO.getSettingId());
        // MemberEntity를 얻는 방법은 필요에 따라 변경 가능
        // 예: settingsDTO에서 memberId를 이용하여 MemberEntity 조회
        settingsEntity.setPartnerId(settingsDTO.getPartnerId());
        return settingsEntity;
    }

    private void updateEntityFromDTO(SettingsEntity existingEntity, SettingsDTO settingsDTO) {
        // MemberEntity 업데이트 로직 추가
        existingEntity.setPartnerId(settingsDTO.getPartnerId());
        // 다른 필드도 필요한 경우 업데이트
    }
}
