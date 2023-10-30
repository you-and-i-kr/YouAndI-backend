package com.example.coupleapp.exception;

import com.example.coupleapp.exception.domian.CommonException;
import com.example.coupleapp.exception.domian.MemberException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MemberException.class)
    public ResponseEntity<CommonResponse> handleMemberException(MemberException memberException) {
        CommonResponse errorResponse = new CommonResponse(memberException.getErrorMessage(),memberException.getErrorCode());
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(errorResponse.getStatus()));
    }

    @ExceptionHandler(CommonException.class)
    public ResponseEntity<CommonResponse> handleCommonException(CommonException commonException) {
        CommonResponse errorResponse = new CommonResponse(commonException.getErrorMessage(),commonException.getErrorCode());
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(errorResponse.getStatus()));
    }
}
