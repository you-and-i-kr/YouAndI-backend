package com.example.coupleapp.service;

import com.example.coupleapp.dto.CalendarDTO;
import com.example.coupleapp.dto.CalendarUpdateDTO;
import com.example.coupleapp.entity.CalendarEntity;
import com.example.coupleapp.entity.MemberEntity;
import com.example.coupleapp.exception.domian.CommonErrorCode;
import com.example.coupleapp.exception.domian.CommonException;
import com.example.coupleapp.exception.domian.PhotoErrorCode;
import com.example.coupleapp.exception.domian.PhotoException;
import com.example.coupleapp.repository.Calendar.CalendarRepository;
import com.example.coupleapp.repository.Member.MemberRepository;
import com.example.coupleapp.security.AuthHolder;
import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class CalendarService {

    final private CalendarRepository calendarRepository;
    final private MemberRepository memberRepository;

    public Long create(CalendarDTO calendarDTO) {
        Long memberId = AuthHolder.getMemberId();
        MemberEntity member = memberRepository.findMemberById(memberId);
        CalendarEntity calendarEntity
                = new CalendarEntity(
                member,
                calendarDTO.getTitle(),
                calendarDTO.getMemo(),
                calendarDTO.getCreated_At(),
                member.getMy_phone_number(),
                member.getYour_phone_number());

        return calendarRepository.save(calendarEntity).getId();
    }

    public List<Map<String, String>> getAllTitle(YearMonth month) {
        Long memberId = AuthHolder.getMemberId();
        MemberEntity member = memberRepository.findMemberById(memberId);
        String myPhoneNum = member.getMy_phone_number();
        String yourPhoneNum = member.getYour_phone_number();

        LocalDate startDate = LocalDate.parse(month + "-01");
        LocalDate endDate = LocalDate.parse(month + "-01").plusMonths(1).minusDays(1);

        List<Tuple> getMonthList = calendarRepository.findMonthSchedule(myPhoneNum,yourPhoneNum,startDate,endDate);

        if(getMonthList.size() == 0) throw new PhotoException(PhotoErrorCode.NOT_FOUND_FILE);

        List<Map<String, String>> resultList = new ArrayList<>();
        for(Tuple tuple : getMonthList){
            Map<String,String> monthMap = new HashMap<>();
            monthMap.put("calendar_id", String.valueOf(tuple.get(0, Long.class)));
            monthMap.put("title",tuple.get(1, String.class));
            monthMap.put("memo",tuple.get(2, String.class));
            monthMap.put("created_At", String.valueOf(tuple.get(3, LocalDate.class)));
            resultList.add(monthMap);
        }

        return resultList;
    }

    public String update(CalendarUpdateDTO calendarUpdateDTO) {
        Long memberId = AuthHolder.getMemberId();
        MemberEntity member = memberRepository.findMemberById(memberId);

        if(calendarRepository.updateContents(member,calendarUpdateDTO) == 0)
            throw new CommonException(CommonErrorCode.FAIL_TO_UPDATE);

        return "수정이 완료 되었습니다.";
    }

    public String delete(Long calendarId) {
        Long memberId = AuthHolder.getMemberId();
        MemberEntity member = memberRepository.findMemberById(memberId);

        if(calendarRepository.deleteByMemeberId(member,calendarId) == 0)
            throw new CommonException(CommonErrorCode.FAIL_TO_DELETE);

        return "삭제 되었습니다.";
    }
}
