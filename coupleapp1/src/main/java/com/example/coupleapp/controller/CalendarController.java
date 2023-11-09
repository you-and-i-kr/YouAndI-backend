package com.example.coupleapp.controller;

import com.amazonaws.http.apache.utils.ApacheUtils;
import com.example.coupleapp.dto.CalendarDTO;
import com.example.coupleapp.dto.CalendarUpdateDTO;
import com.example.coupleapp.service.CalendarService;
import io.swagger.annotations.*;
import lombok.Generated;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/calendars")
@Api(tags = "달력 API")
public class CalendarController {

    final private CalendarService calendarService;
    @ApiOperation(value = "원하는 날짜에 title 과 memo 생성")
    @PostMapping
    public Long create(@RequestBody CalendarDTO calendarDTO) {
        return calendarService.create(calendarDTO);
    }

    @ApiOperation(value = "월에 저장된 날짜 별 데이터 모두 불러오기")
    @GetMapping("/{month}")
    @ApiImplicitParam(name = "month",example = "2023-11")
    public List<Map<String, String>> getAllTitle(@PathVariable @DateTimeFormat(pattern = "yyyy-MM") YearMonth month) {
        return calendarService.getAllTitle(month);
    }

    @ApiOperation(value = "날짜에 해당하는 데이터 변경")
    @PutMapping("/update")
    public String update(@RequestBody CalendarUpdateDTO calendarUpdateDTO) {
        return calendarService.update(calendarUpdateDTO);
    }

    @ApiOperation(value = "날짜에 해당하는 데이터 삭제")
    @DeleteMapping("/delete/{calendar_id}")
    public String delete(@PathVariable Long calendar_id){
        return calendarService.delete(calendar_id);
    }
}
