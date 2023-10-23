package com.example.coupleapp.service;

import com.example.coupleapp.dto.NotificationDTO;
import com.example.coupleapp.entity.NotificationEntity;
import com.example.coupleapp.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public NotificationDTO getNotificationById(Long id) {
        NotificationEntity notificationEntity = notificationRepository.findById(id).orElse(null);
        return convertToDTO(notificationEntity);
    }

    public NotificationDTO createNotification(NotificationDTO notificationDTO) {
        NotificationEntity notificationEntity = convertToEntity(notificationDTO);
        notificationEntity = notificationRepository.save(notificationEntity);
        return convertToDTO(notificationEntity);
    }

    public NotificationDTO updateNotification(Long id, NotificationDTO notificationDTO) {
        NotificationEntity existingNotificationEntity = notificationRepository.findById(id).orElse(null);
        if (existingNotificationEntity != null) {
            updateEntityFromDTO(existingNotificationEntity, notificationDTO);
            notificationRepository.save(existingNotificationEntity);
            return convertToDTO(existingNotificationEntity);
        } else {
            return null; // 업데이트할 항목을 찾지 못한 경우
        }
    }

    public void deleteNotification(Long id) {
        notificationRepository.deleteById(id);
    }

    private NotificationDTO convertToDTO(NotificationEntity notificationEntity) {
        if (notificationEntity == null) {
            return null;
        }
        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setId(notificationEntity.getId());
        notificationDTO.setPartnerId(notificationEntity.getPartner().getMemberId());
        notificationDTO.setCreatedAt(notificationEntity.getCreated_at());
        notificationDTO.setTargetId(notificationEntity.getTarget_id());
        notificationDTO.setIsRead(notificationEntity.getIs_read());
        return notificationDTO;
    }

    private NotificationEntity convertToEntity(NotificationDTO notificationDTO) {
        NotificationEntity notificationEntity = new NotificationEntity();
        notificationEntity.setId(notificationDTO.getId());
        // MemberEntity를 얻는 방법은 필요에 따라 변경 가능
        // 예: notificationDTO에서 partnerId를 이용하여 MemberEntity 조회
        notificationEntity.setCreated_at(notificationDTO.getCreatedAt());
        notificationEntity.setTarget_id(notificationDTO.getTargetId());
        notificationEntity.setIs_read(notificationDTO.getIsRead());
        return notificationEntity;
    }

    private void updateEntityFromDTO(NotificationEntity existingEntity, NotificationDTO notificationDTO) {
        // MemberEntity 업데이트 로직 추가
        existingEntity.setCreated_at(notificationDTO.getCreatedAt());
        existingEntity.setTarget_id(notificationDTO.getTargetId());
        existingEntity.setIs_read(notificationDTO.getIsRead());
        // 다른 필드도 필요한 경우 업데이트
    }
}
