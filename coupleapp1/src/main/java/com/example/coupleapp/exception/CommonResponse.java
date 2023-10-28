package com.example.coupleapp.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CommonResponse {
    private String message;
    private Integer status;

    public CommonResponse(String message, Integer status) {
        this.message = message;
        this.status = status;
    }
}
