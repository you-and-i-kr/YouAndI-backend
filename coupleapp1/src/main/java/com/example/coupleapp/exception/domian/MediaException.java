package com.example.coupleapp.exception.domian;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MediaException extends RuntimeException{

    private Integer errorCode;
    private String errorMessage;


    public MediaException(MediaErrorCode errorCode) {
        this.errorCode = errorCode.getStatus();
        this.errorMessage = errorCode.getDescription();

    }
}