package com.example.coupleapp.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberException extends RuntimeException{

    private MemberErrorCode errorCode;
    private String errorMessage;

    public MemberException(MemberErrorCode errorCode) {
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getDescription();
    }
}
