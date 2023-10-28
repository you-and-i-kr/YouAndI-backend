package com.example.coupleapp.exception.domian;

public class MemberNotFoundException extends RuntimeException {
    public MemberNotFoundException(String memberId) {
        super("Member not found with id: " + memberId);
    }
}
