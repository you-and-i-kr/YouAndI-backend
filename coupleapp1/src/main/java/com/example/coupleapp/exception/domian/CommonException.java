package com.example.coupleapp.exception.domian;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonException extends RuntimeException{
    private Integer errorCode;
    private String errorMessage;

    public CommonException(CommonErrorCode commonErrorCode) {
        this.errorCode = commonErrorCode.getStatus();
        this.errorMessage = commonErrorCode.getDescription();
    }
}
