package com.example.coupleapp.service.util;

public enum FilePath {
    MEMBER_IMAGE_DIR("member/"),

    SEPARATE_POINT(".com/");

    private final String path;

    FilePath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
