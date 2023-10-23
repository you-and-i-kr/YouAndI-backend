package com.example.coupleapp.service;

import com.example.coupleapp.dto.HomeDTO;
import com.example.coupleapp.entity.HomeEntity;
import com.example.coupleapp.repository.HomeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HomeService {
    private final HomeRepository homeRepository;

    @Autowired
    public HomeService(HomeRepository homeRepository) {
        this.homeRepository = homeRepository;
    }

    public HomeDTO getHomeById(Long homeId) {
        HomeEntity homeEntity = homeRepository.findById(homeId).orElse(null);
        return convertToDTO(homeEntity);
    }

    public HomeDTO createHome(HomeDTO homeDTO) {
        HomeEntity homeEntity = convertToEntity(homeDTO);
        homeEntity = homeRepository.save(homeEntity);
        return convertToDTO(homeEntity);
    }

    public HomeDTO updateHome(Long homeId, HomeDTO homeDTO) {
        HomeEntity existingHomeEntity = homeRepository.findById(homeId).orElse(null);
        if (existingHomeEntity != null) {
            updateEntityFromDTO(existingHomeEntity, homeDTO);
            homeRepository.save(existingHomeEntity);
            return convertToDTO(existingHomeEntity);
        } else {
            return null; // 업데이트할 항목을 찾지 못한 경우
        }
    }

    public void deleteHome(Long homeId) {
        homeRepository.deleteById(homeId);
    }

    private HomeDTO convertToDTO(HomeEntity homeEntity) {
        if (homeEntity == null) {
            return null;
        }
        HomeDTO homeDTO = new HomeDTO();
        BeanUtils.copyProperties(homeEntity, homeDTO);
        // 만일 다른 변환 로직이 필요한 경우 추가 작성
        return homeDTO;
    }

    private HomeEntity convertToEntity(HomeDTO homeDTO) {
        HomeEntity homeEntity = new HomeEntity();
        BeanUtils.copyProperties(homeDTO, homeEntity);
        // 만일 다른 변환 로직이 필요한 경우 추가 작성
        return homeEntity;
    }

    private void updateEntityFromDTO(HomeEntity existingEntity, HomeDTO homeDTO) {
        BeanUtils.copyProperties(homeDTO, existingEntity, "home_id");
        // 만일 업데이트 시 특정 필드를 제외하고 싶다면 위의 "home_id"와 같이 필드 이름을 지정
    }
}
