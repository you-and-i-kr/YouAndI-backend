package com.example.coupleapp.exception.domian;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum MemoErrorCode {
    //status(HttpStatus.CONFLICT) 409
    USER_PHONE_ALREADY_EXIST("이미 존재하는 핸드폰번호 입니다.",HttpStatus.CONFLICT.value()),

    //status(HttpStatus.BAD_REQUEST) 400
    LOGIN_FAIL("메모확인에 실패하였습니다.", HttpStatus.BAD_REQUEST.value()),
    USER_NOT_FOUND("존재하지 않는 유저입니다.", HttpStatus.BAD_REQUEST.value()),
    NOT_EQUL_COUPLE("커플이 작성한 memo가 아닙니다.",HttpStatus.BAD_REQUEST.value()),
    NOT_EQUL_PASSWORD("기존의 번호와 일치하지 않습니다.",HttpStatus.BAD_REQUEST.value()),
    NOT_FOUND_MEMO("저장된 메모가 없습니다",HttpStatus.BAD_REQUEST.value()),

    FAIL_UPDATE("업데이트 실패했습니다.",HttpStatus.BAD_REQUEST.value()),

    //status(HttpStatus.UNAUTHORIZED) 401
    AUTHENTICATION_FAIL("인증에 실패하였습니다.",HttpStatus.UNAUTHORIZED.value()),
    INVALID_TOKEN("토큰이 유효하지 않습니다.",HttpStatus.UNAUTHORIZED.value());

    private final String description;
    private final Integer status;
}
