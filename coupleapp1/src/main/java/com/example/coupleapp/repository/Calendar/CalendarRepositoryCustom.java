package com.example.coupleapp.repository.Calendar;

import com.example.coupleapp.dto.CalendarUpdateDTO;
import com.example.coupleapp.entity.MemberEntity;
import com.querydsl.core.Tuple;

import java.time.LocalDate;
import java.util.List;

public interface CalendarRepositoryCustom {
    List<Tuple> findMonthSchedule(String myPhoneNum, String yourPhoneNum, LocalDate startDate, LocalDate endDate);

    Long updateContents(MemberEntity member, CalendarUpdateDTO calendarUpdateDTO);

    Long deleteByMemeberId(MemberEntity member, Long calendarId);
}
