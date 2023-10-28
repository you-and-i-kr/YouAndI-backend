package com.example.coupleapp.exception.domian;

public class MemoNotFoundException extends RuntimeException {
    public MemoNotFoundException(String memoId) {
        super("Memo not found with id: " + memoId);
    }
}
