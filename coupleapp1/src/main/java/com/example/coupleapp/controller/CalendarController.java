package com.example.coupleapp.controller;

import com.example.coupleapp.dto.CalendarDTO;
import com.example.coupleapp.service.CalendarService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/calendars")
@Api(value = "Calendar Controller", description = "Operations pertaining to calendars")
public class CalendarController {
    private final CalendarService calendarService;

    @Autowired
    public CalendarController(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get a calendar by ID", response = CalendarDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved calendar by ID"),
            @ApiResponse(code = 404, message = "Calendar not found")
    })
    public ResponseEntity<CalendarDTO> getCalendar(
            @ApiParam(value = "ID of the calendar", required = true) @PathVariable Long id) {
        CalendarDTO calendarDTO = calendarService.getCalendarById(id);
        if (calendarDTO != null) {
            return ResponseEntity.ok(calendarDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @ApiOperation(value = "Create a new calendar", response = CalendarDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Calendar created successfully"),
            @ApiResponse(code = 400, message = "Invalid input")
    })
    public ResponseEntity<CalendarDTO> createCalendar(
            @ApiParam(value = "Calendar data", required = true) @RequestBody CalendarDTO calendarDTO) {
        CalendarDTO createdCalendarDTO = calendarService.createCalendar(calendarDTO);
        return ResponseEntity.ok(createdCalendarDTO);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update a calendar by ID", response = CalendarDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated calendar by ID"),
            @ApiResponse(code = 404, message = "Calendar not found"),
            @ApiResponse(code = 400, message = "Invalid input")
    })
    public ResponseEntity<CalendarDTO> updateCalendar(
            @ApiParam(value = "ID of the calendar", required = true) @PathVariable Long id,
            @ApiParam(value = "Updated calendar data", required = true) @RequestBody CalendarDTO calendarDTO) {
        CalendarDTO updatedCalendarDTO = calendarService.updateCalendar(id, calendarDTO);
        if (updatedCalendarDTO != null) {
            return ResponseEntity.ok(updatedCalendarDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete a calendar by ID")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Calendar deleted successfully"),
            @ApiResponse(code = 404, message = "Calendar not found")
    })
    public ResponseEntity<Void> deleteCalendar(
            @ApiParam(value = "ID of the calendar", required = true) @PathVariable Long id) {
        calendarService.deleteCalendar(id);
        return ResponseEntity.noContent().build();
    }
}
