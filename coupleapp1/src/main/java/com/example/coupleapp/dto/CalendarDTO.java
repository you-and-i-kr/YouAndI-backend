package com.example.coupleapp.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDate;

@Data
public class CalendarDTO {
    @ApiModelProperty(example = "100일")
    private String title;
    @ApiModelProperty(example = "늦으면 죽어~")
    private String memo;
    @ApiModelProperty(example = "2023-11-20")
    private LocalDate created_At;
}
