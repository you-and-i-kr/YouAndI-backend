package com.example.coupleapp.service;

import com.example.coupleapp.dto.MemberDTO;
import com.example.coupleapp.dto.SettingsDTO;
import com.example.coupleapp.entity.MemberEntity;
import com.example.coupleapp.entity.SettingsEntity;
import com.example.coupleapp.exception.domian.SettingsNotFoundException;
import com.example.coupleapp.repository.MemberRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SettingsService {
    private final MemberRepository memberRepository;
    @Autowired
    public SettingsService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void deleteSettings(Long memberId) {
        memberRepository.deleteById(memberId);
    }

//    public SettingsDTO getSettingsById(Long settingId) {
//        SettingsEntity settingsEntity = settingsRepository.findById(settingId).orElse(null);
//        return convertToDTO(settingsEntity);
//    }
//
//    public SettingsDTO createSettings(SettingsDTO settingsDTO) {
//        SettingsEntity settingsEntity = convertToEntity(settingsDTO);
//        settingsEntity = settingsRepository.save(settingsEntity);

//        return convertToDTO(settingsEntity);
//    }
//public void updateSettings(String note_name, SettingsDTO request) {
//    // 설정 업데이트 로직을 구현합니다.
//    // 먼저 해당 `noteName`에 해당하는 설정을 찾습니다.
//    MemberEntity existingSettings = memberRepository.existsByName(note_name);
//
//    if (existingSettings == null) {
//        // 업데이트할 설정을 찾을 수 없는 경우 SettingsNotFoundException 예외를 던집니다.
//        throw new SettingsNotFoundException("업데이트를 할 수 없습니다");
//    }
//
//    // 업데이트할 설정 정보를 request에서 가져와서 기존 설정에 적용합니다.
//    existingSettings.setName(request.getNote_name());
//    // 필요한 다른 설정 업데이트도 진행합니다.
//
//    // 설정을 저장합니다.
//    memberRepository.save(existingSettings);
//}

//    }

//    private SettingsDTO convertToDTO(SettingsEntity settingsEntity) {
//        if (settingsEntity == null) {
//            return null;
//        }
//        SettingsDTO settingsDTO = new SettingsDTO();
//        settingsDTO.setSettingId(settingsEntity.getSettingId());
//        settingsDTO.setMemberId(settingsEntity.getMember().getMemberId());
//        settingsDTO.setPartnerId(settingsEntity.getPartnerId());
//        return settingsDTO;
//    }
//
//    private SettingsEntity convertToEntity(SettingsDTO settingsDTO) {
//        SettingsEntity settingsEntity = new SettingsEntity();
//        settingsEntity.setSettingId(settingsDTO.getSettingId());
//        // MemberEntity를 얻는 방법은 필요에 따라 변경 가능
//        // 예: settingsDTO에서 memberId를 이용하여 MemberEntity 조회
//        settingsEntity.setPartnerId(settingsDTO.getPartnerId());
//        return settingsEntity;
//    }

//    private void updateEntityFromDTO(SettingsEntity existingEntity, SettingsDTO settingsDTO) {
//        // MemberEntity 업데이트 로직 추가
//        existingEntity.setPartnerId(settingsDTO.getPartnerId());
//        // 다른 필드도 필요한 경우 업데이트
//    }
}
