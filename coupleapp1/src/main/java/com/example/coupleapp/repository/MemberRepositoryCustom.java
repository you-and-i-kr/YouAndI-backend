package com.example.coupleapp.repository;

import com.example.coupleapp.entity.MemberEntity;

public interface MemberRepositoryCustom {
    MemberEntity findUserByEmail(String email);

    MemberEntity findMemberByMemberId(Long memberId);

}
