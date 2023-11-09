package com.example.coupleapp.repository.Calendar;

import com.example.coupleapp.entity.CalendarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CalendarRepository extends JpaRepository<CalendarEntity, Long> ,CalendarRepositoryCustom {

}
