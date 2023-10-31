package com.example.coupleapp.exception;

public enum AlbumErrorCode {
    UNAUTHORIZED_ACCESS("인가되지 않은 코드입니다."), ALBUM_NOT_FOUND("앨범을 찾지 못했습니다.");
    // 다른 에러 코드 추가 가능

    private final String message;

    AlbumErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
    }
