package com.example.coupleapp.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CalendarUpdateDTO {
    @ApiModelProperty(example = "20")
    private Long calendar_id;

    @ApiModelProperty(example = "title 수정")
    private String title;

    @ApiModelProperty(example = "memo 수정")
    private String memo;

}
