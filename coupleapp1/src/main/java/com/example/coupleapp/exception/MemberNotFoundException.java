package com.example.coupleapp.exception;

public class MemberNotFoundException extends RuntimeException {
    public MemberNotFoundException(String memberId) {
        super("Member not found with id: " + memberId);
    }
}
