package com.example.coupleapp.security;

import com.example.coupleapp.exception.domian.MemberErrorCode;
import com.example.coupleapp.exception.domian.MemberException;

public class AuthHolder {

    private static final ThreadLocal<Long> memberHolder = new ThreadLocal<>();

    public static void setMemberHolder(Long memberId) {
        memberHolder.set(memberId);
    }

    public static Long getMemberId(){
        if(memberHolder.get() == null) throw new MemberException(MemberErrorCode.INVALID_TOKEN);
        return memberHolder.get();
    }

    public static void clearMemberId(){memberHolder.remove();}
}
