package com.example.coupleapp.exception.domian;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PhotoException extends RuntimeException{

    private Integer errorCode;
    private String errorMessage;


    public PhotoException(PhotoErrorCode errorCode) {
        this.errorCode = errorCode.getStatus();
        this.errorMessage = errorCode.getDescription();

    }
}