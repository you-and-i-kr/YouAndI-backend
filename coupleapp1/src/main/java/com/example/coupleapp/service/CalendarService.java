package com.example.coupleapp.service;

import com.example.coupleapp.dto.CalendarDTO;
import com.example.coupleapp.entity.CalendarEntity;
import com.example.coupleapp.repository.CalendarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CalendarService {
    private final CalendarRepository calendarRepository;

    @Autowired
    public CalendarService(CalendarRepository calendarRepository) {
        this.calendarRepository = calendarRepository;
    }

    public CalendarDTO getCalendarById(Long calendarId) {
        CalendarEntity calendarEntity = calendarRepository.findById(calendarId).orElse(null);
        return convertToDTO(calendarEntity);
    }

    public CalendarDTO createCalendar(CalendarDTO calendarDTO) {
        CalendarEntity calendarEntity = convertToEntity(calendarDTO);
        calendarEntity = calendarRepository.save(calendarEntity);
        return convertToDTO(calendarEntity);
    }

    public CalendarDTO updateCalendar(Long calendarId, CalendarDTO calendarDTO) {
        CalendarEntity existingCalendarEntity = calendarRepository.findById(calendarId).orElse(null);
        if (existingCalendarEntity != null) {
            updateEntityFromDTO(existingCalendarEntity, calendarDTO);
            calendarRepository.save(existingCalendarEntity);
            return convertToDTO(existingCalendarEntity);
        } else {
            return null; // 업데이트할 항목을 찾지 못한 경우
        }
    }

    public void deleteCalendar(Long calendarId) {
        calendarRepository.deleteById(calendarId);
    }

    private CalendarDTO convertToDTO(CalendarEntity calendarEntity) {
        if (calendarEntity == null) {
            return null;
        }
        CalendarDTO calendarDTO = new CalendarDTO();
        calendarDTO.setCalendarId(calendarEntity.getCalendarId());
        calendarDTO.setMemberId(calendarEntity.getMember().getMemberId());
        calendarDTO.setTitle(calendarEntity.getTitle());
        calendarDTO.setMemo(calendarEntity.getMemo());
        calendarDTO.setStartDate(calendarEntity.getStartDate());
        calendarDTO.setEndDate(calendarEntity.getEndDate());
        return calendarDTO;
    }

    private CalendarEntity convertToEntity(CalendarDTO calendarDTO) {
        CalendarEntity calendarEntity = new CalendarEntity();
        calendarEntity.setCalendarId(calendarDTO.getCalendarId());
        // MemberEntity를 얻는 방법은 필요에 따라 변경 가능
        // 예: calendarDTO에서 memberId를 이용하여 MemberEntity 조회
        calendarEntity.setTitle(calendarDTO.getTitle());
        calendarEntity.setMemo(calendarDTO.getMemo());
        calendarEntity.setStartDate(calendarDTO.getStartDate());
        calendarEntity.setEndDate(calendarDTO.getEndDate());
        return calendarEntity;
    }

    private void updateEntityFromDTO(CalendarEntity existingEntity, CalendarDTO calendarDTO) {
        // MemberEntity 업데이트 로직 추가
        existingEntity.setTitle(calendarDTO.getTitle());
        existingEntity.setMemo(calendarDTO.getMemo());
        existingEntity.setStartDate(calendarDTO.getStartDate());
        existingEntity.setEndDate(calendarDTO.getEndDate());
        // 다른 필드도 필요한 경우 업데이트
    }
}
