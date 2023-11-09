package com.example.coupleapp.repository.Calendar;

import com.example.coupleapp.dto.CalendarUpdateDTO;
import com.example.coupleapp.entity.MemberEntity;
import com.example.coupleapp.entity.QCalendarEntity;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

public class CalendarRepositoryImpl implements CalendarRepositoryCustom {

    private final EntityManager em;

    private final JPAQueryFactory queryFactory;

    public CalendarRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    QCalendarEntity cal = new QCalendarEntity("calendar");


    @Override
    public List<Tuple> findMonthSchedule(String myPhoneNum, String yourPhoneNum, LocalDate startDate, LocalDate endDate) {
        return queryFactory
                .select(cal.id,cal.title,cal.memo,cal.created_At)
                .from(cal)
                .where(cal.my_phone_number.eq(myPhoneNum)
                        .or(cal.my_phone_number.eq(yourPhoneNum))
                        .and(cal.created_At.between(startDate,endDate)))
                .fetch();
    }
    @Transactional
    @Override
    public Long updateContents(MemberEntity member, CalendarUpdateDTO calendarUpdateDTO) {
        return queryFactory
                .update(cal)
                .set(cal.title , calendarUpdateDTO.getTitle())
                .set(cal.memo , calendarUpdateDTO.getMemo())
                .where(cal.id.eq(calendarUpdateDTO.getCalendar_id())
                        .and(cal.my_phone_number.eq(member.getMy_phone_number())
                                .or(cal.my_phone_number.eq(member.getYour_phone_number()))))
                .execute();
    }

    @Transactional
    @Override
    public Long deleteByMemeberId(MemberEntity member, Long calendarId) {
        return queryFactory
                .delete(cal)
                .where(cal.id.eq(calendarId)
                        .and(cal.my_phone_number.eq(member.getMy_phone_number())
                                .or(cal.my_phone_number.eq(member.getYour_phone_number()))))
                .execute();
    }
}
