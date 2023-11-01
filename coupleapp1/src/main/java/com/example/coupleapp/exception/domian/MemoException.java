package com.example.coupleapp.exception.domian;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemoException extends RuntimeException{

    private Integer errorCode;
    private String errorMessage;


    public MemoException(MemoErrorCode errorCode) {
        this.errorCode = errorCode.getStatus();
        this.errorMessage = errorCode.getDescription();

    }
}